package com.storedobject.chart.encoder;

import com.storedobject.chart.coordinate_system.AngleAxis;

public class AngleAxisEncoder extends ComponentEncoder {

	public AngleAxisEncoder() {
		super("angleAxis", AngleAxis.AngleAxisWrapper.class);
	}
}
