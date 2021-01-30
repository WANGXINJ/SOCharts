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

package com.storedobject.chart.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.storedobject.chart.coordinate_system.HasPosition;
import com.storedobject.chart.coordinate_system.Position;
import com.storedobject.chart.coordinate_system.RectangularCoordinate;
import com.storedobject.chart.property.PropertyComponentValue;

/**
 * Toolbox provides certain utilities (Example: "Download the chart display as
 * an image"). Each utility is accessed via a {@link ToolboxButton} part that
 * can be added to the {@link Toolbox} using the method
 * {@link #addButton(ToolboxButton...)}. Some standard buttons are already
 * available as static classes in this class itself.
 *
 * @author Syam
 */
public class Toolbox extends VisiblePart implements Component, HasPosition, SinglePart {

	private final List<ToolboxButton> buttons = new ArrayList<>();
	private Position position;
	private boolean vertical = false;

	/**
	 * Constructor.
	 */
	public Toolbox() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("tooltip", new PropertyComponentValue().setProperty("show", true));
		property("orient", "vertical", vertical);
		if (show) {
			PropertyComponentValue buttonValue = new PropertyComponentValue();
			buttons.stream().filter(button -> button instanceof Internal).forEach(button -> {
				buttonValue.setProperty(((Internal) button).getTag(), button);
			});
			property("feature", buttonValue);
		}
	}

	@Override
	public void validate() {
	}

	@Override
	public final Position getPosition(boolean create) {
		if (position == null && create) {
			position = new Position();
		}
		return position;
	}

	@Override
	public final void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Change the orientation of the toolbox display to vertical.
	 */
	public void showVertically() {
		vertical = true;
	}

	/**
	 * Add too buttons.
	 *
	 * @param buttons Buttons to add.
	 */
	public void addButton(ToolboxButton... buttons) {
		if (buttons != null) {
			this.buttons.addAll(Arrays.asList(buttons));
		}
	}

	/**
	 * Remove tool buttons.
	 *
	 * @param buttons Buttons to remove.
	 */
	public void removeButton(ToolboxButton... buttons) {
		if (buttons != null) {
			this.buttons.removeAll(Arrays.asList(buttons));
		}
	}

	private interface Internal {
		String getTag();
	}

	/**
	 * Download tool button. Clicking this will download the displayed charts as an
	 * image.
	 *
	 * @author Syam
	 */
	public final static class Download extends ToolboxButton implements Internal {

		private int resolution = 1;

		/**
		 * Constructor.
		 */
		public Download() {
			setCaption("Download");
		}

		/**
		 * Get the tag for this tool button. (For internal use only).
		 *
		 * @return Tag.
		 */
		@Override
		public String getTag() {
			return "saveAsImage";
		}

		/**
		 * Get image resolution.
		 *
		 * @return Resolution
		 */
		public int getResolution() {
			return resolution;
		}

		/**
		 * Set image resolution. Resolution is a number greater than zero. Higher the
		 * number, higher the resolution.
		 *
		 * @param resolution Image resolution.
		 */
		public void setResolution(int resolution) {
			this.resolution = resolution;
		}

		@Override
		public void encodeJSON(StringBuilder sb) {
			super.encodeJSON(sb);
			if (resolution > 1) {
				sb.append(',');
				ComponentPart.encode(sb, "pixelRatio", resolution);
			}
		}
	}

	/**
	 * Restore tool button. Clicking this will restore the displayed charts to the
	 * state prior to user interaction.
	 *
	 * @author Syam
	 */
	public final static class Restore extends ToolboxButton implements Internal {

		/**
		 * Constructor.
		 */
		public Restore() {
			setCaption("Restore");
		}

		/**
		 * Get the tag for this tool button. (For internal use only).
		 *
		 * @return Tag.
		 */
		@Override
		public String getTag() {
			return "restore";
		}
	}

	/**
	 * Zoom tool button. This allows data zooming for {@link RectangularCoordinate}
	 * systems.
	 *
	 * @author Syam
	 */
	public final static class Zoom extends ToolboxButton implements Internal {

		private String resetCaption = "Reset zoom";

		/**
		 * Constructor.
		 */
		public Zoom() {
			setCaption("Zoom in");
		}

		/**
		 * Get the tag for this tool button. (For internal use only).
		 *
		 * @return Tag.
		 */
		@Override
		public String getTag() {
			return "dataZoom";
		}

		@Override
		protected void buildCaptionProperties() {
			String caption = getCaption(), resetCaption = getResetCaption();
			if (caption == null && resetCaption == null) {
				return;
			}

			property("title", new PropertyComponentValue() //
					.setProperty("zoom", caption) //
					.setProperty("back", resetCaption));
		}

		/**
		 * Get the caption for the "reset zoom" part (will be shown as a tooltip).
		 *
		 * @return Caption.
		 */
		public String getResetCaption() {
			return resetCaption;
		}

		/**
		 * Set the caption for the "reset zoom" part (will be shown as a tooltip).
		 *
		 * @param resetCaption Caption.
		 */
		public void setResetCaption(String resetCaption) {
			this.resetCaption = resetCaption;
		}
	}
}
