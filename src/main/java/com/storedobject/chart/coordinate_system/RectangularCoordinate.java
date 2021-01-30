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

package com.storedobject.chart.coordinate_system;

import com.storedobject.chart.property.Border;
import com.storedobject.chart.util.ChartException;

/**
 * Representation of rectangular (cartesian) coordinate system with X and Y
 * axes. There could be one or more X and Y axes.
 *
 * @author Syam
 */
public class RectangularCoordinate extends CoordinateSystem {

	private Border border;
	private Boolean sizeIncludeLabels;

	/**
	 * Constructor.
	 *
	 * @param axes Axes of the coordinate
	 */
	public RectangularCoordinate(XYAxis... axes) {
		addAxis(axes);
	}

	@Override
	public void validate() throws ChartException {
		if (noAxis(XAxis.class)) {
			throw new ChartException("X Axis not set");
		}
		if (noAxis(YAxis.class)) {
			throw new ChartException("Y Axis not set");
		}
		super.validate();
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
	public void setBorder(Border border) {
		this.border = border;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property(border);
		property("containLabel", sizeIncludeLabels);
	}

	/**
	 * Set the size in such a way that the size of the coordinate system includes
	 * labels too.
	 */
	public void sizeIncludesLabels() {
		sizeIncludeLabels = true;
	}

	@Override
	public String systemName() {
		return "cartesian2d";
	}
}
