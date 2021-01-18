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

package com.storedobject.chart;

import com.storedobject.chart.component.Chart;
import com.storedobject.chart.component.Component;
import com.storedobject.chart.component.ComponentPart;
import com.storedobject.chart.component.Legend;
import com.storedobject.chart.component.PieChart;
import com.storedobject.chart.component.Tooltip;
import com.storedobject.chart.coordinate_system.AngleAxis;
import com.storedobject.chart.coordinate_system.PolarCoordinate;
import com.storedobject.chart.coordinate_system.RectangularCoordinate;
import com.storedobject.chart.data.AbstractData;
import com.storedobject.chart.data.AbstractDataProvider;
import com.storedobject.chart.encoder.AngleAxisEncoder;
import com.storedobject.chart.encoder.ColorEncoder;
import com.storedobject.chart.encoder.ComponentEncoder;
import com.storedobject.chart.encoder.DataSetEncoder;
import com.storedobject.chart.encoder.DataZoomEncoder;
import com.storedobject.chart.encoder.RectangularCoordinateEncoder;
import com.storedobject.chart.encoder.LegendEncoder;
import com.storedobject.chart.encoder.PolarCoordinateEncoder;
import com.storedobject.chart.encoder.RadarCoordinateEncoder;
import com.storedobject.chart.encoder.RadiusAxisEncoder;
import com.storedobject.chart.encoder.ChartEncoder;
import com.storedobject.chart.encoder.TextStyleEncoder;
import com.storedobject.chart.encoder.TitleEncoder;
import com.storedobject.chart.encoder.ToolboxEncoder;
import com.storedobject.chart.encoder.TooltipEncoder;
import com.storedobject.chart.encoder.XAxisEncoder;
import com.storedobject.chart.encoder.YAxisEncoder;
import com.storedobject.chart.property.Color;
import com.storedobject.chart.property.DefaultColors;
import com.storedobject.chart.property.DefaultTextStyle;
import com.storedobject.chart.property.TextStyle;
import com.storedobject.chart.util.ChartException;
import com.storedobject.helper.ID;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

