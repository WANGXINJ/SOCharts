package com.storedobject.chart.property;

public class MarkPoint extends Marker<MarkPoint> {

	private Integer symbolRotate;
	private Size[] symbolOffset;
	private ItemStyle itemStyle;

	public MarkPoint() {
		super("markPoint");
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("symbolRotate", symbolRotate);
		property("symbolOffset", symbolOffset);
		property(itemStyle);
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

	public static class TypeData extends Marker.TypeData {

		public TypeData(String name, Type type) {
			super(name, type);
		}

		public static enum Type implements Marker.TypeData.Type {
			max, //
			min; //

			@Override
			public TypeData newData(String name) {
				return new TypeData(name, this);
			}
		}
	}
}
