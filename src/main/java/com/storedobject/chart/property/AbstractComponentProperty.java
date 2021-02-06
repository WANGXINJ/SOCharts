package com.storedobject.chart.property;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractComponentProperty implements ComponentProperty {

	final private ComponentProperties customProperties = new ComponentProperties();
	final private ComponentProperties properties = new ComponentProperties();

	protected AbstractComponentProperty() {
	}

	public ComponentProperties getProperties() {
		return new ComponentProperties().setAll(properties).setAll(customProperties);
	}

	public ComponentProperties buildAndGetProperties() {
		buildProperties();
		return getProperties();
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

	final public AbstractComponentProperty setProperty(String name, Object value) {
		customProperties.set(name, value);
		return this;
	}

	final public <T> AbstractComponentProperty setProperty(String name, T value, Function<T, Object> mapper) {
		customProperties.set(name, value, mapper);
		return this;
	}

	final public <T> AbstractComponentProperty setProperty(String name, T value, Predicate<T> condition) {
		customProperties.set(name, value, condition);
		return this;
	}

	final public <T> AbstractComponentProperty setProperty(String name, T value, boolean condition) {
		customProperties.set(name, value, condition);
		return this;
	}

	public AbstractComponentProperty setProperty(ComponentProperty componentProperty) {
		customProperties.set(componentProperty);
		return this;
	}

	final public AbstractComponentProperty setProperty(String propertyJson) {
		customProperties.set(propertyJson);
		return this;
	}

	final public AbstractComponentProperty setProperty(ComponentProperties props) {
		customProperties.setAll(props);
		return this;
	}

	final protected void property(String name, Object value) {
		properties.set(name, value);
	}

	final protected <T> void property(String name, T value, Function<T, Object> mapper) {
		properties.set(name, value, mapper);
	}

	final protected <T> void property(String property, T value, Predicate<T> condition) {
		properties.set(property, value, condition);
	}

	final protected <T> void property(String property, T value, boolean condition) {
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

	final protected void property(ComponentProperties otherProperties) {
		properties.setAll(otherProperties);
	}

	final protected void copyProperties(AbstractComponentProperty other) {
		if (other == null)
			return;

		other.buildProperties();
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
