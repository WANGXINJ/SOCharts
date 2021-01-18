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

import com.storedobject.chart.component.ComponentPart;
import com.storedobject.chart.data.DataType;

/**
 * Representation of radius axis.
 *
 * @author Syam
 */
public class RadiusAxis extends Axis {

	/**
	 * Constructor.
	 *
	 * @param dataType Data type.
	 */
	public RadiusAxis(DataType dataType) {
		super(dataType);
	}

	@Override
	public ComponentPart wrap(CoordinateSystem coordinateSystem) {
		AxisWrapper w = wrappers.get(coordinateSystem);
		return w == null ? new RadiusAxisWrapper(this, coordinateSystem) : w;
	}

	public static class RadiusAxisWrapper extends AxisWrapper {

		RadiusAxisWrapper(Axis axis, CoordinateSystem coordinateSystem) {
			super(axis, coordinateSystem);
		}
	}
}
