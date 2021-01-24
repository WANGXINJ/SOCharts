package com.storedobject.chart.property;

public class BaseArrayProperty extends AbstractArrayProperty implements ComponentProperty {

	public BaseArrayProperty(String name) {
		super(name);
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		encodeArray(sb);
	}
}
