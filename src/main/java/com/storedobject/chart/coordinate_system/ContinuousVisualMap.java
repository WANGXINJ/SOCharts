package com.storedobject.chart.coordinate_system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.storedobject.chart.property.BaseComponentProperty;
import com.storedobject.chart.property.Color;

public class ContinuousVisualMap extends VisualMap {

	private InRange inRange;

	public ContinuousVisualMap() {
		super(VisualMapType.continuous);
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property(inRange);
	}

	public InRange getInRange() {
		return inRange;
	}

	public ContinuousVisualMap setInRange(InRange inRange) {
		this.inRange = inRange;
		return this;
	}

	public static class InRange extends BaseComponentProperty {
		private List<Color> colors;

		public InRange() {
			super("inRange");
		}

		public List<Color> getColors() {
			return colors;
		}

		public InRange setColors(String... colors) {
			return setColors(toColors(colors));
		}

		public InRange setColors(Color... colors) {
			return setColors(Arrays.asList(colors));
		}

		public InRange setColors(List<Color> colors) {
			this.colors = colors;
			return this;
		}

		@Override
		protected void buildProperties() {
			super.buildProperties();

			property("color", colors);
		}

		private static List<Color> toColors(String... strings) {
			List<Color> list = new ArrayList<>();
			for (String str : strings) {
				list.add(new Color(str));
			}
			return list;
		}
	}
}
