package com.storedobject.chart.property;

import static com.storedobject.chart.component.ComponentPart.addComma;
import static com.storedobject.chart.util.ComponentPropertyUtil.encodeComponentProperty;
import static com.storedobject.chart.util.ComponentPropertyUtil.encodeJsonProperty;
import static com.storedobject.chart.util.ComponentPropertyUtil.encodeValueProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import com.storedobject.helper.ID;

public class ComponentProperties {

	private static final String PREFIX_PROPERTY_JSON = "-json";
	private static final String PREFIX_COMPONENT_PROPERTY = "+property";

	final Map<String, Object> properties = new LinkedHashMap<>();

	public void encode(StringBuilder sb) {
		if (isEmpty())
			return;

		addComma(sb);
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith(PREFIX_COMPONENT_PROPERTY)) {
				encodeComponentProperty(((ComponentProperty) entry.getValue()), sb);
			} else if (key.startsWith(PREFIX_PROPERTY_JSON)) {
				encodeJsonProperty(((String) entry.getValue()), sb);
			} else {
				encodeValueProperty(entry.getKey(), entry.getValue(), sb);
			}
		}
	}

	final public <T> ComponentProperties set(String name, T value, Predicate<T> condition) {
		if (name != null && value != null && (condition == null || condition.test(value))) {
			properties.put(name, value);
		}
		return this;
	}

	final public ComponentProperties set(String name, Object value) {
		return set(name, value, (Predicate<Object>) null);
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
