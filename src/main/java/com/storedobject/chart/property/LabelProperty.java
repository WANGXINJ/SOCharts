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

import static com.storedobject.chart.util.ComponentPropertyUtil.camelName;
import static com.storedobject.chart.util.ComponentPropertyUtil.escape;

public class LabelProperty extends TextStyle implements HasFormatter<LabelProperty> {

	private Boolean show = true;
	private Position position;
	private String formatter;

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("show", show);
		property("position", position);
		property("formatter", formatter);
	}

	public LabelProperty setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public Position getPosition() {
		return position;
	}

	public LabelProperty setPosition(Position position) {
		this.position = position;
		return this;
	}

	@Override
	public String getFormatter() {
		return formatter;
	}

	@Override
	public LabelProperty setFormatter(String formatter, Format... formats) {
		this.formatter = toFormatter(formatter, formats);
		return this;
	}

	public LabelProperty innerLabel() {
		return setPosition(Position.inside);
	}

	public static class Position {
		public static final Position top = new Position("top"); //
		public static final Position bottom = new Position("bottom"); //
		public static final Position left = new Position("left"); //
		public static final Position right = new Position("right"); //
		public static final Position inside = new Position("inside"); //
		public static final Position insideLeft = new Position(inside, left); //
		public static final Position insideRight = new Position(inside, right); //
		public static final Position insideTop = new Position(inside, top); //
		public static final Position insideBottom = new Position(inside, bottom); //
		public static final Position insideTopLeft = new Position(inside, top, left); //
		public static final Position insideBottomLeft = new Position(inside, bottom, left); //
		public static final Position insideTopRight = new Position(inside, top, right); //
		public static final Position insideBottomRight = new Position(inside, bottom, right); //
		public static final Position outside = new Position("outside"); //
		public static final Position middle = new Position("middle"); //
		public static final Position center = new Position("center"); //

		final private String str;
		final private Size relativeLeft, relativeTop;

		public Position(Position... positions) {
			this(toStrings(positions));
		}

		public Position(String... strings) {
			this(camelName(true, false, strings));
		}

		public Position(String str) {
			this.str = str;
			this.relativeLeft = null;
			this.relativeTop = null;
		}

		public Position(Size relativeLeft, Size relativeTop) {
			this.relativeLeft = relativeLeft;
			this.relativeTop = relativeTop;
			this.str = null;
		}

		@Override
		public String toString() {
			if (str != null) {
				return str;
			}

			return escape(new Size[] { relativeLeft, relativeTop });
		}

		private static String[] toStrings(Position[] positions) {
			int length = positions.length;
			String[] strings = new String[length];
			for (int i = 0; i < length; i++) {
				strings[i] = positions[i].toString();
			}
			return strings;
		}
	}

	public static enum AlignTo {
		none, //
		labelLine, //
		edge //
		;
	}
}
