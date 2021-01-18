package com.storedobject.chart.encoder;

import com.storedobject.chart.coordinate_system.YAxis;

public class YAxisEncoder extends ComponentEncoder {

	public YAxisEncoder() {
		super("yAxis", YAxis.YAxisWrapper.class);
	}
}
