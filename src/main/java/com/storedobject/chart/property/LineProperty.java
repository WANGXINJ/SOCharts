/*
 *  Copyright 2019-2020 Syam Pillai
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.storedobject.chart.property;

import static com.storedobject.chart.component.ComponentPart.addComma;
import static com.storedobject.chart.util.ComponentPropertyUtil.beginNode;
import static com.storedobject.chart.util.ComponentPropertyUtil.endNode;

/**
 * Represents line property. Certain charts supports this property (Example:
 * {@link LineChart}.
 *
 * @author xj
 */
public class LineProperty extends Line {

	private String name;

	private LineProperty(String name) {
		this.name = name;
	}

	public LineStyle getStyle() {
		LineStyle style = super.getStyle(false);
		if (style == null) {
			style = new LineStyle();
			style.setOpacity(100);
			setStyle(style);
		}
		return style;
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		addComma(sb);
		beginNode(name, sb);
		super.encodeJSON(sb);
		endNode(sb);
	}

	public static LineProperty splitLine() {
		return new LineProperty("splitLine");
	}

	public static LineProperty minorSplitLine() {
		return new LineProperty("minorSplitLine");
	}

	public static LineProperty custom(String name) {
		return new LineProperty(name);
	}
}
