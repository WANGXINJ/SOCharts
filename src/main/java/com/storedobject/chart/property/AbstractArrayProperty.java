package com.storedobject.chart.property;

import static com.storedobject.chart.component.ComponentPart.addComma;
import static com.storedobject.chart.util.ComponentPropertyUtil.encodeStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractArrayProperty implements Iterable<PropertyValue> {

	final String name;
	final List<PropertyValue> valueList = new ArrayList<>();

	protected AbstractArrayProperty(String name) {
		if (name != null && (name = name.trim()).length() > 0) {
			this.name = name;
		} else {
			this.name = null;
		}
	}

	public String getName() {
		return name;
	}

	protected void encodeArray(StringBuilder sb) {
		if (isEmpty())
			return;

		addComma(sb);
		if (name != null) {
			sb.append("\"" + name + "\":");
		}
		encodeStream(sb, stream(), "[", "]", true, (strbuilder, data, index) -> {
			addComma(strbuilder);
			data.encodeValue(strbuilder);
		});
	}

	public PropertyComponentValue newPropertyValue() {
		PropertyComponentValue properties = new PropertyComponentValue();
		addPropertyValue(properties);
		return properties;
	}

	public AbstractArrayProperty addPropertyValue(PropertyValue propertyValue) {
		int index = valueList.indexOf(propertyValue);
		if (index >= 0) {
			valueList.set(index, propertyValue);
		} else {
			valueList.add(propertyValue);
		}
		return this;
	}

	public AbstractArrayProperty copyProperties(AbstractArrayProperty other) {
		valueList.addAll(other.valueList);
		return this;
	}

	@Override
	public Iterator<PropertyValue> iterator() {
		return valueList.iterator();
	}

	public Stream<PropertyValue> stream() {
		return valueList.stream();
	}

	public AbstractArrayProperty clear() {
		valueList.clear();
		return this;
	}

	public boolean isEmpty() {
		return valueList.isEmpty();
	}
}
