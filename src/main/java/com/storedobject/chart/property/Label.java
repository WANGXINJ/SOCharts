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

import com.storedobject.chart.component.PieChart;

/**
 * Represents label property. Certain charts supports this property (Example:
 * {@link PieChart}.
 *
 * @author xj
 */
public class Label extends BaseComponentProperty {

	private Integer fontSize;
	private Boolean show;
	private Position position;
	private AlignTo alignTo;

	public Label() {
		super("label");
	}

	public Boolean isShow() {
		return show;
	}

	public Label setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public Label setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	public Position getPosition() {
		return position;
	}

	public Label setPosition(Position position) {
		this.position = position;
		return this;
	}

	public AlignTo getAlignTo() {
		return alignTo;
	}

	public Label setAlignTo(AlignTo alignTo) {
		this.alignTo = alignTo;
		return this;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("show", show);
		property("fontSize", fontSize);
		property("position", position);
		property("alignTo", alignTo);
	}

	public static enum Position {
		top, //
		bottom, //
		left, //
		right, //
		inner, //
		outer //
		;
	}

	public static enum AlignTo {
		none, //
		labelLine, //
		edge //
		;
	}
}
