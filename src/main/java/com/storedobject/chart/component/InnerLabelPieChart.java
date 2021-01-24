package com.storedobject.chart.component;

import com.storedobject.chart.property.InnerLabelProperty;
import com.storedobject.chart.property.Label;

public class InnerLabelPieChart extends PieChart {

	public InnerLabelPieChart() {
		super.setLabel(new InnerLabelProperty());
	}

	@Override
	public void setLabel(Label labelProperty) {
		// NOOP
	}

}
