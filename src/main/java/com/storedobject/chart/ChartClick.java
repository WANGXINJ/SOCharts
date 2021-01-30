package com.storedobject.chart;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

import com.vaadin.event.SerializableEventListener;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class ChartClick {

	private ChartClick() {
	}

	public interface Listener extends SerializableEventListener {
		public static final Method CHART_CLICK_METHOD = ReflectTools.findMethod(Listener.class, "onClick", Event.class);

		void onClick(Event event);
	}

	public static class Event extends Component.Event {
		private static final long serialVersionUID = -1374743977934914349L;

		private final EventDetails details;

		public Event(SOChart chart, JsonValue param) {
			super(chart);

			details = toDetails(param);
		}

		public SOChart getChart() {
			return (SOChart) getSource();
		}

		public EventDetails getDetails() {
			return details;
		}
	}

	public static class EventDetails {
		// component name of clicked component
		// e.g., 'series', 'markLine', 'markPoint', 'timeLine'
		final private String componentType;
		// series type (useful when componentType is 'series')
		// e.g., 'line', 'bar', 'pie'
		final private String seriesType;
		// series index in option.series (useful when componentType is 'series')
		final private Integer seriesIndex;
		// series name (useful when componentType is 'series')
		final private String seriesName;
		// data name, or category name
		final private String name;
		// data index in input data array
		final private Integer dataIndex;
		// raw input data item
		final private JsonValue data;
		// Some series, such as sankey or graph, maintains both nodeData and edgeData,
		// in which case, dataType is set to be 'node' or 'edge' to identify.
		// On the other hand, most other series have only one type of data,
		// where dataType is not needed.
		final private String dataType;
		// input data value
		final private JsonValue value;
		// color of component (useful when componentType is 'series')
		final private String color;

		private EventDetails(String componentType, String seriesType, Integer seriesIndex, String seriesName,
				String name, Integer dataIndex, JsonValue data, String dataType, JsonValue value, String color) {
			this.componentType = componentType;
			this.seriesType = seriesType;
			this.seriesIndex = seriesIndex;
			this.seriesName = seriesName;
			this.name = name;
			this.dataIndex = dataIndex;
			this.data = data;
			this.dataType = dataType;
			this.value = value;
			this.color = color;
		}

		public String getComponentType() {
			return componentType;
		}

		public String getSeriesType() {
			return seriesType;
		}

		public Integer getSeriesIndex() {
			return seriesIndex;
		}

		public String getSeriesName() {
			return seriesName;
		}

		public String getName() {
			return name;
		}

		public Integer getDataIndex() {
			return dataIndex;
		}

		public JsonValue getData() {
			return data;
		}

		public String getDataType() {
			return dataType;
		}

		public JsonValue getValue() {
			return value;
		}

		public String getColor() {
			return color;
		}
	}

	private static EventDetails toDetails(JsonValue value) {
		JsonObject obj = (JsonObject) value;
		return new EventDetails(jsonGet(obj, "componentType", JsonObject::getString), //
				jsonGet(obj, "seriesType", JsonObject::getString), //
				toInt(jsonGet(obj, "seriesIndex", JsonObject::getNumber)), //
				jsonGet(obj, "seriesName", JsonObject::getString), //
				jsonGet(obj, "name", JsonObject::getString), //
				toInt(jsonGet(obj, "dataIndex", JsonObject::getNumber)), //
				obj.get("data"), //
				jsonGet(obj, "dataType", JsonObject::getString), //
				obj.get("value"), //
				jsonGet(obj, "color", JsonObject::getString));
	}

	private static <T> T jsonGet(JsonObject obj, String key, BiFunction<JsonObject, String, T> getter) {
		if (obj.get(key) == null) {
			return null;
		}

		return getter.apply(obj, key);
	}

	private static Integer toInt(Double d) {
		return d != null ? d.intValue() : null;
	}
}
