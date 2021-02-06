package com.storedobject.chart.util;

import static com.storedobject.chart.component.ComponentPart.addComma;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.storedobject.chart.property.ComponentProperty;
import com.storedobject.chart.property.PropertyValue;
import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;

public class ComponentPropertyUtil {

	public static final SimpleDateFormat ISO_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public static StringBuilder encodeValueProperty(String name, Object value, StringBuilder sb) {
		if (value == null) {
			return sb;
		}

		addComma(sb);
		return sb.append("\"").append(name).append("\":").append(escape(value)).append(",");
	}

	public static <T> StringBuilder encodeValueProperty(String name, T value, Function<T, Object> mapper,
			StringBuilder sb) {
		if (value == null) {
			return sb;
		}

		return encodeValueProperty(name, mapper.apply(value), sb);
	}

	public static StringBuilder encodeComponentProperty(ComponentProperty property, StringBuilder sb) {
		if (property == null) {
			return sb;
		}

		addComma(sb);
		property.encodeJSON(sb);
		addComma(sb);

		return sb;
	}

	public static StringBuilder encodeComponentProperty(String name, ComponentProperty property, StringBuilder sb) {
		if (property == null) {
			return sb;
		}

		beginNode(name, sb);
		property.encodeJSON(sb);
		endNode(sb);

		return sb;
	}

	public static StringBuilder encodeJsonProperty(String propertyJson, StringBuilder sb) {
		if (propertyJson == null || propertyJson.isEmpty()) {
			return sb;
		}

		addComma(sb);
		JsonObject json = Json.parse("{" + propertyJson + "}");
		propertyJson = JsonUtil.stringify(json);
		propertyJson = propertyJson.substring(1, propertyJson.length() - 1);
		return sb.append(propertyJson).append(",");
	}

	public static StringBuilder beginNode(String node, StringBuilder sb) {
		if (node == null || node.isEmpty()) {
			return sb;
		}

		addComma(sb);
		return sb.append("\"").append(node).append("\":{");
	}

	public static StringBuilder beginValueNode(StringBuilder sb) {
		addComma(sb);
		return sb.append("{");
	}

	public static StringBuilder endNode(StringBuilder sb) {
		return sb.append("},");
	}

	/**
	 * Append the JSON encoding of all values coming from a stream to the given
	 * string builder.
	 *
	 * @param sb           Append the JSONified string to this.
	 * @param dataStream   Sream of data values.
	 * @param prefix       Prefix to add.
	 * @param suffix       Suffix to add.
	 * @param appendAnyway Append prefix and suffix even if data is empty.
	 * @param dataEncoder  Encoder for the value read from the stream. If
	 *                     <code>null</code> is passed, stringified version will be
	 *                     appended.
	 * @param <DATA>       Type of data value in the stream.
	 */
	public static <DATA> void encodeStream(StringBuilder sb, Stream<DATA> dataStream, String prefix, String suffix,
			boolean appendAnyway, TriConsumer<StringBuilder, DATA, Integer> dataEncoder) {
		AtomicInteger index = new AtomicInteger(-1);
		dataStream.forEach(data -> {
			boolean first = index.incrementAndGet() == 0;
			if (first) {
				sb.append(prefix);
			} else {
				addComma(sb);
			}

			dataEncoder.accept(sb, data, index.get());
		});

		if (index.get() == -1) {
			if (appendAnyway) {
				sb.append(prefix).append(suffix);
			}
		} else {
			sb.append(suffix);
		}
	}

	/**
	 * Helper method to escape invalid characters in JSON strings. Please note that
	 * this method returns a double-quoted string unless the parameter is a number.
	 * For example, escape("Hello") will return "Hello" not Hello.
	 *
	 * @param any Anything to encode.
	 * @return Encoded string.
	 */
	public static String escape(Object any) {
		if (any.getClass().isArray()) {
			any = Arrays.asList(toObjectArray(any));
		}
		if (any instanceof Iterable) {
			StringBuilder sb = new StringBuilder();
			Iterable<?> iter = (Iterable<?>) any;
			int size;
			if (iter instanceof Collection) {
				size = ((Collection<?>) iter).size();
			} else {
				size = (int) iter.spliterator().getExactSizeIfKnown();
			}

			if (size > 1) {
				sb.append("[");
				boolean first = true;
				for (Object obj : iter) {
					if (first) {
						first = false;
					} else {
						sb.append(',');
					}
					sb.append(escape(obj));
				}
				sb.append("]");
				return sb.toString();

			} else if (size == 1) {
				any = iter.iterator().next();

			} else {
				any = null;
			}
		}

		if (any instanceof Number || any instanceof Boolean || any instanceof PropertyValue) {
			return any.toString();
		}

		if (any instanceof Date) {
			any = ISO_DATE_FORMATTER.format((Date) any);
//			any = DataType.TIME.mapValue(any); 
		}

		String string = any != null ? any.toString() : null;
		if (string == null) {
			string = "";
		}
		if (string.startsWith("\"") && string.endsWith("\"")) {
			return string; // Special case - already encoded.
		}
		if (string.contains("\"")) {
			string = string.replace("\"", "\\\"");
		}
		if (string.contains("\n")) {
			string = string.replace("\n", "\\n");
		}
		return '"' + string + '"';
	}

	public static String camelName(boolean firstLower, boolean lowerCase, String... names) {
		String camelName = null;
		for (String name : names) {
			int length = name != null ? name.length() : 0;
			if (length == 0)
				continue;

			boolean first = camelName == null;
			String capital = name.substring(0, 1);
			capital = first && firstLower ? capital.toLowerCase() : capital.toUpperCase();
			if (length > 1) {
				String follow = name.substring(1);
				if (lowerCase) {
					follow = follow.toLowerCase();
				}
				name = capital + follow;
			} else {
				name = capital;
			}

			camelName = first ? name : camelName + name;
		}

		return camelName;
	}

	public static Predicate<Integer> nonNegativeInt() {
		return number -> number >= 0;
	}

	public static Predicate<Integer> positiveInt() {
		return number -> number > 0;
	}

	public static Object[] toObjectArray(Object obj) {
		Object[] array = new Object[Array.getLength(obj)];
		for (int i = 0; i < array.length; i++) {
			array[i] = Array.get(obj, i);
		}

		return array;
	}
}
