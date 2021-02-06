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

import static com.storedobject.chart.util.ComponentPropertyUtil.positiveInt;

/**
 * As the name indicates, "text style" is for styling texts.
 *
 * @author Syam
 */
public class TextStyle extends PropertyComponentValue {

	private Color color;
	private FontStyle fontStyle;
	private FontWeight fontWeight;
	private String fontFamily;
	private Integer fontSize;
	private Integer lineHeight;
	private Integer width, height;
	private TextBorder textBorder;
	private Overflow overFlow;
	private String ellipsis;
	private Overflow lineOverflow;
	private Color background;
	private Padding padding;
	private Border border;
	private Alignment alignment;

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("color", color);
		property("fontStyle", fontStyle);
		property("fontWeight", fontWeight);
		property("fontFamily", fontFamily);
		property("fontSize", fontSize, positiveInt());
		property("lineHeight", lineHeight);
		property("width", width);
		property("height", height);
		property(textBorder != null ? textBorder.setPrefix("text") : null);
		property("overflow", overFlow);
		property("ellipsis", ellipsis);
		property("lineOverflow", lineOverflow);
		property("backgroundColor", background);
		property(border);
		property(padding);
		property(alignment);
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
	public TextStyle setColor(Color color) {
		this.color = color;
		return this;
	}

	/**
	 * Get font-style.
	 *
	 * @return Font-style.
	 */
	public final FontStyle getFontStyle() {
		return fontStyle;
	}

	/**
	 * Set font-style.
	 *
	 * @param fontStyle Font-style.
	 */
	public TextStyle setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
		return this;
	}

	/**
	 * Get font-size.
	 *
	 * @return Font-size.
	 */
	public final Integer getFontSize() {
		return fontSize;
	}

	/**
	 * Set font-size.
	 *
	 * @param fontSize Font-size.
	 */
	public TextStyle setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	/**
	 * Get font-weight.
	 *
	 * @return Font-weight.
	 */
	public final FontWeight getFontWeight() {
		return fontWeight;
	}

	/**
	 * Set font-weight.
	 *
	 * @param fontWeight Font-weight.
	 */
	public TextStyle setFontWeight(FontWeight fontWeight) {
		this.fontWeight = fontWeight;
		return this;
	}

	/**
	 * Get font-family.
	 *
	 * @return Font-family.
	 */
	public final String getFontFamily() {
		return fontFamily;
	}

	/**
	 * Set font-family. (Example: "sans-serif", "serif", "monospace" etc.).
	 *
	 * @param fontFamily Font-family.
	 */
	public TextStyle setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
		return this;
	}

	public Integer getLineHeight() {
		return lineHeight;
	}

	public TextStyle setLineHeight(Integer lineHeight) {
		this.lineHeight = lineHeight;
		return this;
	}

	public Integer getWidth() {
		return width;
	}

	public TextStyle setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public Integer getHeight() {
		return height;
	}

	public TextStyle setHeight(Integer height) {
		this.height = height;
		return this;
	}

	/**
	 * Get the text border.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Text border.
	 */
	public final TextBorder getTextBorder(boolean create) {
		if (textBorder == null && create) {
			textBorder = new TextBorder();
		}
		return textBorder;
	}

	/**
	 * Set the text border.
	 *
	 * @param textBorder Text border.
	 */
	public final TextStyle setTextBorder(TextBorder textBorder) {
		this.textBorder = textBorder;
		return this;
	}

	public Overflow getOverflow() {
		return overFlow;
	}

	public TextStyle setOverflow(Overflow overFlow) {
		this.overFlow = overFlow;
		return this;
	}

	public String getEllipsis() {
		return ellipsis;
	}

	public TextStyle setEllipsis(String ellipsis) {
		this.ellipsis = ellipsis;
		return this;
	}

	public Overflow getLineOverflow() {
		return lineOverflow;
	}

	public TextStyle setLineOverflow(Overflow lineOverflow) {
		this.lineOverflow = lineOverflow;
		return this;
	}

	/**
	 * Get background color.
	 *
	 * @return Background color.
	 */
	public final Color getBackground() {
		return background;
	}

	/**
	 * Set background color.
	 *
	 * @param background Background color.
	 */
	public TextStyle setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * Get the border.
	 * 
	 * @param create Whether to create if not exists or not.
	 *
	 * @return Border.
	 */
	public final Border getBorder(boolean create) {
		if (border == null && create) {
			border = new Border();
		}
		return border;
	}

	/**
	 * Set the border.
	 *
	 * @param border Border.
	 */
	public TextStyle setBorder(Border border) {
		this.border = border;
		return this;
	}

	/**
	 * Get the padding.
	 * 
	 * @param create Whether to create if not exists or not.
	 *
	 * @return Padding.
	 */
	public final Padding getPadding(boolean create) {
		if (padding == null && create) {
			padding = new Padding();
		}
		return padding;
	}

	/**
	 * Set the padding.
	 *
	 * @param padding Padding.
	 */
	public TextStyle setPadding(Padding padding) {
		this.padding = padding;
		return this;
	}

	/**
	 * Get the alignment.
	 *
	 * @param create Whether to create if not exists or not.
	 * @return Alignment.
	 */
	public Alignment getAlignment(boolean create) {
		return alignment;
	}

	/**
	 * Set the alignment.
	 *
	 * @param alignment Alignment.
	 */
	public TextStyle setAlignment(Alignment alignment) {
		this.alignment = alignment;
		return this;
	}

	public void save(OuterProperties op) {
		op.background = background;
		setBackground(null);
		op.padding = padding;
		setPadding(null);
		op.border = border;
		setBorder(null);
		op.alignment = alignment;
		setAlignment(null);
	}

	public void restore(OuterProperties op) {
		setBackground(op.background);
		setPadding(op.padding);
		setBorder(op.border);
		setAlignment(op.alignment);
	}

	public static enum Overflow {
		none("none"), //
		truncate("truncate"), //
		breakLine("break"), //
		breakAll("breakAll"), //
		;

		private String name;

		private Overflow(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static class OuterProperties {
		private Color background;
		private Padding padding;
		private Border border;
		private Alignment alignment;

		public Color getBackground() {
			return background;
		}

		public OuterProperties setBackground(Color background) {
			this.background = background;
			return this;
		}

		public Padding getPadding() {
			return padding;
		}

		public OuterProperties setPadding(Padding padding) {
			this.padding = padding;
			return this;
		}

		public Border getBorder() {
			return border;
		}

		public OuterProperties setBorder(Border border) {
			this.border = border;
			return this;
		}

		public Alignment getAlignment() {
			return alignment;
		}

		public OuterProperties setAlignment(Alignment alignment) {
			this.alignment = alignment;
			return this;
		}
	}
}