package com.storedobject.chart.property;

import com.storedobject.chart.component.PieChart;

/**
 * Represents itemStyle property. Certain charts supports this property
 * (Example: {@link PieChart}.
 *
 * @author xj
 */
public class ItemStyle extends BaseComponentProperty {

	private Color color;
	private Color borderColor;
	private Integer borderWidth;
	private LineStyle.Type borderType;

	public ItemStyle() {
		super("itemStyle");
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("color", color);
		property("borderColor", borderColor);
		property("borderWidth", borderWidth);
		property("borderType", borderType);
	}

	public Color getColor() {
		return color;
	}

	public ItemStyle setColor(Color color) {
		this.color = color;
		return this;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public ItemStyle setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	public Integer getBorderWidth() {
		return borderWidth;
	}

	public ItemStyle setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
		return this;
	}

	public LineStyle.Type getBorderType() {
		return borderType;
	}

	public ItemStyle setBorderType(LineStyle.Type borderType) {
		this.borderType = borderType;

		return this;
	}
}
