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

package com.storedobject.chart.coordinate_system;

import com.storedobject.chart.SOChart;
import com.storedobject.chart.component.ComponentPart;
import com.storedobject.chart.data.DataType;
import com.storedobject.chart.encoder.ComponentEncoder;
import com.storedobject.chart.property.Area;
import com.storedobject.chart.property.Color;
import com.storedobject.chart.property.Format;
import com.storedobject.chart.property.HasFormatter;
import com.storedobject.chart.property.LineStyle;
import com.storedobject.chart.property.Location;
import com.storedobject.chart.property.Shadow;
import com.storedobject.chart.property.TextStyle;
import com.storedobject.chart.property.VisibleProperty;
import com.storedobject.chart.util.ChartException;
import com.storedobject.helper.ID;

import static com.storedobject.chart.util.ComponentPropertyUtil.encodeValueProperty;
import static com.storedobject.chart.util.ComponentPropertyUtil.nonNegativeInt;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract representation of an axis.
 *
 * @author Syam
 */
public abstract class Axis extends VisibleProperty {

	private final long id = ID.newID();
	private DataType dataType;
	Map<CoordinateSystem, AxisWrapper> wrappers = new HashMap<>();
	private String name;
	private int nameGap = 15, nameRotation = 0;
	private Location nameLocation;
	private TextStyle nameTextStyle;
	private boolean inverted = false;
	private Object min, max;
	private int divisions = 0;
	private boolean showZero = true;
	private AxisLabel label;
	private Line line;
	private MinorTicks minorTicks;
	private Ticks ticks;
	private GridLines gridLines;
	private MinorGridLines minorGridLines;
	private GridAreas gridAreas;
	private Pointer pointer;
	private Line splitLine;
	private Line minorSplitLine;