import elemental.json.Json;
import elemental.json.impl.JsonUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * Chart is a Vaadin
 * {@link com.storedobject.chart.component.flow.component.Component} so that you
 * can add it to any layout component for displaying it. It is a LitComponent
 * wrapper around the "echarts" library.
 * </p>
 * <p>
 * Chart is composed of one more chart {@link Component}s and each chart
 * {@link Component} may have zero or more {@link ComponentPart}s. Examples of
 * chart {@link Component}s are (a) {@link Chart}, (b)
 * {@link RectangularCoordinate}, (c) {@link PolarCoordinate} etc. An example of
 * a {@link ComponentPart} is {@link AngleAxis} that is used by the
 * {@link PolarCoordinate}.
 * </p>
 * <p>
 * Typical usage of SOChart is to new it and add it to some layout for
 * displaying it. Any {@link Component} that is added to the {@link Chart} will
 * be be displayed. For example, you can crate a {@link PieChart} and add it to
 * the {@link SOChart} using {@link #add(Component...)}.
 * </p>
 * 
 * <pre>
 * SOChart soChart = new SOChart();
 * soChart.setSize("600px", "400px");
 * CategoryData labels = new CategoryData("Banana", "Apple", "Orange", "Grapes");
 * Data data = new Data(25, 40, 20, 30);
 * soChart.add(new PieChart(labels, data));
 * myLayout.add(soChart);
 * </pre>
 *
 * @author Syam
 */
@JavaScript({ "vaadin://echarts-4.8.0/echarts.min.js", //
		"vaadin://sochart/sochart.js", //
		"vaadin://sochart/sochart-connector.js" })
public class SOChart extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = 3054575609878387969L;

	private static final String SKIP_DATA = "Skipping data but new data found: ";
	final public static ComponentEncoder[] encoders = { //
			new ColorEncoder(), //
			new TextStyleEncoder(), //
			new TitleEncoder(), //
			new LegendEncoder(), //
			new ToolboxEncoder(), //
			new TooltipEncoder(), //
			new DataSetEncoder(), //
			new AngleAxisEncoder(), //
			new RadiusAxisEncoder(), //
			new XAxisEncoder(), //
			new YAxisEncoder(), //
			new PolarCoordinateEncoder(), //
			new RadarCoordinateEncoder(), //
			new RectangularCoordinateEncoder(), //
			new ChartEncoder(), //
			new DataZoomEncoder(), //
	};
	private final List<Component> components = new ArrayList<>();
	private final List<ComponentPart> parts = new ArrayList<>();
	private Legend legend = new Legend();
	private Tooltip tooltip = new Tooltip();
	private boolean neverUpdated = true;
	private DefaultColors defaultColors;
	private Color defaultBackground;
	private DefaultTextStyle defaultTextStyle;
	private boolean isDataSetEncoding;

	/**
	 * Constructor.
	 */
	public SOChart() {
		getState().setProperty("idChart", "sochart" + ID.newID());
	}

	@Override
	public void attach() {
		super.attach();
		try {
			if (neverUpdated) {
				update();
			}
		} catch (Exception ignored) {
		}
	}

	/**
	 * Get the list of default colors. A list is returned and you may add any number
	 * of colors to that list. Those colors will be used sequentially and
	 * circularly. However, please note that if the list contains less than 11
	 * colors, more colors will be added to it automatically from the following to
	 * make the count 11:<BR>
	 * ['#0000ff', '#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83',
	 * '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3']
	 *
	 * @return List of default colors.
	 */
	public List<Color> getDefaultColors() {
		if (defaultColors == null) {
			defaultColors = new DefaultColors();
		}
		return defaultColors;
	}

	/**
	 * Set the default background color.
	 *
	 * @param background Background color.
	 */
	public void setDefaultBackground(Color background) {
		this.defaultBackground = background;
	}

	/**
	 * Get the default text style. You may invoke this method and override default
	 * values if required. However, please note that setting padding, border (not
	 * text border) and alignment properties do not have any effect.
	 *
	 * @return Default text style.
	 */
	public TextStyle getDefaultTextStyle() {
		if (defaultTextStyle == null) {
			defaultTextStyle = new DefaultTextStyle();
		}
		return defaultTextStyle.getTextStyle();
	}

	/**
	 * A tooltip will be shown by default. However, you can either disable it using
	 * this method or you can create your own customized tooltips and add it using
	 * {@link #add(Component...)}.
	 */
	public void disableDefaultTooltip() {
		tooltip = null;
	}

	/**
	 * Legends will be shown by default. However, you can either disable it using
	 * this method or you can create your own customized legends and add it using
	 * {@link #add(Component...)}.
	 */
	public void disableDefaultLegend() {
		legend = null;
	}

	/**
	 * Set the size.
	 *
	 * @param width  Width.
	 * @param height Height.
	 */
	public void setSize(String width, String height) {
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		getState().setProperty("width", width);
	}

	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		getState().setProperty("height", height);
	}

	public void setMinWidth(String minWidth) {
		getState().setProperty("minw", minWidth);
	}

	public void setMinHeight(String minHeight) {
		getState().setProperty("minh", minHeight);
	}

	public void setMaxWidth(String maxWidth) {
		getState().setProperty("maxw", maxWidth);
	}

	public void setMaxHeight(String maxHeight) {
		getState().setProperty("maxh", maxHeight);
	}

	/**
	 * This should be invoked only from {@link Component#addParts(SOChart)} method
	 * (That method will be called on each {@link Component} whenever chart is
	 * getting updated).
	 *
	 * @param parts Parts to add.
	 */
	public void addParts(ComponentPart... parts) {
		if (parts != null) {
			for (ComponentPart cp : parts) {
				if (cp != null) {
					this.parts.add(cp);
				}
			}
		}
	}

	/**
	 * Add components to the chart. (Chart will not be updated unless
	 * {@link #update()} method is called).
	 *
	 * @param components Components to add.
	 */
	public void add(Component... components) {
		if (components != null) {
			for (Component c : components) {
				if (c != null) {
					this.components.add(c);
				}
			}
		}
	}

	/**
	 * Remove components from the chart. (Chart will not be updated unless
	 * {@link #update()} method is called).
	 *
	 * @param components Components to remove.
	 */
	public void remove(Component... components) {
		if (components != null) {
			for (Component component : components) {
				if (component != null) {
					this.components.remove(component);
				}
			}
		}
	}

	/**
	 * Remove all components from the chart. (Chart display will not be cleared
	 * unless {@link #update()} or {@link #clear()} method is called).
	 */
	public void removeAll() {
		components.clear();
	}

	/**
	 * Clear the chart. This will remove the chart display. However, it can be
	 * rendered again by invoking {@link #update()} as long as {@link #removeAll()}
	 * is not called.
	 */
	public void clear() {
		if (neverUpdated) {
			return;
		}
//        executeJS("clearChart");
	}

	/**
	 * Update the chart display with current set of components.
	 * {@link Component#validate()} method of each component will be invoked before
	 * updating the chart display. The chart display may be already there and only
	 * the changes and additions will be updated. If a completely new display is
	 * required, {@link #clear()} should be invoked before this. (Please note that
	 * an "update" will automatically happen when a {@link SOChart} is added to its
	 * parent layout for the first time).
	 *
	 * @throws ChartException When any of the component is not valid.
	 * @throws Exception      If the JSON customizer raises any exception.
	 */
	public void update() throws ChartException, Exception {
		update(false);
	}

	/**
	 * <p>
	 * This method is same as {@link #update()} but based on the parameter passed,
	 * no data may be passed to the client-side. So, it is useful only if it is a
	 * partial update. Old set of data passed will be used for the display changes
	 * if parameter is <code>true</code>.
	 * </p>
	 * <p>
	 * Why this method is required? If the data set is really big, it will be
	 * accountable for the majority of the communication overhead and it will be
	 * useful if we can update the display with other changes if no data is changed.
	 * </p>
	 * <p>
	 * Even after eliminating the overhead of data, we can eliminate other
	 * components that are not changed via the method {@link #remove(Component...)}.
	 * </p>
	 *
	 * @param skipData Skip data or not. This parameter will be ignored if this is
	 *                 the first-time update.
	 * @throws ChartException When any of the component is not valid or new data
	 *                        found while skipping data.
	 * @throws Exception      If the JSON customizer raises any exception.
	 */
	public void update(boolean skipData) throws ChartException, Exception {
		if (neverUpdated && skipData) {
			skipData = false;
		}
		if (components.isEmpty()) {
			clear();
			return;
		}

		prepareComponents(skipData);
		setupParts(skipData);

		String options = buildOption(skipData);
		System.out.println(JsonUtil.stringify(Json.parse(options), 4));
		getState().options = customizeJSON(JsonUtil.stringify(Json.parse(options), 0));
//		executeJS("updateChart", customizeJSON(sb.toString()));

		closeUpdate();
	}

	protected void prepareComponents(boolean skipData) throws ChartException {
		for (Component component : components) {
			if (skipData) {
				if (component instanceof AbstractData) {
					if (component.getSerial() < 0) {
						throw new ChartException(SKIP_DATA + component.className());
					}
					continue;
				}
			}
			component.skippingData(skipData);
			component.validate();
			component.setSerial(-2);
		}
	}

	protected void setupParts(boolean skipData) throws ChartException {
		parts.clear();
		for (Component component : components) {
			component.addParts(this);
		}
		for (ComponentPart part : parts) {
			if (skipData) {
				if (part instanceof AbstractData) {
					if (part.getSerial() < 0) {
						throw new ChartException(SKIP_DATA + part.className());
					}
					continue;
				}
			}
			part.validate();
			part.setSerial(-2);
		}
		parts.addAll(components);
		if (defaultColors != null && !defaultColors.isEmpty()) {
			parts.add(defaultColors);
		}
		if (defaultTextStyle != null) {
			parts.add(defaultTextStyle);
		}
		if (!skipData && legend != null && parts.stream().noneMatch(cp -> cp instanceof Legend)) {
			parts.add(legend);
		}
		if (!skipData && tooltip != null && parts.stream().noneMatch(cp -> cp instanceof Tooltip)) {
			parts.add(tooltip);
		}

		setupPartSeries();

		isDataSetEncoding = isDataSetEncoding(parts);
	}

	protected void setupPartSeries() {
		for (ComponentEncoder encoder : encoders) {
			int serial = 0;
			List<ComponentPart> encodePartList = parts.stream()
					.filter(part -> encoder.support(part) && part.getSerial() == -2).collect(Collectors.toList());
			Map<ComponentPart, List<ComponentPart>> serialPartMap = encodePartList.stream()
					.collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.toList()));
			for (List<ComponentPart> serialPartList : serialPartMap.values()) {
				for (ComponentPart serialPart : serialPartList) {
					serialPart.setSerial(serial);
				}
				serial++;
			}
		}

		parts.sort(Comparator.comparing(ComponentPart::getSerial));
	}

	protected String buildOption(boolean skipData) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');

		if (defaultBackground != null) {
			sb.append("\"backgroundColor\":").append(defaultBackground);
		}

		for (ComponentEncoder encoder : encoders) {
			if (encoder instanceof DataSetEncoder && (skipData || !isDataSetEncoding)) {
				continue;
			}

			encoder.encode(sb, parts);

			if (sb.length() > 1 && sb.charAt(sb.length() - 1) != '\n') {
				sb.append('\n');
			}
		}
		sb.append('}');

		return sb.toString();
	}

	protected void closeUpdate() {
		parts.clear();
		defaultColors = null;
		defaultBackground = null;
		defaultTextStyle = null;
		neverUpdated = false;
	}

	protected boolean isNeverUpdated() {
		return neverUpdated;
	}

	/**
	 * This method is invoked just before the JSON string that is being constructed
	 * in the {@link #update()} method is sent to the client. The returned value by
	 * this method will be sent to the "echarts" instance at the client-side. The
	 * default implementation just returns the same string. However, if someone
	 * wants to do some cutting-edge customization, this method can be used. This
	 * JOSN string is used to construct the "option" parameter for the
	 * "echarts.setOption(option)" JavaScript method.
	 *
	 * @param json JSON string constructed by the {@link #update()} method.
	 * @return Customized JSON string.
	 * @throws ChartException If any custom error to be notified so that rendering
	 *                        will not happen.
	 */
//    @SuppressWarnings("RedundantThrows")
	protected String customizeJSON(String json) throws Exception {
		return json;
	}

	@Override
	protected SOChartState getState() {
		return (SOChartState) super.getState();
	}

	public void refresh() {
		try {
			clear();
			update();
		} catch (Exception e) {
		}
	}

	public static boolean isDataSetEncoding(List<ComponentPart> parts) {
		return dataProviderStream(parts).allMatch(dataProvider -> dataProvider.isDataSetEncoding());
	}

	protected static Stream<AbstractDataProvider<?>> dataProviderStream(List<ComponentPart> parts) {
		return parts.stream().filter(part -> part instanceof AbstractDataProvider)
				.map(part -> (AbstractDataProvider<?>) part);
	}
}