package com.storedobject.chart.property;

import static com.storedobject.chart.property.PropertyValue.toProperty;

import java.util.function.Predicate;

public abstract class AbstractComponentProperty implements ComponentProperty {

	final private ComponentProperties customProperties = new ComponentProperties();
	final private ComponentProperties properties = new ComponentProperties();

	protected AbstractComponentProperty() {
	}

	public ComponentProperties getProperties() {
		return new ComponentProperties().setAll(properties).setAll(customProperties);
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		buildProperties();
		if (isEmpty())
			return;

		encodeProperties(sb);
	}

	final protected void encodeProperties(StringBuilder sb) {
		getProperties().encode(sb);
	}

	protected void buildProperties() {
		properties.clear();
	}

	final public <T> AbstractComponentProperty setProperty(String name, T value, Predicate<T> condition) {
		customProperties.set(name, value, condition);
		return this;
	}

	final public AbstractComponentProperty setProperty(String name, PropertyValue propertyValue) {
		return setProperty(toProperty(name, propertyValue));
	}

	final public AbstractComponentProperty setProperty(String name, Object value) {
		customProperties.set(name, value);
		return this;
	}

	final public AbstractComponentProperty setProperty(ComponentProperty componentProperty) {
		customProperties.set(componentProperty);
		return this;
	}

	final public AbstractComponentProperty setProperty(String propertyJson) {
		customProperties.set(propertyJson);
		return this;
	}

	final protected void property(String name, PropertyValue propertyValue) {
		property(name, propertyValue, null);
	}

	final protected <T extends PropertyValue> void property(String name, T propertyValue, Predicate<T> condition) {
		if (propertyValue == null || condition != null && !condition.test(propertyValue))
			return;

		property(toProperty(name, propertyValue));
	}

	final protected void property(String name, Object value) {
		properties.set(name, value);
	}

	final protected <T> void property(String property, T value, Predicate<T> condition) {
		properties.set(property, value, condition);
	}

	final protected void property(PropertyComponentValue propertyValue) {
		copyProperties(propertyValue);
	}

	final protected void property(ComponentProperty componentProperty) {
		properties.set(componentProperty);
	}

	final protected void property(String propertyJson) {
		properties.set(propertyJson);
	}

	final protected void copyProperties(AbstractComponentProperty other) {
		customProperties.setAll(other.customProperties);
		properties.setAll(other.properties);
	}

	public void clear() {
		customProperties.clear();
		properties.clear();
	}

	public boolean isEmpty() {
		return properties.isEmpty() && customProperties.isEmpty();
	}
}
