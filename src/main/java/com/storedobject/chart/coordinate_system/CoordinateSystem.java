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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.storedobject.chart.component.Chart;
import com.storedobject.chart.component.Component;
import com.storedobject.chart.component.ComponentParts;
import com.storedobject.chart.component.NightingaleRoseChart;
import com.storedobject.chart.component.PieChart;
import com.storedobject.chart.component.VisiblePart;
import com.storedobject.chart.property.HasPolarProperty;
import com.storedobject.chart.util.ChartException;

/**
 * Abstract coordinate system. Most {@link Chart}s are plotted on a coordinate
 * system with the exception of charts such as {@link PieChart},
 * {@link NightingaleRoseChart} etc. One or more compatible {@link Chart}s can
 * be plotted on the same coordinate system.
 *
 * @author Syam
 */
public abstract class CoordinateSystem extends VisiblePart implements Component, HasPosition {

	final private List<Axis> axes = new ArrayList<>();
	private Position position;
	private final List<Chart> charts = new ArrayList<>();

	/**
	 * Add charts to plot on this coordinate system.
	 *
	 * @param charts Charts to be added.
	 */
	public void add(Chart... charts) {
		if (charts != null) {
			for (Chart chart : charts) {
				CoordinateSystem coordinateSystem = chart.getCoordinateSystem();
				if (coordinateSystem != null) {
					coordinateSystem.remove(chart);
				}
				chart.setCoordinateSystem(this);
				chart.setAxes(this.axes);

				this.charts.add(chart);
			}
		}
	}

	/**
	 * Remove charts to be removed from this coordinate system.
	 *
	 * @param charts Charts to be removed.
	 */
	public void remove(Chart... charts) {
		if (charts != null) {
			for (Chart chart : charts) {
				this.charts.remove(chart);

				chart.setCoordinateSystem(null);
				if (chart.getAxes() == this.axes) {
					chart.setAxes(null);
				}
			}
		}
	}

	/**
	 * Add axes.
	 *
	 * @param axes Axes to add.
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

	/**
	 * Remove axes.
	 *
	 * @param axes Axes to remove.
	 */
	public void removeAxis(Axis... axes) {
		if (axes != null) {
			for (Axis axis : axes) {
				if (axis != null) {
					this.axes.remove(axis);
				}
			}
		}
	}

	public Stream<Axis> axes(Class<?> axisClass) {
		return axes.stream().filter(a -> axisClass.isAssignableFrom(a.getClass()));
	}

	boolean noAxis(Class<?> axisClass) {
		return !axes(axisClass).findAny().isPresent();
	}

	@Override
	public void validate() throws ChartException {
		for (Axis axis : axes) {
			axis.validate();
		}
	}

	@Override
	public void addPartsInto(ComponentParts parts) {
		for (Chart chart : charts) {
			parts.add(chart);
			parts.addAll(chart.getData());
		}
		for (Axis axis : axes) {
			parts.add(axis.wrap(this));
		}
	}

	public boolean containsAxis(Axis axis) {
		return axes.contains(axis);
	}

	public List<Axis> getAxes() {
		return axes;
	}

	@Override
	public final Position getPosition(boolean create) {
		if (this instanceof HasPolarProperty) {
			return null;
		}
		if (position == null && create) {
			position = new Position();
		}
		return position;
	}

	@Override
	public final void setPosition(Position position) {
		if (this instanceof HasPolarProperty) {
			return;
		}
		this.position = position;
	}

	public String systemName() {
		return null;
	}

	public String[] axesData() {
		return null;
	}
}
