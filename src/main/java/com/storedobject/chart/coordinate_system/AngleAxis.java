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

import com.storedobject.chart.data.DataType;

/**
 * <p>
 * Representation of angle axis.
 * </p>
 * <p>
 * Note: {@link AngleAxis} doesn't support "name" and its associated properties.
 * So, setting it will not have any effect.
 * </p>
 *
 * @author Syam
 */
public class AngleAxis extends Axis {

	private boolean clockwise = true;
	private int startingAngle = 90;

	/**
	 * Constructor.
	 *
	 * @param dataType Data type.
	 */
	public AngleAxis(DataType dataType) {
		super(dataType);
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("clockwise", false, !clockwise);
		property("startAngle", startingAngle);
	}

	@Override
	public AxisWrapper wrap(CoordinateSystem coordinateSystem) {
		AxisWrapper w = wrappers.get(coordinateSystem);
		return w == null ? new AngleAxisWrapper(this, coordinateSystem) : w;
	}

	/**
	 * Calling this method causes the axis to be drawn in the anticlockwise
	 * direction.
	 */
	public AngleAxis anticlockwise() {
		clockwise = false;
		return this;
	}

	/**
	 * Get the starting angle.
	 *
	 * @return Angle in degrees.
	 */
	public int getStartingAngle() {
		return startingAngle;
	}

	/**
	 * Set the starting angle.
	 *
	 * @param startingAngle Angle in degrees.
	 */
	public AngleAxis setStartingAngle(int startingAngle) {
		this.startingAngle = startingAngle;
		return this;
	}

	public static class AngleAxisWrapper extends AxisWrapper {

		AngleAxisWrapper(Axis axis, CoordinateSystem coordinateSystem) {
			super(axis, coordinateSystem);
		}
	}
}
