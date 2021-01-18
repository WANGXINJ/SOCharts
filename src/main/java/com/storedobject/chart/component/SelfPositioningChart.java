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

import com.storedobject.chart.coordinate_system.CoordinateSystem;
import com.storedobject.chart.coordinate_system.HasPosition;
import com.storedobject.chart.coordinate_system.Position;
import com.storedobject.chart.data.AbstractDataProvider;

/**
 * Base class for those charts that do not require a {@link CoordinateSystem} to
 * plot on and supports its own positioning.
 *
 * @author Syam
 */
public abstract class SelfPositioningChart extends AbstractChart implements HasPosition {

	private Position position;

	/**
	 * Create a chart of a given type and data.
	 *
	 * @param type type of the chart.
	 * @param data Data to be used (multiples of them for charts that use multi-axis
	 *             coordinate systems).
	 */
	public SelfPositioningChart(ChartType type, AbstractDataProvider<?>... data) {
		super(type, data);
	}

	@Override
	public final Position getPosition(boolean create) {
		if (position == null && create) {
			position = new Position();
		}
		return position;
	}

	@Override
	public final void setPosition(Position position) {
		this.position = position;
	}
}
