package com.storedobject.chart.property;

public class InnerLabelProperty extends LabelProperty {

	public InnerLabelProperty() {
	}

	@Override
	protected void addProperties() {
		super.addProperties();

		addProperty("position", "inner");
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		super.encodeJSON(sb);

		new LabelLineProperty().setShow(false).encodeJSON(sb);
	}
}
