package com.storedobject.chart.property;

public class MarkLine extends Marker<MarkLine> {

	private Integer precision;
	private LineStyle lineStyle;

	public MarkLine() {
		super("markLine");
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("precision", precision);
		property("lineStyle", lineStyle);
	}

	public Integer getPrecision() {
		return precision;
	}

	public MarkLine setPrecision(Integer precision) {
		this.precision = precision;
		return this;
	}

	public LineStyle getLineStyle(boolean create) {
		if (lineStyle == null && create) {
			lineStyle = new LineStyle();
		}
		return lineStyle;
	}

	public MarkLine setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
		return this;
	}

	public static class TypeData extends Marker.TypeData {

		public TypeData(String name, Type type) {
			super(name, type);
		}

		public static enum Type implements Marker.TypeData.Type {
			average;

			@Override
			public TypeData newData(String name) {
				return new TypeData(name, this);
			}
		}
	}
}
