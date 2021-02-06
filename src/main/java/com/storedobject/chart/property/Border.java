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

import java.util.Arrays;

/**
 * Represents a border. Several other properties or parts have border as an
 * additional property.
 *
 * @author Syam
 */
public class Border extends TextBorder {

	private Color background;
	private final int[] radius = new int[4];

	/**
	 * Constructor.
	 */
	public Border() {
		Arrays.fill(radius, 0);
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		if (any()) {
			int[] radii;
			if (radius[0] == radius[1] && radius[1] == radius[2] && radius[2] == radius[3]) {
				radii = new int[] { radius[0] };
			} else {
				radii = radius;
			}
			property(p("borderRadius"), radii);
		}

		property("backgroundColor", background);
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
	public Border setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * Set the radius of the border corners (all corners) (in degrees).
	 *
	 * @param radius Radius of the corner.
	 */
	public Border setRadius(int radius) {
		Arrays.fill(this.radius, Math.min(90, Math.max(0, radius)));
		return this;
	}

	/**
	 * Set the radius at the given corner. (Corner index: Top-left corner is 0,
	 * Top-right corner is 1, Bottom-right corner is 2, Bottom-left corner is 3).
	 *
	 * @param index  Corner index.
	 * @param radius Radius to set in degrees.
	 */
	public Border setRadius(int index, int radius) {
		if (index >= 0) {
			this.radius[index % this.radius.length] = Math.min(90, Math.max(0, radius));
		}

		return this;
	}

	private boolean any() {
		for (int r : radius) {
			if (r > 0) {
				return true;
			}
		}
		return false;
	}
}
