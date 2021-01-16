package com.storedobject.chart.property;

import com.storedobject.chart.Color;
import com.storedobject.chart.LineStyle;
import com.storedobject.chart.PieChart;

/**
 * Represents itemStyle property. Certain charts supports this property
 * (Example: {@link PieChart}.
 *
 * @author xj
 */
public class ItemStyleProperty extends BaseComponentProperty {

	private Color color;
	private Color borderColor;
	private Integer borderWidth;
	private LineStyle.Type borderType;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ItemStyleProperty() {
		super("itemStyle");
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public ItemStyleProperty setBorderColor(Color borderColor) {
		this.borderColor = borderColor;

		return this;
	}

	public Integer getBorderWidth() {
		return borderWidth;
	}

	public ItemStyleProperty setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;

		return this;
	}

	public LineStyle.Type getBorderType() {
		return borderType;
	}

	public ItemStyleProperty setBorderType(LineStyle.Type borderType) {
		this.borderType = borderType;

		return this;
	}

	@Override
	protected void initProperties() {
		super.initProperties();

		property("color", color);
		property("borderColor", borderColor);
		property("borderWidth", borderWidth);
		property("borderType", borderType);
	}
}
