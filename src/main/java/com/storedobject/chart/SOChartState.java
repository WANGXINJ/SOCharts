package com.storedobject.chart;

import com.vaadin.shared.ui.JavaScriptComponentState;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import elemental.json.impl.JreJsonObject;

public class SOChartState extends JavaScriptComponentState {
	private static final long serialVersionUID = -8959191675958421669L;

	public JsonObject properties;
	public String option;
	public boolean notMerge = true;

	public SOChartState() {
		properties = new JreJsonObject(new JreJsonFactory());
	}

	public void setProperty(String property, String value) {
		properties.put(property, value);
	}
}