	/**
	 * Constructor.
	 *
	 * @param dataType Axis data type.
	 */
	public Axis(DataType dataType) {
		this.dataType = dataType;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		if (gridLines != null) {
			Line splitLine = getSplitLine(true);
			splitLine.setProperty(gridLines);
		}
		if (minorGridLines != null) {
			Line minorSplitLine = getMinorSplitLine();
			minorSplitLine.setProperty(minorGridLines);
		}

		property("inverse", true, inverted);
		property("type", dataType);

		if (name != null) {
			property("name", name);
			property("nameLocation", nameLocation);
			property("nameGap", nameGap);
			property("nameRotate", nameRotation);
			property("nameTextStyle", nameTextStyle);
		}

		property("axisLine", line);
		property("axisLabel", label);
		property("min", min);
		property("max", max);

		if (dataType != DataType.CATEGORY) {
			property("splitNumber", divisions, divisions > 0);
			property("scale", !showZero, min == null && max == null);
		}

		property("axisTick", ticks);
		property("minorTick", minorTicks);
		property("splitLine", splitLine);
		property("minorSplitLine", minorSplitLine);
		property("splitArea", gridAreas);
		property("axisPointer", pointer);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Axis axis = (Axis) o;
		return id == axis.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public DataType getDataType() {
		return dataType;
	}

	public boolean isDataType(DataType type) {
		return Objects.equals(dataType, type);
	}

	public boolean isDataType(Class<?> type) {
		return type.isAssignableFrom(dataType.getType());
	}

	public String axisName() {
		return axisName(getClass());
	}

	Object value(Object value) {
		return DataType.mapValue(value, dataType, type -> dataType = type);
	}

	void validate() throws ChartException {
		if (dataType == null) {
			String name = getName();
			if (name == null) {
				name = ComponentPart.className(getClass());
			}
			throw new ChartException("Unable to determine the data type for this axis - " + name);
		}
	}

	/**
	 * Get name of the axis.
	 *
	 * @return Name of the axis.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Set the name of the axis.
	 *
	 * @param name Name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Invert the axis. If called, drawing of the axis will be inverted (drawn in
	 * the opposite direction).
	 */
	public void invert() {
		inverted = true;
	}

	/**
	 * Get the gap between the axis and the name in pixels. (It can have negative
	 * values).
	 *
	 * @return Gap in pixels.
	 */
	public int getNameGap() {
		return nameGap;
	}

	/**
	 * Set the gap between the axis and the name in pixels. (It can have negative
	 * values).
	 *
	 * @param nameGap Gap in pixels.
	 */
	public void setNameGap(int nameGap) {
		this.nameGap = nameGap;
	}

	/**
	 * Get the rotation of the name in degrees.
	 *
	 * @return Rotation.
	 */
	public int getNameRotation() {
		return nameRotation;
	}

	/**
	 * Set the rotation of the name in degrees.
	 *
	 * @param nameRotation Rotation.
	 */
	public void setNameRotation(int nameRotation) {
		this.nameRotation = nameRotation;
	}

	/**
	 * Get the location of the name.
	 *
	 * @return Location of the name.
	 */
	public Location getNameLocation() {
		return nameLocation;
	}

	/**
	 * Set the location of the name.
	 *
	 * @param nameLocation Location to set.
	 */
	public void setNameLocation(Location nameLocation) {
		this.nameLocation = Location.h(nameLocation);
	}

	/**
	 * Get the text style for the name.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Text style.
	 */
	public final TextStyle getNameTextStyle(boolean create) {
		if (nameTextStyle == null && create) {
			nameTextStyle = new TextStyle();
		}
		return nameTextStyle;
	}

	/**
	 * Set the text style or the name.
	 *
	 * @param nameTextStyle Text style to set.
	 */
	public void setNameTextStyle(TextStyle nameTextStyle) {
		this.nameTextStyle = nameTextStyle;
	}

	/**
	 * Set the minimum value for the axis.
	 *
	 * @param min Minimum value. (For category axis, it could be just an ordinal
	 *            number of the category).
	 */
	public void setMin(Number min) {
		this.min = value(min);
	}

	/**
	 * By invoking this method, minimum of the axis will be set as minimum value of
	 * the data.
	 */
	public void setMinAsMinData() {
		min = "dataMin";
	}

	/**
	 * Set maximum value for the axis.
	 *
	 * @param max Maximum value. (For category axis, it could be just an ordinal
	 *            number of the category).
	 */
	public void setMax(Number max) {
		this.max = value(max);
	}

	/**
	 * By invoking this method, maximum of the axis will be set as maximum value of
	 * the data.
	 */
	public void setMaxAsMaxData() {
		max = "dataMax";
	}

	/**
	 * Set the number of divisions on the axis. This will be used as a hint only
	 * because for actual divisions will be determined based on the readability too.
	 * Also, this is not applicable to category type axes.
	 *
	 * @param divisions Number of divisions.
	 */
	public void setDivisions(int divisions) {
		this.divisions = divisions;
	}

	/**
	 * Set whether zero position to be shown or not. On certain charts, it may be
	 * required not to show zero position of the axis. Note: this setting is ignored
	 * if minimum and maximum values are already set. Also, this is not applicable
	 * to category type axes.
	 *
	 * @param show True or false.
	 */
	public void showZeroPosition(boolean show) {
		this.showZero = show;
	}

	/**
	 * Get the line.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Line.
	 */
	public final Line getLine(boolean create) {
		if (line == null && create) {
			line = new Line();
		}
		return line;
	}

	/**
	 * Set the line for the axis.
	 *
	 * @param line Line.
	 */
	public void setLine(Line line) {
		this.line = line;
	}

	/**
	 * Get the label.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Label.
	 */
	public final AxisLabel getLabel(boolean create) {
		if (label == null && create) {
			label = new AxisLabel();
		}
		return label;
	}

	/**
	 * Set the label for the axis.
	 *
	 * @param label Label.
	 */
	public void setLabel(AxisLabel label) {
		this.label = label;
	}

	/**
	 * Get the axis-line ticks.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Tick.
	 */
	public final Ticks getTicks(boolean create) {
		if (ticks == null && create) {
			ticks = new Ticks();
		}
		return ticks;
	}

	/**
	 * Set the axis-line ticks.
	 *
	 * @param ticks Ticks.
	 */
	public void setTicks(Ticks ticks) {
		this.ticks = ticks;
	}

	/**
	 * Get the minor tick of the axis-line.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Minor ticks.
	 */
	public final MinorTicks getMinorTicks(boolean create) {
		if (minorTicks == null && create) {
			minorTicks = new MinorTicks();
		}
		return minorTicks;
	}

	/**
	 * Set the minor of the axis-line.
	 *
	 * @param ticks Minor ticks.
	 */
	public void setMinorTicks(MinorTicks ticks) {
		this.minorTicks = ticks;
	}

	/**
	 * Get the grid-lines of the axis.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Grid-lines.
	 */
	public final GridLines getGridLines(boolean create) {
		if (gridLines == null && create) {
			gridLines = new GridLines();
		}
		return gridLines;
	}

	/**
	 * Set the grid-lines for the axis.
	 *
	 * @param gridLines Grid-lines to set.
	 */
	public void setGridLines(GridLines gridLines) {
		this.gridLines = gridLines;
	}

	/**
	 * Get the minor grid-lines of the axis.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Grid-lines.
	 */
	public final MinorGridLines getMinorGridLines(boolean create) {
		if (minorGridLines == null && create) {
			minorGridLines = new MinorGridLines();
		}
		return minorGridLines;
	}

	/**
	 * Set the minor grid-lines for the axis.
	 *
	 * @param gridLines Grid-lines to set.
	 */
	public void setMinorGridLines(MinorGridLines gridLines) {
		this.minorGridLines = gridLines;
	}

	/**
	 * Get the grid-areas for the axis.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Grid-areas.
	 */
	public final GridAreas getGridAreas(boolean create) {
		if (gridAreas == null && create) {
			gridAreas = new GridAreas();
		}
		return gridAreas;
	}

	/**
	 * Set the grid-areas for the axis.
	 *
	 * @param gridAreas Grid-areas to set.
	 */
	public void setGridAreas(GridAreas gridAreas) {
		this.gridAreas = gridAreas;
	}

	/**
	 * Get the axis-pointer for the axis. (By default, no axis-pointer will be
	 * shown. However, if a call is made to this method with create =
	 * <code>true</code>, axis-pointer will be made visible. Then, you may use the
	 * {@link Pointer#hide()} method to hide it if required.)
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Axis-pointer.
	 */
	public final Pointer getPointer(boolean create) {
		if (pointer == null && create) {
			pointer = new Pointer();
		}
		return pointer;
	}

	/**
	 * Set the axis-pointer for this axis.
	 *
	 * @param pointer Axis-pointer.
	 */
	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}

