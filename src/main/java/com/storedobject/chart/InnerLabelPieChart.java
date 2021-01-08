package com.storedobject.chart;

import com.storedobject.chart.property.InnerLabelProperty;
import com.storedobject.chart.property.LabelProperty;

public class InnerLabelPieChart extends PieChart {

	public InnerLabelPieChart() {
		super.setLabelProperty(new InnerLabelProperty());
	}

	@Override
	public void setLabelProperty(LabelProperty labelProperty) {
		// NOOP
	}

}
