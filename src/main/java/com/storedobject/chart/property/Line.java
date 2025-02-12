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

/**
 * Represents a line.
 *
 * @author Syam
 */
public class Line extends VisibleProperty {

	private LineStyle style;

	/**
	 * Constructor.
	 */
	public Line() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("lineStyle", style);
	}

	/**
	 * Get the style.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Style.
	 */
	public LineStyle getStyle(boolean create) {
		if (style == null && create) {
			style = new LineStyle();
		}

		return style;
	}

	/**
	 * Set the style.
	 *
	 * @param style Style to set.
	 */
	public void setStyle(LineStyle style) {
		this.style = style;
	}
}