	public final Line getSplitLine(boolean create) {
		if (splitLine == null && create) {
			splitLine = new Line();
		}
		return splitLine;
	}

	public void setSplitLine(Line splitLine) {
		this.splitLine = splitLine;
	}

	public final Line getMinorSplitLine() {
		if (minorSplitLine == null) {
			minorSplitLine = new Line();
		}
		return minorSplitLine;
	}

	public void setMinorSplitLine(Line minorSplitLine) {
		this.minorSplitLine = minorSplitLine;
	}

	/**
	 * Definition of pointer types.
	 *
	 * @author Syam
	 */
	public enum PointerType {
		/**
		 * Pointer as a line.
		 */
		LINE,
		/**
		 * Pointer as a shadow.
		 */
		SHADOW,
		/**
		 * Pointer as a cross-hair.
		 */
		CROSS_HAIR,
		/**
		 * No pointer.
		 */
		NONE;

		public String encode() {
			return toString().toLowerCase();
		}
	}

	/**
	 * Represents the label used by {@link Axis}.
	 *
	 * @author Syam
	 */
	public abstract static class BaseAxisLabel extends TextStyle implements HasFormatter<BaseAxisLabel> {

		private boolean show = true;
		private Integer gap;
		private String formatter;

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("show", show);
			property("margin", gap);
			property("formatter", formatter);
		}

		@Override
		public void encodeJSON(StringBuilder sb) {
			if (!show) {
				encodeValueProperty("show", false, sb);
				return;
			}

			super.encodeJSON(sb);
		}

		/**
		 * Show labels.
		 */
		public void show() {
			show = true;
		}

		/**
		 * Hide labels.
		 */
		public void hide() {
			show = false;
		}

		/**
		 * Set the gap between the axis and labels.
		 *
		 * @return Gap in pixels.
		 */
		public Integer getGap() {
			return gap;
		}

		/**
		 * Set the gap between the axis and labels.
		 *
		 * @param gap Gap in pixels.
		 */
		public void setGap(Integer gap) {
			this.gap = gap;
		}

		@Override
		public String getFormatter() {
			return formatter;
		}

