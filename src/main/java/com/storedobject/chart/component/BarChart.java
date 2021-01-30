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

import com.storedobject.chart.data.AbstractDataProvider;
import com.storedobject.chart.data.DataProvider;
import com.storedobject.chart.property.HasLabel;
import com.storedobject.chart.property.LabelProperty;

/**
 * Bar chart. (Future versions will provide more chart-specific methods).
 *
 * @author Syam
 */
public class BarChart extends XYChart implements HasLabel {

	private LabelProperty label;

	/**
	 * Constructor. (Data can be set later).
	 */
	public BarChart() {
		this(null, null);
	}

	/**
	 * Constructor.
	 *
	 * @param xData Data for X axis.
	 * @param yData Data for Y axis.
	 */
	public BarChart(AbstractDataProvider<?> xData, DataProvider yData) {
		super(ChartType.Bar, xData, yData);
	}

	@Override
	public LabelProperty getLabel(boolean create) {
		if (label == null && create) {
			label = new LabelProperty();
		}
		return label;
	}

	@Override
	public void setLabel(LabelProperty labelProperty) {
		this.label = labelProperty;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property(label);
	}
}
