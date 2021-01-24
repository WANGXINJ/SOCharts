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

import com.storedobject.chart.coordinate_system.Axis;
import com.storedobject.chart.coordinate_system.CoordinateSystem;
import com.storedobject.chart.data.DataType;
import com.storedobject.chart.property.AbstractComponentProperty;
import com.storedobject.chart.util.ChartException;
import com.storedobject.helper.ID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Represents abstract "data zoom" component. Data zoom components allow the
 * end-users to zoom in and to zoom out charts using mouse and/or touch devices.
 *
 * @author Syam
 */
public abstract class AbstractDataZoom extends AbstractComponentProperty implements Component {

	private int serial;
	private final long id = ID.newID();
	private final List<Axis> axes = new ArrayList<>();
	private final CoordinateSystem coordinateSystem;
	private boolean explicitAxisIndex;
	private FilterMode filterMode;
	private int start = Integer.MIN_VALUE, end = Integer.MAX_VALUE;
	private Object startValue, endValue;
	private int minSpan = Integer.MIN_VALUE, maxSpan = Integer.MAX_VALUE;
	private Object minSpanValue, maxSpanValue;
	private Boolean zoomLock;

	/**
	 * Constructor.
	 *
	 * @param type             Type.
	 * @param coordinateSystem Coordinate system.
	 * @param axes             Axis list.
	 */
	protected AbstractDataZoom(CoordinateSystem coordinateSystem, Axis... axes) {
		this.coordinateSystem = coordinateSystem;
		addAxis(axes);
	}

	@Override
	public final long getId() {
		return id;
	}

	public abstract DataZoomType getType();

	public boolean isType(DataZoomType type) {
		return Objects.equals(getType(), type);
	}

	public boolean isForAxis(Axis axis) {
		return axis != null ? axes.contains(axis) : axes.isEmpty();
	}

	/**
	 * Add list of axes.
	 *
	 * @param axes Axis list.
	 */
	public void addAxis(Axis... axes) {
		if (axes != null) {
			for (Axis axis : axes) {
				if (axis != null && !this.axes.contains(axis)) {
					this.axes.add(axis);
				}
			}
		}
	}

	@Override
	public void validate() throws ChartException {
		if (coordinateSystem.getSerial() < 0) {
			throw new ChartException("Coordinate system is not used");
		}
		for (Axis axis : this.axes) {
			if (!coordinateSystem.containsAxis(axis)) {
				String name = axis.getName();
				if (name == null) {
					name = ComponentPart.className(axis.getClass());
				}
				throw new ChartException("Axis " + name + " doesn't belong to the coordinate system of this");
			}
		}
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("id", id);
		property("type", getType());

		List<Axis> axes = new ArrayList<>(this.axes);
		if (axes.isEmpty() && explicitAxisIndex) {
			axes.addAll(coordinateSystem.getAxes());
		}
		axes.stream().map(Axis::getClass).forEach(axisClass -> {
			property(Axis.axisName(axisClass) + "Index", axes.stream().filter(axis -> axis.getClass() == axisClass)
					.mapToInt(axis -> axis.wrap(coordinateSystem).getSerial()).toArray());
		});

		property("filterMode", filterMode);

		if (start != Integer.MIN_VALUE || end != Integer.MAX_VALUE || startValue != null || endValue != null) {
			String[] rangeMode = new String[2];
			rangeMode[0] = startValue != null ? "value" : "percent";
			rangeMode[1] = endValue != null ? "value" : "percent";
			property("rangeMode", rangeMode);
		}

		if (start != Integer.MIN_VALUE) {
			property("start", start);
			if (start == 0) {
				start = Integer.MIN_VALUE;
			}
		}
		if (end != Integer.MAX_VALUE) {
			property("end", end);
			if (end == 100) {
				end = Integer.MAX_VALUE;
			}
		}
		property("startValue", startValue);
		property("endValue", endValue);

		if (minSpan != Integer.MIN_VALUE) {
			property("minSpan", minSpan);
			if (minSpan == 0) {
				minSpan = Integer.MIN_VALUE;
			}
		}
		if (maxSpan != Integer.MAX_VALUE) {
			property("maxSpan", maxSpan);
			if (maxSpan == 100) {
				maxSpan = Integer.MAX_VALUE;
			}
		}
		property("minValueSpan", minSpanValue);
		property("maxValueSpan", maxSpanValue);

		property("zoomLock", zoomLock);
	}

