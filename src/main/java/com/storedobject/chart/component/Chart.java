/*
 *  Copyright 2019-2020 Syam Pillai
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.storedobject.chart.component;

import static com.storedobject.chart.util.ComponentPropertyUtil.camelName;

import java.util.*;
import java.util.stream.Collectors;

import com.storedobject.chart.SOChart;
import com.storedobject.chart.coordinate_system.Axis;
import com.storedobject.chart.coordinate_system.Axis.AxisWrapper;
import com.storedobject.chart.coordinate_system.CoordinateSystem;
import com.storedobject.chart.coordinate_system.Position;
import com.storedobject.chart.data.AbstractDataProvider;
import com.storedobject.chart.data.DataProvider;
import com.storedobject.chart.property.BaseComponentProperty;
import com.storedobject.chart.property.Color;
import com.storedobject.chart.property.ComponentProperty;
import com.storedobject.chart.util.ChartException;

/**
 * <p>
 * Chart. Since this is a concrete class, this may be directly used for creating
 * a chart of a particular {@link ChartType}. It has got the flexibility that
 * the {@link ChartType} can be changed at any time using
 * {@link #setType(ChartType)} method. However, there are concrete derivatives
 * of this class such as {@link PieChart}, {@link NightingaleRoseChart} etc.
 * where more chart-specific methods are available and data for the chart is
 * checked more accurately for errors. If the data set for the chart is of
 * invalid type, system tries to do its best to adapt that data but the chart
 * may not appear if the data conversion fails.
 * </p>
 * <p>
 * Positioning of charts within the display area of {@link SOChart}: Most charts
 * need a {@link CoordinateSystem} to plot on and all {@link CoordinateSystem}s
 * support positioning (Please see
 * {@link CoordinateSystem#setPosition(Position)}). Those which do not require a
 * {@link CoordinateSystem} are called {@link SelfPositioningChart} and supports
 * its own positioning mechanism (Please see
 * {@link SelfPositioningChart#setPosition(Position)}).
 * </p>
 *
 * @author Syam
 */
public class Chart extends AbstractPart implements Component {

	private ChartType type = ChartType.Line;
	private String name;
	private CoordinateSystem coordinateSystem;
	private List<Axis> axes;
	private AbstractDataProvider<?>[] data;
	private Color[] colors;
	private final Map<Class<? extends ComponentProperty>, ComponentProperty> propertyMap = new HashMap<>();
	private final Map<Class<? extends ComponentProperty>, String> propertyNameMap = new HashMap<>();

	/**
	 * Create a {@link ChartType#Line} chart.
	 */
	public Chart() {
		this(ChartType.Line);
	}

	/**
	 * Create a {@link ChartType#Line} chart with the given data.
	 *
	 * @param data Data to be used (multiples of them for charts that use multi-axis
	 *             coordinate systems).
	 */
	public Chart(AbstractDataProvider<?>... data) {
		this(null, data);
	}

	/**
	 * Create a chart of a given type and data.
	 *
	 * @param type type of the chart.
	 * @param data Data to be used (multiples of them for charts that use multi-axis
	 *             coordinate systems).
	 */
	public Chart(ChartType type, AbstractDataProvider<?>... data) {
		setType(type);
		this.data = data;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("type", type());
		property("color", colors);

		if (coordinateSystem != null && axes != null) {
			List<Axis> coordinateAxes = coordinateSystem.getAxes();
			if (coordinateAxes != axes) {
				axes.stream().map(axis -> axis.wrap(coordinateSystem)).forEach(axisPart -> {
					axisIndexProperty(axisPart);
				});
			} else {
				coordinateAxes.stream().map(axis -> axis.getClass()).distinct()
						.forEach(axisClass -> coordinateSystem.axes(axisClass).map(axis -> axis.wrap(coordinateSystem))
								.min(Comparator.comparing(ComponentPart::getSerial)).ifPresent(axisPart -> {
									axisIndexProperty(axisPart);
								}));
			}
		}

		property("coordinateSystem", coordinateSystem, CoordinateSystem::systemName);

		if (!(this instanceof AbstractDataChart)) {
			String[] axes = null;
			if (coordinateSystem != null) {
				axes = coordinateSystem.axesData();
			}
			if (axes == null) {
				axes = type.getAxes();
			}

			ComponentParts dataParts = ComponentParts.of(data);
			if (dataParts.isDataSetEncoding()) {
				int datasetIndex = data[0].getDatasetIndex();
				property("datasetIndex", datasetIndex, datasetIndex != 0);

				BaseComponentProperty encode = new BaseComponentProperty("encode");
				for (int i = 0; i < axes.length; i++) {
					if (data[i].isDataSetEncoding()) {
						encode.setProperty(axes[i], data[i].datasetName());
					}
				}
				property(encode);
			} else {
				List<DataProvider> valueDataList = dataParts.valueDataProviderStream()
						.filter(DataProvider::nonDataSetEncoding).collect(Collectors.toList());
				if (valueDataList.size() == 1) {
					DataProvider valueData = valueDataList.get(0);
					property(valueData.asProperty());
				}
			}
		}
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		super.encodeJSON(sb);

		propertyMap.values().forEach(property -> {
			ComponentPart.addComma(sb);
			sb.append('\"').append(getPropertyName(property)).append("\":{");
			ComponentPart.encodeProperty(sb, property);
			sb.append("}");
		});

	}

