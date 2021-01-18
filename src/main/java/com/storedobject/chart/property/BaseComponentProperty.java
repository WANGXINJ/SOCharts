package com.storedobject.chart.property;

import static com.storedobject.chart.util.ComponentPropertyUtil.beginNode;
import static com.storedobject.chart.util.ComponentPropertyUtil.endNode;

public abstract class BaseComponentProperty implements ComponentProperty {

	final String name;
	final ComponentProperties properties = new ComponentProperties();
	final ComponentProperties customProperties = new ComponentProperties();

	public BaseComponentProperty(String name) {
		this.name = name;
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		buildProperties();
		if (isEmpty())
			return;

		beginNode(name, sb);
		encodeProperties(sb);
		endNode(sb);
	}

	final protected void buildProperties() {
		properties.clear();
		initProperties();
		properties.setAll(customProperties);
	}

	protected void initProperties() {
		// FI
	}

	public void clear() {
		properties.clear();
		customProperties.clear();
	}

	final public void setProperty(String property, Object value) {
		customProperties.set(property, value);
	}

	final public void setProperty(ComponentProperty componentProperty) {
		customProperties.set(componentProperty);
	}

	final public void setProperty(String propertyJson) {
		customProperties.set(propertyJson);
	}

	final protected void property(String property, Object value) {
		properties.set(property, value);
	}

	final protected void property(ComponentProperty componentProperty) {
		properties.set(componentProperty);
	}

	final protected void property(String propertyJson) {
		properties.set(propertyJson);
	}

	private void encodeProperties(StringBuilder sb) {
		properties.encode(sb);
	}

	protected boolean isEmpty() {
		return properties.isEmpty();
	}
}
