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
 * Representation of X axis.
 *
 * @author Syam
 */
public class XAxis extends XYAxis {

	/**
	 * Constructor.
	 *
	 * @param dataType Data type.
	 */
	public XAxis(DataType dataType) {
		super(dataType);
	}

	@Override
	public AxisWrapper wrap(CoordinateSystem coordinateSystem) {
		AxisWrapper w = wrappers.get(coordinateSystem);
		return w == null ? new XAxisWrapper(this, coordinateSystem) : w;
	}

	@Override
	String positionString() {
		return "top";
	}

	public static class XAxisWrapper extends AxisWrapper {

		XAxisWrapper(Axis axis, CoordinateSystem coordinateSystem) {
			super(axis, coordinateSystem);
		}
	}
}