	@Override
	public final int getSerial() {
		return serial;
	}

	@Override
	public final void setSerial(int serial) {
		this.serial = serial;
	}

	public void explicitAxisIndex() {
		explicitAxisIndex = true;
	}

	public void implicitAxisIndex() {
		explicitAxisIndex = false;
	}

	/**
	 * Get the filter mode. (See {@link #setFilterMode(FilterMode)}).
	 *
	 * @return Get the current filter mode.
	 */
	public final FilterMode getFilterMode() {
		return filterMode != null ? filterMode : FilterMode.filter;
	}

	public void setFilterMode(FilterMode filterMode) {
		if (this.filterMode == null && filterMode == FilterMode.filter)
			return;

		this.filterMode = filterMode;
	}

	/**
	 * <p>
	 * Set the filter mode.
	 * </p>
	 * <p>
	 * 0: Do not filter data.
	 * </p>
	 * <p>
	 * 1: Data that outside the window will be set to NaN, which will not lead to
	 * changes of the window of other axes.
	 * </p>
	 * <p>
	 * 2: Data that outside the window will be filtered, which may lead to some
	 * changes of windows of other axes. For each data item, it will be filtered
	 * only if all of the relevant dimensions are out of the same side of the
	 * window.
	 * </p>
	 * <p>
	 * 3: Data that outside the window will be filtered, which may lead to some
	 * changes of windows of other axes. For each data item, it will be filtered if
	 * one of the relevant dimensions is out of the window. This is the default
	 * value.
	 * </p>
	 * <p>
	 * Note: Setting other values will be ignored.
	 * </p>
	 *
	 * @param filterMode Filter mode.
	 */
	public void setFilterMode(int filterMode) {
		setFilterMode(FilterMode.valueOf(filterMode));
	}

	/**
	 * The start percentage of the window out of the data extent, in the range of 0
	 * ~ 100.
	 *
	 * @return Start percentage.
	 */
	public final int getStart() {
		return start == Integer.MIN_VALUE ? 0 : start;
	}

	/**
	 * The start percentage of the window out of the data extent, in the range of 0
	 * ~ 100. (Value set by setStartValue(...) methods will not be effective if this
	 * method is invoked).
	 *
	 * @param start Start percentage.
	 */
	public void setStart(int start) {
		this.start = (start >= 0 && start < 100) ? start : Integer.MIN_VALUE;
		startValue = null;
	}

	/**
	 * The end percentage of the window out of the data extent, in the range of 0 ~
	 * 100.
	 *
	 * @return End percentage.
	 */
	public final int getEnd() {
		return end;
	}

	/**
	 * The end percentage of the window out of the data extent, in the range of 0 ~
	 * 100.
	 *
	 * @param end End percentage.
	 */
	public void setEnd(int end) {
		this.end = (end > 0 && end <= 100) ? end : Integer.MAX_VALUE;
	}

	/**
	 * The absolute start value of the window.
	 *
	 * @return Start value.
	 */
	public final Object getStartValue() {
		return startValue;
	}

	/**
	 * The absolute start value of the window. (Value set by {@link #setStart(int)}
	 * will not be effective if this method is invoked).
	 *
	 * @param startValue Start value. (Can be index value for category data).
	 */
	public void setStartValue(Number startValue) {
		this.startValue = startValue;
		start = Integer.MIN_VALUE;
	}

