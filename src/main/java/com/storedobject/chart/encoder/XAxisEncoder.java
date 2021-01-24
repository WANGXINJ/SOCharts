package com.storedobject.chart.encoder;

import com.storedobject.chart.coordinate_system.XAxis;

public class XAxisEncoder extends AxisEncoder {

	public XAxisEncoder() {
		super("xAxis", XAxis.XAxisWrapper.class);
	}
}
