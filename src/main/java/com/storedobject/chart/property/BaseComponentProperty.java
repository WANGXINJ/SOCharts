package com.storedobject.chart.property;

import static com.storedobject.chart.util.ComponentPropertyUtil.beginNode;
import static com.storedobject.chart.util.ComponentPropertyUtil.endNode;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.storedobject.chart.ComponentProperty;
import com.storedobject.chart.util.ComponentPropertyUtil;
import com.storedobject.helper.ID;

public abstract class BaseComponentProperty implements ComponentProperty {

	private static final String PREFIX_PROPERTY_JSON = "-json";
	private static final String PREFIX_COMPONENT_PROPERTY = "+property";

	final String name;
	final Map<String, Object> properties = new LinkedHashMap<>();
	final Map<String, Object> customProperties = new LinkedHashMap<>();

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
		addProperties();
		properties.putAll(customProperties);
	}

	protected void addProperties() {
		// FI
	}

	public void clear() {
		properties.clear();
		customProperties.clear();
	}

	final public void addCustomProperty(String property, Object value) {
		customProperties.put(property, value);
	}

	final public void addCustomProperty(ComponentProperty componentProperty) {
		addCustomProperty(PREFIX_COMPONENT_PROPERTY + ID.newID(), componentProperty);
	}

	final public void addCustomProperty(String propertyJson) {
		addCustomProperty(PREFIX_PROPERTY_JSON + ID.newID(), propertyJson);
	}

	final protected void addProperty(String property, Object value) {
		properties.put(property, value);
	}

	final protected void addProperty(ComponentProperty componentProperty) {
		addProperty(PREFIX_COMPONENT_PROPERTY + ID.newID(), componentProperty);
	}

	final protected void addProperty(String propertyJson) {
		addProperty(PREFIX_PROPERTY_JSON + ID.newID(), propertyJson);
	}

	private void encodeProperties(StringBuilder sb) {
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith(PREFIX_COMPONENT_PROPERTY)) {
				ComponentPropertyUtil.encode(((ComponentProperty) entry.getValue()), sb);
			} else if (key.startsWith(PREFIX_PROPERTY_JSON)) {
				ComponentPropertyUtil.encode(((String) entry.getValue()), sb);
			} else {
				ComponentPropertyUtil.encode(entry.getKey(), entry.getValue(), sb);
			}
		}
	}

	protected boolean isEmpty() {
		return properties.values().stream().allMatch(Objects::isNull);
	}
}
