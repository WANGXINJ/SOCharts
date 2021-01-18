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

import com.storedobject.chart.coordinate_system.RectangularCoordinate;
import com.storedobject.chart.data.AbstractDataProvider;
import com.storedobject.chart.data.DataProvider;

/**
 * Basic XY-type chart (mostly plotted on {@link RectangularCoordinate} system.
 *
 * @author Syam
 */
public abstract class XYChart extends AbstractChart {

	String stackName;

	/**
	 * Constructor.
	 *
	 * @param type  Type.
	 * @param xData Data for X axis.
	 * @param yData Data for Y axis.
	 */
	public XYChart(ChartType type, AbstractDataProvider<?> xData, DataProvider yData) {
		super(type, xData, yData);
	}

	/**
	 * Set data for X axis.
	 *
	 * @param xData Data for X axis.
	 */
	public void setXData(AbstractDataProvider<?> xData) {
		setData(xData, 0);
	}

	/**
	 * Set data for Y axis.
	 *
	 * @param yData Data for Y axis.
	 */
	public void setYData(DataProvider yData) {
		setData(yData, 1);
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		super.encodeJSON(sb);
		if (stackName != null) {
			ComponentPart.encode(sb, "stack", stackName, true);
		}
	}

	/**
	 * Certain charts (example: {@link LineChart}, {@link BarChart}) can stack
	 * multiples of them when drawn on the same coordinate system with shared axis.
	 * If stacking needs to be enabled, those charts should have the same stack
	 * name.
	 *
	 * @param stackName Stack name.
	 */
	public void setStackName(String stackName) {
		this.stackName = stackName;
	}
}