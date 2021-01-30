package com.storedobject.chart.property;

import static com.storedobject.chart.util.ComponentPropertyUtil.beginNode;
import static com.storedobject.chart.util.ComponentPropertyUtil.endNode;

final public class PropertyComponentValueWrapper extends BaseComponentProperty {

	private PropertyComponentValue _value;

	public PropertyComponentValueWrapper(String name, PropertyComponentValue value) {
		super(name);

		_value = value;
	}

	/*
	 * Keep and replace encodeJSON when all PropertyComponentValue class are based
	 * on property implementation;
	 */
//	@Override
//	protected void buildProperties() {
//		super.buildProperties();
//		
//		property(_value.buildAndGetProperties());
//	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		_value.property(buildAndGetProperties());

		beginNode(name, sb);
		_value.encodeJSON(sb);
		endNode(sb);
	}
}