	@Override
	public void validate() throws ChartException {
		String[] axes = type.getAxes();
		if (data == null) {
			throw new ChartException("Data not set for " + className());
		}
		if (data.length < axes.length) {
			throw new ChartException("Data for " + name(axes[data.length]) + " not set for " + className());
		}
		if (type.requireCoordinateSystem()) {
			if (coordinateSystem == null) {
				throw new ChartException("Coordinate system not set for " + className());
			}
			if (this.axes == null || this.axes.isEmpty()) {
				this.axes = coordinateSystem.getAxes();
			}
			if (coordinateSystem.getAxes() != this.axes) {
				for (Axis axis : this.axes) {
					if (!coordinateSystem.containsAxis(axis)) {
						String name = axis.getName();
						if (name == null) {
							name = ComponentPart.className(axis.getClass());
						}
						throw new ChartException("Axis " + name
								+ " doesn't belong to the coordinate system of this chart - " + getName());
					}
				}
			}
		}
		if (coordinateSystem != null && coordinateSystem.getAxes() != this.axes) {
			for (Axis axis : this.axes) {
				if (this.axes.stream().filter(ax -> ax.getClass() == axis.getClass()).count() > 1) {
					String name = axis.getName();
					if (name == null) {
						name = ComponentPart.className(axis.getClass());
					}
					throw new ChartException(
							"Multiple axes of the same type found (" + name + ") for this chart - " + getName());
				}
			}
		}
	}

	/**
	 * Set data for the chart.
	 *
	 * @param data Data to be used (multiples of them for charts that use multi-axis
	 *             coordinate systems).
	 */
	public void setData(AbstractDataProvider<?>... data) {
		this.data = data;
	}

	/**
	 * Get the current set of data.
	 *
	 * @return Data.
	 */
	public AbstractDataProvider<?>[] getData() {
		return data;
	}

	private String type() {
		return camelName(type.toString());
	}

	private String getPropertyName(ComponentProperty property) {
		String name = propertyNameMap.get(property.getClass());
		if (name == null) {
			name = property.getClass().getName();
			name = name.substring(name.lastIndexOf('.') + 1);
			name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
		}
		return name;
	}

	<P extends ComponentProperty> void setPropertyName(Class<P> propertyClass, String name) {
		propertyNameMap.put(propertyClass, name);
	}

	<P extends ComponentProperty> P getProperty(Class<P> propertyClass, boolean create) {
		@SuppressWarnings("unchecked")
		P property = (P) propertyMap.get(propertyClass);
		if (property == null && create) {
			try {
				property = propertyClass.getDeclaredConstructor().newInstance();
			} catch (Throwable ignored) {
				return null;
			}
			propertyMap.put(propertyClass, property);
		}
		return property;
	}

	@Override
	public Chart setProperty(ComponentProperty property) {
		if (property == null) {
			return this;
		}

		if (property instanceof BaseComponentProperty) {
			return (Chart) super.setProperty(property);
		}

		propertyMap.put(property.getClass(), property);
		return this;
	}

	String axisName(int axis) {
		return name(type.getAxes()[axis]);
	}

