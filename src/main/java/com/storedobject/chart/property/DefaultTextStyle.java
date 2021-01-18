package com.storedobject.chart.property;

import com.storedobject.chart.component.ComponentPart;

public class DefaultTextStyle implements ComponentPart {

	private int serial;
	private final TextStyle textStyle = new TextStyle();

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public void validate() {
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		TextStyle.OuterProperties op = new TextStyle.OuterProperties();
		textStyle.save(op);
		ComponentPart.encodeProperty(sb, textStyle);
	}

	@Override
	public final int getSerial() {
		return serial;
	}

	@Override
	public void setSerial(int serial) {
		this.serial = serial;
	}

	public TextStyle getTextStyle() {
		return textStyle;
	}
}