	/**
	 * The absolute start value of the window. (Value set by {@link #setStart(int)}
	 * will not be effective if this method is invoked).
	 *
	 * @param startValue Start value. (Used for {@link DataType#DATE}).
	 */
	public void setStartValue(LocalDate startValue) {
		this.startValue = startValue;
		start = Integer.MIN_VALUE;
	}

	/**
	 * The absolute start value of the window. (Value set by {@link #setStart(int)}
	 * will not be effective if this method is invoked).
	 *
	 * @param startValue Start value. (Used for {@link DataType#TIME}).
	 */
	public void setStartValue(LocalDateTime startValue) {
		this.startValue = startValue;
		start = Integer.MIN_VALUE;
	}

	/**
	 * The absolute end value of the window.
	 *
	 * @return End value.
	 */
	public final Object getEndValue() {
		return endValue;
	}

	/**
	 * The absolute end value of the window. (Value set by {@link #setEnd(int)} will
	 * not be effective if this method is invoked).
	 *
	 * @param endValue End value. (Can be index value for category data).
	 */
	public void setEndValue(Number endValue) {
		this.endValue = endValue;
		end = Integer.MAX_VALUE;
	}

	/**
	 * The absolute end value of the window. (Value set by {@link #setEnd(int)} will
	 * not be effective if this method is invoked).
	 *
	 * @param endValue End value. (Used for {@link DataType#DATE}).
	 */
	public void setEndValue(LocalDate endValue) {
		this.endValue = endValue;
		end = Integer.MAX_VALUE;
	}

	/**
	 * The absolute end value of the window. (Value set by {@link #setEnd(int)} will
	 * not be effective if this method is invoked).
	 *
	 * @param endValue End value. (Used for {@link DataType#TIME}).
	 */
	public void setEndValue(LocalDateTime endValue) {
		this.endValue = endValue;
		end = Integer.MAX_VALUE;
	}

	/**
	 * The minimum span percentage value of the window out of the data extent, in
	 * the range of 0 ~ 100.
	 *
	 * @return Minimum span percentage value.
	 */
	public final int getMinSpan() {
		return minSpan == Integer.MIN_VALUE ? 0 : minSpan;
	}

	/**
	 * The minimum span percentage value of the window out of the data extent, in
	 * the range of 0 ~ 100. (Value set by setMinSpanValue(...) methods will not be
	 * effective if this method is invoked).
	 *
	 * @param minSpan Minimum span percentage value.
	 */
	public void setMinSpan(int minSpan) {
		this.minSpan = (minSpan >= 0 && minSpan < 100) ? minSpan : Integer.MIN_VALUE;
		minSpanValue = null;
	}

	/**
	 * The maximum span percentage value of the window out of the data extent, in
	 * the range of 0 ~ 100.
	 *
	 * @return MaxSpan Maximum span percentage value.
	 */
	public final int getMaxSpan() {
		return maxSpan;
	}

	/**
	 * The maximum span percentage value of the window out of the data extent, in
	 * the range of 0 ~ 100.
	 *
	 * @param maxSpan Maximum span percentage value.
	 */
	public void setMaxSpan(int maxSpan) {
		this.maxSpan = (maxSpan > 0 && maxSpan <= 100) ? maxSpan : Integer.MAX_VALUE;
	}

	/**
	 * The absolute minimum span value of the window.
	 *
	 * @return Minimum span value.
	 */
	public final Object getMinSpanValue() {
		return minSpanValue;
	}

	/**
	 * The absolute minimum span value of the window. (Value set by
	 * {@link #setMinSpan(int)} will not be effective if this method is invoked).
	 *
	 * @param minSpanValue Minimum span value.
	 */
	public void setMinSpanValue(Number minSpanValue) {
		this.minSpanValue = minSpanValue;
		minSpan = Integer.MIN_VALUE;
	}

