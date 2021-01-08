package com.storedobject.chart.util;

import static com.storedobject.chart.ComponentPart.escape;

import com.storedobject.chart.ComponentProperty;

public class ComponentPropertyUtil {

	public static StringBuilder propertyName(String property, StringBuilder sb) {
		return sb.append("\"").append(property).append("\":");
	}

	public static StringBuilder encode(String property, Object value, StringBuilder sb) {
		if (value == null) {
			return sb;
		}

		return propertyName(property, sb).append(escape(value)).append(",");
	}

	public static StringBuilder encode(String propertyJson, StringBuilder sb) {
		return sb.append(propertyJson).append(",");
	}

	public static StringBuilder encode(ComponentProperty property, StringBuilder sb) {
		property.encodeJSON(sb);
		sb.append(",");
		return sb;
	}

	public static StringBuilder beginNode(String node, StringBuilder sb) {
		return sb.append("\"").append(node).append("\":{");
	}

	public static StringBuilder endNode(StringBuilder sb) {
		return sb.append("},");
	}
}
