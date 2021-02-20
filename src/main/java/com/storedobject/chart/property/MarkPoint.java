package com.storedobject.chart.property;

public class MarkPoint extends BaseComponentProperty {

	private Symbol symbol;
	private Integer[] symbolSize;
	private Integer symbolRotate;
	private Size[] symbolOffset;
	private Boolean silent;
	private LabelProperty label;
	private ItemStyle itemStyle;
	private PropertyValueArray data;

	public MarkPoint() {
		super("markPoint");
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("symbol", symbol);
		property("symbolSize", symbolSize);
		property("symbolRotate", symbolRotate);
		property("symbolOffset", symbolOffset);
		property("silent", silent);
		property("label", label);
		property(itemStyle);
		property("data", data);
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public MarkPoint setSymbol(Symbol symbol) {
		this.symbol = symbol;
		return this;
	}

	public Integer[] getSymbolSize() {
		return symbolSize;
	}

	public MarkPoint setSymbolSize(Integer size) {
		if (size == null) {
			symbolSize = null;
		} else {
			setSymbolSize(size, size);
		}
		return this;
	}

	public MarkPoint setSymbolSize(int width, int height) {
		if (symbolSize == null) {
			symbolSize = new Integer[2];
		}

		symbolSize[0] = width;
		symbolSize[1] = height;

		return this;
	}

	public Integer getSymbolRotate() {
		return symbolRotate;
	}

	public MarkPoint setSymbolRotate(Integer symbolRotate) {
		this.symbolRotate = symbolRotate;
		return this;
	}

	public Size[] getSymbolOffset() {
		return symbolOffset;
	}

	public MarkPoint setSymbolOffset(Size offset) {
		if (offset == null) {
			symbolOffset = null;
		} else {
			setSymbolOffset(offset, offset);
		}
		return this;
	}

	public MarkPoint setSymbolOffset(Size right, Size top) {
		if (symbolOffset == null) {
			symbolOffset = new Size[2];
		}

		symbolOffset[0] = right;
		symbolOffset[1] = top;

		return this;
	}

	public Boolean getSilent() {
		return silent;
	}

	public MarkPoint setSilent(Boolean silent) {
		this.silent = silent;
		return this;
	}

	public LabelProperty getLabel(boolean create) {
		if (label == null && create) {
			label = new LabelProperty();
		}
		return label;
	}

	public MarkPoint setLabel(LabelProperty label) {
		this.label = label;
		return this;
	}

	public ItemStyle getItemStyle(boolean create) {
		if (itemStyle == null && create) {
			itemStyle = new ItemStyle();
		}
		return itemStyle;
	}

	public MarkPoint setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
		return this;
	}

	public PropertyValueArray getData(boolean create) {
		if (data == null && create) {
			data = new PropertyValueArray();
		}
		return data;
	}

	public MarkPoint addData(String name, TypeData.Type type) {
		TypeData data = Data.typeData(name, type);
		getData(true).addPropertyValue(data);
		return this;
	}

	public MarkPoint addData(String name, Size x, Size y) {
		XYData data = Data.xyData(name, x, y);
		getData(true).addPropertyValue(data);
		return this;
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
			return new TypeData(name, type);
		}

		public static XYData xyData(String name, Size x, Size y) {
			return new XYData(name, x, y);
		}
	}

	public static class TypeData extends Data {
		private Type type;

		public TypeData(String name, Type type) {
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

		public static enum Type {
			max, //
			min //
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
