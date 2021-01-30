package com.storedobject.chart.property;

public class InnerLabel extends LabelProperty {

	public InnerLabel() {
		innerLabel();
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		super.encodeJSON(sb);

		new LabelLine().setShow(false).encodeJSON(sb);
	}
}
