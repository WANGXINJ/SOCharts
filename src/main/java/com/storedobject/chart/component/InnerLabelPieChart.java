package com.storedobject.chart.component;

import com.storedobject.chart.property.InnerLabel;
import com.storedobject.chart.property.LabelProperty;

public class InnerLabelPieChart extends PieChart {

	public InnerLabelPieChart() {
		super.setLabel(new InnerLabel());
	}

	@Override
	public void setLabel(LabelProperty label) {
		// NOOP
	}
}
