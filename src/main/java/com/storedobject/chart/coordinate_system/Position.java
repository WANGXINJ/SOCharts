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

package com.storedobject.chart.coordinate_system;

import com.storedobject.chart.SOChart;
import com.storedobject.chart.property.PropertyComponentValue;
import com.storedobject.chart.property.Size;

/**
 * Representation of a position within the chart display. Chart display always
 * occupies a rectangular part of the screen and the size of this rectangle is
 * determined by the methods used from Vaadin's
 * {@link com.vaadin.flow.component.HasSize} on the chart ({@link SOChart}
 * implements {@link com.vaadin.flow.component.HasSize} interface). Using the
 * same standard used by CSS for absolute positioning, a part that supports
 * positioning can use this class to define its positioning requirements within
 * the chart's boundary. Only just enough details need to be set for both
 * horizontal and vertical positions. For example: if "left" and "right" are
 * specified, "width" will be automatically computed. Each attribute can use
 * absolute pixels values or percentage values. For example, left = 30% means a
 * horizontal position of 30% of the chart's width from the left edge of the
 * chart.
 *
 * @author Syam
 */
public class Position extends PropertyComponentValue {

	private Size top;
	private Size right;
	private Size bottom;
	private Size left;
	private Size width;
	private Size height;

	/**
	 * Constructor.
	 */
	public Position() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("top", top);
		property("right", right);
		property("bottom", bottom);
		property("left", left);
		property("width", width);
		property("height", height);
	}

	/**
	 * Set the "left" size.
	 *
	 * @param size Size.
	 */
	public void setLeft(Size size) {
		left = size.clone();
	}

	/**
	 * Set the "right" size.
	 *
	 * @param size Size.
	 */
	public void setRight(Size size) {
		right = size.clone();
	}

	/**
	 * Set the "top" size.
	 *
	 * @param size Size.
	 */
	public void setTop(Size size) {
		top = size.clone();
	}

	/**
	 * Set the "bottom" size.
	 *
	 * @param size Size.
	 */
	public void setBottom(Size size) {
		bottom = size.clone();
	}

	/**
	 * Set the "width".
	 *
	 * @param size Size.
	 */
	public void setWidth(Size size) {
		width = size.clone();
	}

	/**
	 * Set the "height".
	 *
	 * @param size Size.
	 */
	public void setHeight(Size size) {
		height = size.clone();
	}

	/**
	 * Justify to the left side (horizontal).
	 */
	public void justifyLeft() {
		left.left();
	}

	/**
	 * Justify to the center (horizontal).
	 */
	public void justifyCenter() {
		left.center();
	}

	/**
	 * Justify to the right side (horizontal).
	 */
	public void justifyRight() {
		left.right();
	}

	/**
	 * Align to the top side (vertical).
	 */
	public void alignTop() {
		top.top();
	}

	/**
	 * Align to the center (vertical).
	 */
	public void alignCenter() {
		top.middle();
	}

	/**
	 * Align to the bottom side (vertical).
	 */
	public void alignBottom() {
		top.bottom();
	}

	/**
	 * Center it.
	 */
	public void center() {
		alignCenter();
		justifyCenter();
	}

	private static boolean stuff(StringBuilder sb, String attribute, Size value, boolean comma) {
		String size = value.encode();
		if (size == null) {
			return comma;
		}
		if (comma) {
			sb.append(',');
		}
		sb.append('"').append(attribute).append("\":").append(size);
		return true;
	}
}