	/**
	 * The absolute minimum span value of the window. (Value set by
	 * {@link #setMinSpan(int)} will not be effective if this method is invoked).
	 *
	 * @param minSpanValue Minimum span value. (Used for {@link DataType#DATE}).
	 */
	public void setMinSpanValue(LocalDate minSpanValue) {
		this.minSpanValue = minSpanValue;
		minSpan = Integer.MIN_VALUE;
	}

	/**
	 * The absolute minimum span value of the window. (Value set by
	 * {@link #setMinSpan(int)} will not be effective if this method is invoked).
	 *
	 * @param minSpanValue Minimum span value. (Used for {@link DataType#TIME}).
	 */
	public void setMinSpanValue(LocalDateTime minSpanValue) {
		this.minSpanValue = minSpanValue;
		minSpan = Integer.MIN_VALUE;
	}

	/**
	 * The absolute maximum span value of the window.
	 *
	 * @return Maximum span value.
	 */
	public final Object getMaxSpanValue() {
		return maxSpanValue;
	}

	/**
	 * The absolute maximum span value of the window. (Value set by
	 * {@link #setMaxSpan(int)} will not be effective if this method is invoked).
	 *
	 * @param maxSpanValue Maximum span value.
	 */
	public void setMaxSpanValue(Number maxSpanValue) {
		this.maxSpanValue = maxSpanValue;
		maxSpan = Integer.MAX_VALUE;
	}

	/**
	 * The absolute maximum span value of the window. (Value set by
	 * {@link #setMaxSpan(int)} will not be effective if this method is invoked).
	 *
	 * @param maxSpanValue Maximum span value. (Used for {@link DataType#DATE}).
	 */
	public void setMaxSpanValue(LocalDate maxSpanValue) {
		this.maxSpanValue = maxSpanValue;
		maxSpan = Integer.MAX_VALUE;
	}

	/**
	 * The absolute maximum span value of the window. (Value set by
	 * {@link #setMaxSpan(int)} will not be effective if this method is invoked).
	 *
	 * @param maxSpanValue Maximum span value. (Used for {@link DataType#TIME}).
	 */
	public void setMaxSpanValue(LocalDateTime maxSpanValue) {
		this.maxSpanValue = maxSpanValue;
		maxSpan = Integer.MAX_VALUE;
	}

	/**
	 * Check whether zoom lock is set or not. (See {@link #setZoomLock(boolean)} for
	 * details).
	 *
	 * @return True or false.
	 */
	public final boolean isZoomLock() {
		return zoomLock;
	}

	/**
	 * <p>
	 * Set the zoom lock.
	 * </p>
	 * <p>
	 * When set as true, the size of window is locked, that is, only the translation
	 * (by mouse drag or touch drag) is available.
	 * </p>
	 *
	 * @param zoomLock True or false.
	 */
	public void setZoomLock(boolean zoomLock) {
		this.zoomLock = zoomLock;
	}

	public static enum DataZoomType {
		inside((coordinate, axes) -> new CoordinateSystemZoom(coordinate, axes)), //
		slider((coordinate, axes) -> new DataZoom(coordinate, axes)) //
		;

		final private BiFunction<CoordinateSystem, Axis[], ? extends AbstractDataZoom> creater;

		private DataZoomType(BiFunction<CoordinateSystem, Axis[], ? extends AbstractDataZoom> creater) {
			this.creater = creater;
		}

		public <ZOOM extends AbstractDataZoom> ZOOM newDataZoom(CoordinateSystem coordinate, Axis... axes) {
			@SuppressWarnings("unchecked")
			ZOOM zoom = (ZOOM) creater.apply(coordinate, axes);
			return zoom;
		}
	}

	public static enum FilterMode {
		none, //
		empty, //
		weakFilter, //
		filter, //
		;

		public static FilterMode valueOf(int index) {
			FilterMode[] modes = values();
			if (index >= 0 && index < modes.length) {
				return modes[index];
			}

			return filter;
		}
	}
}