	static String name(String name) {
		if (name.substring(1).equals(name.substring(1).toLowerCase())) {
			return name;
		}
		StringBuilder n = new StringBuilder();
		n.append(Character.toUpperCase(name.charAt(0)));
		name.chars().skip(1).forEach(c -> {
			if (Character.isUpperCase((char) c)) {
				n.append(' ');
			}
			n.append((char) c);
		});
		return n.toString();
	}

	@Override
	public void addPartsInto(ComponentParts parts) {
		if (coordinateSystem != null) {
			parts.add(coordinateSystem);
			coordinateSystem.addPartsInto(parts);
		} else {
			parts.addAll(data);
		}
	}

	/**
	 * Get the type of this chart.
	 *
	 * @return Type.
	 */
	public ChartType getType() {
		return type;
	}

	/**
	 * Set the type of this chart.
	 *
	 * @param type Type to be set.
	 */
	public void setType(ChartType type) {
		this.type = type == null ? ChartType.Line : type;
	}

	/**
	 * Plot the chart on a given coordinate system. (Certain chart types such as
	 * {@link ChartType#Pie}, do not have a coordinate system and thus, this call is
	 * not required. Also, instead of using this method, you can use the
	 * {@link CoordinateSystem#add(Chart...)} method if you want to plot on the
	 * default set of axes.
	 *
	 * @param coordinateSystem Coordinate system on which the chart will be plotted.
	 *                         (If it was plotted on another coordinate system, it
	 *                         will be removed from it).
	 * @param axes             Axes to be used by the chart. This needs to be
	 *                         specified if the coordinate system has multiple axes
	 *                         of the required type.
	 * @return Self reference.
	 */
	public Chart plotOn(CoordinateSystem coordinateSystem, Axis... axes) {
		if (coordinateSystem == null) {
			this.axes = null;
		} else if (type.requireCoordinateSystem()) {
			coordinateSystem.add(this);
		}
		if (this.coordinateSystem != null && axes != null && axes.length > 0) {
			this.axes = new ArrayList<>();
			for (Axis a : axes) {
				if (a != null) {
					this.axes.add(a);
				}
			}
			this.coordinateSystem.addAxis(axes);
		}
		return this;
	}

	/**
	 * Plot the chart on a given set of axes.
	 *
	 * @param axes Axes to be used by the chart.
	 * @return Self reference.
	 */
	public Chart plotOn(Axis... axes) {
		if (axes != null && axes.length > 0) {
			this.axes = new ArrayList<>();
			for (Axis a : axes) {
				if (a != null) {
					this.axes.add(a);
				}
			}
		}
		return this;
	}

	/**
	 * Set the name of the chart. (This will be used in displaying the legend if the
	 * legend is enabled in the {@link SOChart} or added separately).
	 *
	 * @return Name.
	 */
	@Override
	public String getName() {
		return name == null || name.isEmpty() ? ("Chart " + (getSerial() + 1)) : name;
	}

	/**
	 * Set name for this chart.
	 *
	 * @param name Name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public CoordinateSystem getCoordinateSystem() {
		return coordinateSystem;
	}

	public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
		this.coordinateSystem = coordinateSystem;
	}

	public List<Axis> getAxes() {
		return axes;
	}

	public void setAxes(List<Axis> axes) {
		this.axes = axes;
	}

	/**
	 * Set colors for the charts. Certain charts require more than one color, e.g.,
	 * {@link PieChart}. (Colors are used sequentially and then, circularly).
	 *
	 * @param colors List of one or more colors.
	 */
	public void setColors(Color... colors) {
		this.colors = colors.length == 0 ? null : colors;
	}

	/**
	 * Get the tooltip. (If <code>true</code> is passed as the parameter, a new
	 * tooltip will be created if not already exists).
	 *
	 * @param create Whether to create it or not.
	 * @return Tooltip.
	 */
	public Tooltip getTooltip(boolean create) {
		return getProperty(Tooltip.class, create);
	}

	/**
	 * Set the tooltip.
	 *
	 * @param tooltip Tooltip.
	 */
	public void setTooltip(Tooltip tooltip) {
		setProperty(tooltip);
	}

	private void axisIndexProperty(AxisWrapper axisPart) {
		if (axisPart.getSerial() <= 0)
			return;

		property(axisPart.getAxis().axisName() + "Index", axisPart.getSerial());
	}
}
