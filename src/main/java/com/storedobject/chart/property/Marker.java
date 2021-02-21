package com.storedobject.chart.property;

public abstract class Marker<MARKER extends Marker<MARKER>> extends BaseComponentProperty {

	@SuppressWarnings("unchecked")
	final private MARKER _this = (MARKER) this;

	private Symbol symbol;
	private Integer[] symbolSize;
	private Boolean silent;
	private LabelProperty label;
	private PropertyValueArray data;

	protected Marker(String name) {
		super(name);
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("symbol", symbol);
		property("symbolSize", symbolSize);
		property("silent", silent);
		property("label", label);
		property("data", data);
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public MARKER setSymbol(Symbol symbol) {
		this.symbol = symbol;
		return _this;
	}

	public Integer[] getSymbolSize() {
		return symbolSize;
	}

	public MARKER setSymbolSize(Integer size) {
		if (size == null) {
			symbolSize = null;
		} else {
			setSymbolSize(size, size);
		}
		return _this;
	}

	public MARKER setSymbolSize(int width, int height) {
		if (symbolSize == null) {
			symbolSize = new Integer[2];
		}

		symbolSize[0] = width;
		symbolSize[1] = height;

		return _this;
	}

	public Boolean getSilent() {
		return silent;
	}

	public MARKER setSilent(Boolean silent) {
		this.silent = silent;
		return _this;
	}

	public LabelProperty getLabel(boolean create) {
		if (label == null && create) {
			label = new LabelProperty();
		}
		return label;
	}

	public MARKER setLabel(LabelProperty label) {
		this.label = label;
		return _this;
	}

	public PropertyValueArray getData(boolean create) {
		if (data == null && create) {
			data = new PropertyValueArray();
		}
		return data;
	}

	public MARKER addData(String name, TypeData.Type type) {
		TypeData data = Data.typeData(name, type);
		getData(true).addPropertyValue(data);
		return _this;
	}

	public MARKER addData(String name, Size x, Size y) {
		XYData data = Data.xyData(name, x, y);
		getData(true).addPropertyValue(data);
		return _this;
	}

	public static class Data extends PropertyComponentValue {
		private String name;

		protected Data(String name) {
			this.name = name;
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("name", name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static TypeData typeData(String name, TypeData.Type type) {
			return type.newData(name);
		}

		public static XYData xyData(String name, Size x, Size y) {
			return new XYData(name, x, y);
		}
	}

	protected abstract static class TypeData extends Data {
		private Type type;

		protected TypeData(String name, Type type) {
			super(name);

			this.type = type;
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("type", type);
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		@Override
		public int hashCode() {
			return type.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof TypeData)) {
				return false;
			}
			TypeData other = (TypeData) o;
			return this.type == other.type;
		}

		public interface Type {
			TypeData newData(String name);
		}
	}

	public static class XYData extends Data {
		private Size x;
		private Size y;

		public XYData(String name, Size x, Size y) {
			super(name);

			this.x = x;
			this.y = y;
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("x", x);
			property("y", y);
		}

		public Size getX() {
			return x;
		}

		public void setX(Size x) {
			this.x = x;
		}

		public Size getY() {
			return y;
		}

		public void setY(Size y) {
			this.y = y;
		}

		@Override
		public int hashCode() {
			return x.hashCode() + y.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof XYData)) {
				return false;
			}
			XYData other = (XYData) o;
			return this.x == other.x && this.y == other.y;
		}
	}

	public static enum Symbol {
		circle, //
		rect, //
		roundRect, //
		triangle, //
		diamond, //
		pin, //
		arrow, //
		none //
	}
}
