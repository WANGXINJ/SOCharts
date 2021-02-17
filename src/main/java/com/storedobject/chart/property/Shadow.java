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

/**
 * Represents a shadow. Typically, other properties have this property as an
 * additional property. For example, {@link TextStyle} has this property to
 * define its shadow.
 *
 * @author Syam
 */
public class Shadow extends PropertyComponentValue {

	private String prefix;
	private Color color;
	private Integer blur, offsetX, offsetY;
	private Integer opacity;

	/**
	 * Constructor.
	 */
	public Shadow() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property(p("shadowColor"), color);
		property(p("shadowBlur"), blur);
		property(p("shadowOffsetX"), offsetX);
		property(p("shadowOffsetY"), offsetY);
		property("opacity", opacity());
	}

	/**
	 * Get the blur for the shadow. (Example value: 10).
	 *
	 * @return Blur.
	 */
	public final Integer getBlur() {
		return blur;
	}

	/**
	 * Set the blur for the shadow. (Example value: 10).
	 *
	 * @param blur Blur.
	 */
	public Shadow setBlur(Integer blur) {
		this.blur = blur;
		return this;
	}

	/**
	 * Get the color.
	 *
	 * @return Color.
	 */
	public final Color getColor() {
		return color;
	}

	/**
	 * Set the color.
	 *
	 * @param color Color.
	 */
	public Shadow setColor(Color color) {
		this.color = color;
		return this;
	}

	/**
	 * Get offset X.
	 *
	 * @return X offset.
	 */
	public final Integer getOffsetX() {
		return offsetX;
	}

	/**
	 * Set offset X.
	 *
	 * @param offsetX X offset.
	 */
	public Shadow setOffsetX(Integer offsetX) {
		this.offsetX = offsetX;
		return this;
	}

	/**
	 * Get offset Y.
	 *
	 * @return Y offset.
	 */
	public final Integer getOffsetY() {
		return offsetY;
	}

	/**
	 * Set offset Y.
	 *
	 * @param offsetY Y offset.
	 */
	public Shadow setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
		return this;
	}

	/**
	 * Get the opacity of the shadow (Value as percentage 0 to 100%).
	 *
	 * @return Opacity.
	 */
	public Integer getOpacity() {
		return opacity;
	}

	/**
	 * Set the opacity of the shadow (Value as percentage, 0 to 100%).
	 *
	 * @param opacity Opacity.
	 */
	public Shadow setOpacity(Integer opacity) {
		this.opacity = opacity;
		return this;
	}

	public Shadow setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	private Double opacity() {
		return opacity != null && opacity >= 0 ? Math.min(100, opacity) / 100.0 : null;
	}

	private String p(String any) {
		return camelName(prefix, any);
	}
}
