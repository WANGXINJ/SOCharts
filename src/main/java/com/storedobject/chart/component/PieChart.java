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
import com.storedobject.chart.property.HasPolarProperty;
import com.storedobject.chart.property.LabelProperty;
import com.storedobject.chart.property.PolarProperty;
import com.storedobject.chart.property.Size;

/**
 * Pie chart. (Future versions will provide more chart-specific methods).
 *
 * @author Syam
 */
public class PieChart extends SelfPositioningChart implements HasPolarProperty {

	private PolarProperty polarProperty;
	private LabelProperty label;

	/**
	 * Constructor.
	 */
	public PieChart() {
		this(null, null);
	}

	/**
	 * Constructor.
	 *
	 * @param itemNames Item names of the slices.
	 * @param values    Values of the slices.
	 */
	public PieChart(AbstractDataProvider<?> itemNames, DataProvider values) {
		super(ChartType.Pie, itemNames, values);
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property(polarProperty);
		property("label", label);
	}

	/**
	 * Set names of the slices.
	 *
	 * @param itemNames Item names of the slices.
	 */
	public void setItemNames(AbstractDataProvider<?> itemNames) {
		setData(itemNames, 0);
	}

	/**
	 * Set data for the slices.
	 *
	 * @param data Data.
	 */
	public void setData(DataProvider data) {
		setData(data, 1);
	}

	@Override
	public final PolarProperty getPolarProperty(boolean create) {
		if (polarProperty == null && create) {
			polarProperty = new PolarProperty();
		}
		return polarProperty;
	}

	@Override
	public final void setPolarProperty(PolarProperty polarProperty) {
		this.polarProperty = polarProperty;
	}

	public LabelProperty getLabel(boolean create) {
		if (label == null && create) {
			label = new LabelProperty();
		}
		return label;
	}

	public void setLabel(LabelProperty label) {
		this.label = label;
	}

	/**
	 * Set the hole radius (when used as a donut chart). (Basically, it is setting
	 * the inner radius of the polar).
	 *
	 * @param size Hole radius.
	 */
	public void setHoleRadius(Size size) {
		getPolarProperty(true).setInnerRadius(size);
	}
}