package com.storedobject.chart.property;

public class InnerLabelProperty extends Label {

	public InnerLabelProperty() {
		setPosition(Position.inner);
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		super.encodeJSON(sb);

		new LabelLine().setShow(false).encodeJSON(sb);
	}
}
