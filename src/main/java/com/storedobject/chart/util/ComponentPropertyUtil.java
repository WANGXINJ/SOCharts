package com.storedobject.chart.util;

import static com.storedobject.chart.component.ComponentPart.escape;

import com.storedobject.chart.property.ComponentProperty;

import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;

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
		JsonObject json = Json.parse("{" + propertyJson + "}");
		propertyJson = JsonUtil.stringify(json);
		propertyJson = propertyJson.substring(1, propertyJson.length() - 1);
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
