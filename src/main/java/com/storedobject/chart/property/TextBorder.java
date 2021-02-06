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
import static com.storedobject.chart.util.ComponentPropertyUtil.nonNegativeInt;

/**
 * Represents text-border. Text can be drawn with borders and the width and
 * color of the border can be set.
 *
 * @author Syam
 */
public class TextBorder extends PropertyComponentValue {

	private String prefix;
	private Color color;
	private Integer width;
	private Shadow shadow;

	/**
	 * Constructor.
	 */
	public TextBorder() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property(p("borderColor"), color);
		property(p("borderWidth"), width, nonNegativeInt());
		property(shadow());
	}

	/**
	 * Get the color of the border.
	 *
	 * @return Color of the border.
	 */
	public final Color getColor() {
		return color;
	}

	/**
	 * Set the color of the border.
	 *
	 * @param color Color to set.
	 */
	public TextBorder setColor(Color color) {
		this.color = color;
		return this;
	}

	/**
	 * Get the width of the border (in pixels).
	 *
	 * @return Width of the border.
	 */
	public final Integer getWidth() {
		return width;
	}

	/**
	 * <p>
	 * Set the width of the border (in pixels).
	 * </p>
	 * <p>
	 * Note: Corner radius is not applicable for text borders.
	 * </p>
	 *
	 * @param width Width of the border.
	 */
	public TextBorder setWidth(Integer width) {
		this.width = width;
		return this;
	}

	/**
	 * Get the shadow.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Shadow.
	 */
	public final Shadow getShadow(boolean create) {
		if (shadow == null && create) {
			shadow = new Shadow();
		}
		return shadow;
	}

	/**
	 * Set the shadow.
	 *
	 * @param shadow Shadow.
	 */
	public TextBorder setShadow(Shadow shadow) {
		this.shadow = shadow;
		return this;
	}

	public TextBorder setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	private Shadow shadow() {
		return shadow != null ? shadow.setPrefix(prefix) : null;
	}

	protected String p(String any) {
		return camelName(true, false, prefix, any);
	}
}