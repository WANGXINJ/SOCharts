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
 * Line-style. Determines how a line will be drawn.
 *
 * @author Syam
 */
public class LineStyle extends PropertyComponentValue {

	private Color color;
	private int width = Integer.MIN_VALUE;
	private Type type;
	private int opacity = -1;
	private Shadow shadow;

	/**
	 * Constructor.
	 */
	public LineStyle() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("color", color);
		property("width", width, width > 0);
		property("type", type);
		property("opacity", Math.min(100, opacity) / 100.0);
		property(shadow);
	}

	/**
	 * Get color.
	 *
	 * @return Color.
	 */
	public final Color getColor() {
		return color;
	}

	/**
	 * Set color.
	 *
	 * @param color Color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Get the width of the line.
	 *
	 * @return Line width in pixels.
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Set the width of the line.
	 *
	 * @param width Width of the line in pixels.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Get the line-type.
	 *
	 * @return Line type.
	 */
	public final Type getType() {
		return type;
	}

	/**
	 * Set the line-type.
	 *
	 * @param type Line type.
	 */
	public void setType(Type type) {
		this.type = type;
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
	public void setShadow(Shadow shadow) {
		this.shadow = shadow;
	}

	/**
	 * Get the opacity of the line (Value as percentage 0 to 100%).
	 *
	 * @return Opacity.
	 */
	public int getOpacity() {
		return opacity;
	}

	/**
	 * Set the opacity of the line (Value as percentage, 0 to 100%).
	 *
	 * @param opacity Opacity.
	 */
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}

	/**
	 * Line-type.
	 *
	 * @author Syam
	 */
	public enum Type {
		/**
		 * Solid.
		 */
		SOLID,
		/**
		 * Dashed.
		 */
		DASHED,
		/**
		 * Dotted.
		 */
		DOTTED;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
}