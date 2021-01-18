package com.storedobject.chart.property;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.storedobject.chart.component.ComponentPart;
import com.storedobject.chart.util.ComponentPropertyUtil;
import com.storedobject.helper.ID;

public class ComponentProperties {

	private static final String PREFIX_PROPERTY_JSON = "-json";
	private static final String PREFIX_COMPONENT_PROPERTY = "+property";

	final Map<String, Object> properties = new LinkedHashMap<>();

	public void encode(StringBuilder sb) {
		ComponentPart.addComma(sb);

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

	final public ComponentProperties set(String property, Object value) {
		properties.put(property, value);
		return this;
	}

	final public ComponentProperties set(ComponentProperty componentProperty) {
		return set(PREFIX_COMPONENT_PROPERTY + ID.newID(), componentProperty);
	}

	final public ComponentProperties set(String propertyJson) {
		return set(PREFIX_PROPERTY_JSON + ID.newID(), propertyJson);
	}

	public ComponentProperties setAll(ComponentProperties props) {
		properties.putAll(props.properties);
		return this;
	}

	public ComponentProperties clear() {
		properties.clear();
		return this;
	}

	public boolean isEmpty() {
		return properties.values().stream().allMatch(Objects::isNull);
	}
}