		@Override
		public BaseAxisLabel setFormatter(String formatter, Format... formats) {
			if (formats != null && formats.length > 0) {
				formats = new Format[] { formats[formats.length - 1].clone("value") };
			}

			this.formatter = toFormatter(formatter, formats);
			return this;
		}
	}

	/**
	 * Represents the label used by {@link Axis}.
	 *
	 * @author Syam
	 */
	public static class AxisLabel extends BaseAxisLabel {

		private int rotation = Integer.MIN_VALUE;
		private Boolean inside;
		private Boolean showMaxLabel, showMinLabel;
		private Integer interval;

		/**
		 * Constructor.
		 */
		public AxisLabel() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("inside", inside);
			if (rotation >= -90 && rotation <= 90) {
				property("rotate", rotation);
			}
			property("showMinLabel", showMinLabel);
			property("showMaxLabel", showMaxLabel);
			property("interval", interval, nonNegativeInt());
		}

		/**
		 * Get the rotation of the label.
		 *
		 * @return Rotation in degrees.
		 */
		public final int getRotation() {
			return rotation;
		}

		/**
		 * Set the rotation of the label.
		 *
		 * @param rotation Rotation in degrees. (Must be between -90 and 90).
		 */
		public void setRotation(int rotation) {
			this.rotation = rotation;
		}

		/**
		 * Check if the label is drawn inside or outside of the axis.
		 *
		 * @return True if inside.
		 */
		public Boolean isInside() {
			return inside != null && inside;
		}

		/**
		 * Setting for drawing the label inside the axis.
		 *
		 * @param inside True if inside.
		 */
		public void setInside(Boolean inside) {
			this.inside = inside;
		}

		/**
		 * Check if label for the maximum value will be displayed or not.
		 *
		 * @return True or false. <code>Null</code> value means that it will be
		 *         determined automatically to eliminate labels-overlap.
		 */
		public final Boolean isShowMaxLabel() {
			return showMaxLabel;
		}

		/**
		 * Setting for displaying the label corresponding to the maximum value.
		 *
		 * @param showMaxLabel True or false. <code>Null</code> value means that it will
		 *                     be determined automatically to eliminate labels-overlap.
		 */
		public void setShowMaxLabel(Boolean showMaxLabel) {
			this.showMaxLabel = showMaxLabel;
		}

		/**
		 * Check if label for the minimum value will be displayed or not.
		 *
		 * @return True or false. <code>Null</code> value means that it will be
		 *         determined automatically to eliminate labels-overlap.
		 */
		public final Boolean isShowMinLabel() {
			return showMinLabel;
		}

		/**
		 * Setting for displaying the label corresponding to the minimum value.
		 *
		 * @param showMinLabel True or false. <code>Null</code> value means that it will
		 *                     be determined automatically to eliminate labels-overlap.
		 */
		public void setShowMinLabel(Boolean showMinLabel) {
			this.showMinLabel = showMinLabel;
		}

		/**
		 * Get the interval between labels.
		 *
		 * @return Interval.
		 */
		public final Integer getInterval() {
			return interval;
		}

		/**
		 * Set the interval between labels.
		 *
		 * @param interval 0 means all labels, 1 means every alternate labels, 2 means
		 *                 every 2nd labels and so on. null or negative number means
		 *                 labelling will be determined automatically to eliminate
		 *                 overlap.
		 */
		public void setInterval(Integer interval) {
			this.interval = interval;
		}
	}

	/**
	 * Represents a line of the axis.
	 *
	 * @author Syam
	 */
	public static class Line extends com.storedobject.chart.property.Line {

		/**
		 * Constructor.
		 */
		public Line() {
		}
	}

	/**
	 * Represents ticks on an axis line.
	 *
	 * @author Syam
	 */
	public static abstract class AbstractTicks extends com.storedobject.chart.property.Line {

		private Integer width;

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("length", width, nonNegativeInt());
		}

		/**
		 * Get width of the tick in pixels.
		 *
		 * @return Width of the tick.
		 */
		public final Integer getWidth() {
			return width;
		}

		/**
		 * Set width of the tick in pixels.
		 *
		 * @param width Width of the tick.
		 */
		public void setWidth(Integer width) {
			this.width = width;
		}
	}

	/**
	 * Represents the minor tick on the axis line.
	 *
	 * @author Syam
	 */
	public static class MinorTicks extends AbstractTicks {

		private int divisions = 0;

		/**
		 * Constructor.
		 */
		public MinorTicks() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("splitNumber", divisions, divisions > 0);
		}

		/**
		 * Get number of divisions.
		 *
		 * @return Number of divisions.
		 */
		public final int getDivisions() {
			return divisions;
		}

		/**
		 * Set number of divisions.
		 *
		 * @param divisions Number of divisions.
		 */
		public void setDivisions(int divisions) {
			this.divisions = divisions;
		}
	}

	/**
	 * Represents the tick on the axis line.
	 *
	 * @author Syam
	 */
	public static class Ticks extends AbstractTicks {

		private Boolean inside;
		private Integer interval;
		private Boolean alignWithLabels;

		/**
		 * Constructor.
		 */
		public Ticks() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("inside", inside);
			property("interval", interval, nonNegativeInt());
			property("alignWithLabel", alignWithLabels);
		}

		/**
		 * Check if the tick is drawn inside or outside of the axis.
		 *
		 * @return True if inside.
		 */
		public Boolean isInside() {
			return inside != null && inside;
		}

		/**
		 * Setting for drawing the tick inside the axis.
		 *
		 * @param inside True if inside.
		 */
		public void setInside(Boolean inside) {
			this.inside = inside;
		}

		/**
		 * Get the interval between labels.
		 *
		 * @return Interval.
		 */
		public final Integer getInterval() {
			return interval;
		}

		/**
		 * Set the interval between labels. (If not set and if the "interval" is set for
		 * the {@link AxisLabel} of the axis, that will be used).
		 *
		 * @param interval 0 means all labels, 1 means every alternate labels, 2 means
		 *                 every 2nd labels and so on. null or negative number means
		 *                 labelling will be determined automatically to eliminate
		 *                 overlap.
		 */
		public void setInterval(Integer interval) {
			this.interval = interval;
		}

		/**
		 * Check if ticks are aligned with labels or not. (Applicable only to category
		 * type axes).
		 *
		 * @return True or false.
		 */
		public final Boolean isAlignWithLabels() {
			return alignWithLabels != null ? alignWithLabels : false;
		}

		/**
		 * Set alignment of the ticks with labels. (Applicable only to category type
		 * axes).
		 *
		 * @param alignWithLabels True or false.
		 */
		public void setAlignWithLabel(Boolean alignWithLabels) {
			this.alignWithLabels = alignWithLabels;
		}
	}

	/**
	 * Represents the grid-lines drawn by the axis.
	 *
	 * @author Syam
	 */
	public static class GridLines extends com.storedobject.chart.property.Line {

		private Integer interval;

		/**
		 * Constructor.
		 */
		public GridLines() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("interval", interval, nonNegativeInt());
		}

		/**
		 * Get the interval for the grid lines. (If not defined, interval property of
		 * the axis-label will be used).
		 *
		 * @return Interval.
		 */
		public final Integer getInterval() {
			return interval;
		}

		/**
		 * Set the interval for the grid lines. (If not defined, interval property of
		 * the axis-label will be used).
		 *
		 * @param interval 0 means all, 1 means every alternate line, 2 means every 2nd
		 *                 line and so on. null or negative number means it will be
		 *                 determined automatically.
		 */
		public void setInterval(Integer interval) {
			this.interval = interval;
		}
	}

	/**
	 * Represents the minor grid-lines drawn by the axis.
	 *
	 * @author Syam
	 */
	public static class MinorGridLines extends com.storedobject.chart.property.Line {

		/**
		 * Constructor.
		 */
		public MinorGridLines() {
		}
	}

	/**
	 * Represents the grid-areas drawn by the axis.
	 *
	 * @author Syam
	 */
	public static class GridAreas extends Area {

		private Integer interval;

		/**
		 * Constructor.
		 */
		public GridAreas() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("interval", interval, nonNegativeInt());
		}

		/**
		 * Get the interval for the grid lines. (If not defined, interval property of
		 * the axis-label will be used).
		 *
		 * @return Interval.
		 */
		public final Integer getInterval() {
			return interval;
		}

		/**
		 * Set the interval for the grid lines. (If not defined, interval property of
		 * the axis-label will be used).
		 *
		 * @param interval 0 means all, 1 means every alternate line, 2 means every 2nd
		 *                 line and so on. null or negative number means it will be
		 *                 determined automatically.
		 */
		public void setInterval(Integer interval) {
			this.interval = interval;
		}
	}

	/**
	 * Represents the axis-pointer shown by the axis. (By default no axis-pointer
	 * will be shown. However, if you create one via
	 * {@link Axis#getPointer(boolean)}, it will be displayed).
	 *
	 * @author Syam
	 */
	public static class Pointer extends VisibleProperty {

		private PointerType type;
		private Boolean snap;
		private PointerLabel label;
		private Shadow shadow;
		private LineStyle lineStyle;
		private PointerHandle handle;

		/**
		 * Constructor.
		 */
		public Pointer() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("type", type, PointerType::encode);
			property("snap", snap);
			property("label", label);
			property("shadowStyle", shadow);
			property("handle", handle);
		}

		/**
		 * Get the type of the pointer.
		 *
		 * @return Pointer-type.
		 */
		public PointerType getType() {
			return type;
		}

		/**
		 * Set the type of the pointer.
		 *
		 * @param type Pointer-type.
		 */
		public void setType(PointerType type) {
			this.type = type;
		}

		/**
		 * Get the snap attribute. This will determine whether to snap to point
		 * automatically or not. The default value is auto determined. This feature
		 * usually makes sense in value axis and time axis, where tiny points can be
		 * sought automatically.
		 *
		 * @return True/false or <code>null</code> if not set (means default).
		 */
		public Boolean getSnap() {
			return snap;
		}

		/**
		 * Set the snap attribute. (See {@link #getSnap()} to understand more about this
		 * attribute).
		 *
		 * @param snap True/false or <code>null</code>.
		 */
		public void setSnap(Boolean snap) {
			this.snap = snap;
		}

		/**
		 * Get the label of the pointer.
		 *
		 * @param create Whether to create if not exists or not.
		 * @return Pointer label.
		 */
		public final PointerLabel getLabel(boolean create) {
			if (label == null && create) {
				label = new PointerLabel();
			}
			return label;
		}

		/**
		 * Set the label for the pointer.
		 *
		 * @param label Pointer label.
		 */
		public void setLabel(PointerLabel label) {
			this.label = label;
		}

		/**
		 * Get the line-style of the pointer.
		 *
		 * @param create Whether to create if not exists or not.
		 * @return Line-style.
		 */
		public LineStyle getLineStyle(boolean create) {
			if (lineStyle == null && create) {
				lineStyle = new LineStyle();
			}
			return lineStyle;
		}

		/**
		 * Set the line-style of the pointer.
		 *
		 * @param lineStyle Line-style.
		 */
		public void setLineStyle(LineStyle lineStyle) {
			this.lineStyle = lineStyle;
		}

		/**
		 * Get the shadow-style of the pointer.
		 *
		 * @param create Whether to create if not exists or not.
		 * @return Shadow.
		 */
		public final Shadow getShadow(boolean create) {
			if (shadow == null && create) {
				shadow = new Shadow();
			}
			return shadow;
		}

		/**
		 * Set the shadow-style of the pointer.
		 *
		 * @param shadow Shadow.
		 */
		public void setShadow(Shadow shadow) {
			this.shadow = shadow;
		}

		/**
		 * Get the pointer-handle.
		 *
		 * @param create Whether to create if not exists or not.
		 * @return Pointer-handle.
		 */
		public final PointerHandle getHandle(boolean create) {
			if (handle == null && create) {
				handle = new PointerHandle();
			}
			return handle;
		}

		/**
		 * Set the pointer-handle.
		 *
		 * @param handle Pointer-handle.
		 */
		public void setHandle(PointerHandle handle) {
			this.handle = handle;
		}
	}

	/**
	 * Represents the label that is displayed by the axis-pointer.
	 *
	 * @author Syam
	 */
	public static class PointerLabel extends BaseAxisLabel {

		private int precision = -1;

		/**
		 * Constructor.
		 */
		public PointerLabel() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("precision", precision, precision >= 0);
			property("precision", "auto", precision < 0);
		}

		/**
		 * Get the precision for showing the value (Applicable to numeric values).
		 *
		 * @return Precision (Number of digits). A value of -1 means it will be
		 *         determined automatically).
		 */
		public int getPrecision() {
			return precision;
		}

		/**
		 * Set the precision for showing the value (Applicable to numeric values).
		 *
		 * @param precision Precision (Number of digits). A value of -1 means it will be
		 *                  determined automatically).
		 */
		public void setPrecision(int precision) {
			this.precision = precision;
		}
	}

	/**
	 * Represent the handle that can be used with axis-pointer. (This is useful
	 * mainly for touch-devices).
	 *
	 * @author Syam
	 */
	public static class PointerHandle extends VisibleProperty {

		private int width = -1, height = -1, gap = -1;
		private Shadow shadow;
		private Color color;

		/**
		 * Constructor.
		 */
		public PointerHandle() {
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			if (width > 0 || height > 0) {
				int w = width, h = height;
				if (w <= 0) {
					w = 45;
				}
				if (h <= 0) {
					h = 45;
				}
				property("size", w, w == h);
				property("size", new int[] { w, h }, w != h);

				property("margin", gap, gap >= 0);
			}
			property("color", color);
			property(shadow);
		}

		/**
		 * Get the width in pixels.
		 *
		 * @return Width.
		 */
		public int getWidth() {
			return width;
		}

		/**
		 * Set the width in pixels.
		 *
		 * @param width Width.
		 */
		public void setWidth(int width) {
			this.width = width;
		}

		/**
		 * Get the height in pixels.
		 *
		 * @return Height.
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * Set the height in pixels.
		 *
		 * @param height Height.
		 */
		public void setHeight(int height) {
			this.height = height;
		}

		/**
		 * Set the size in pixes (setting to both width and height).
		 *
		 * @param size Size in pixels.
		 */
		public void setSize(int size) {
			width = size;
			height = size;
		}

		/**
		 * Set the gap between the axis and the handle.
		 *
		 * @return Gap in pixels.
		 */
		public int getGap() {
			return gap;
		}

		/**
		 * Set the gap between the axis and handle.
		 *
		 * @param gap Gap in pixels.
		 */
		public void setGap(int gap) {
			this.gap = gap;
		}

		/**
		 * Get the color of the handle.
		 *
		 * @return Color
		 */
		public Color getColor() {
			return color;
		}

		/**
		 * Set the color of the handle.
		 *
		 * @param color Color.
		 */
		public void setColor(Color color) {
			this.color = color;
		}

		/**
		 * Get the shadow-style of the pointer-handle.
		 *
		 * @param create Whether to create if not exists or not.
		 * @return Shadow.
		 */
		public final Shadow getShadow(boolean create) {
			if (shadow == null && create) {
				shadow = new Shadow();
			}
			return shadow;
		}

		/**
		 * Set the shadow-style of the pointer-handle.
		 *
		 * @param shadow Shadow.
		 */
		public void setShadow(Shadow shadow) {
			this.shadow = shadow;
		}

	}

	public abstract ComponentPart wrap(CoordinateSystem coordinateSystem);

	public static class AxisWrapper implements ComponentPart {

		private int serial;
		private final long id = ID.newID();
		final private Axis axis;
		private final CoordinateSystem coordinateSystem;

		AxisWrapper(Axis axis, CoordinateSystem coordinateSystem) {
			this.axis = axis;
			this.coordinateSystem = coordinateSystem;
			this.axis.wrappers.put(this.coordinateSystem, this);
		}

		@Override
		public final long getId() {
			return id;
		}

		public Axis getAxis() {
			return axis;
		}

		@Override
		public void encodeJSON(StringBuilder sb) {
			encodeValueProperty("id", id, sb);
			for (ComponentEncoder encoder : SOChart.encoders) {
				if (!encoder.exact(coordinateSystem)) {
					continue;
				}
				encodeValueProperty(encoder.getLabel() + "Index", coordinateSystem.getSerial(), sb);
				break;
			}
			axis.encodeJSON(sb);
		}

		@Override
		public final int getSerial() {
			return serial;
		}

		@Override
		public final void setSerial(int serial) {
			this.serial = serial;
		}

		@Override
		public void validate() throws ChartException {
			axis.validate();
		}
	}

	public static String axisName(Class<? extends Axis> axisClass) {
		String name = axisClass.getSimpleName();
		return Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}
}