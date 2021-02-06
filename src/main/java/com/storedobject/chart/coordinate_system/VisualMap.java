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

import com.storedobject.chart.component.VisiblePart;
import java.util.Objects;

/**
 * Abstract representation of a visualMap.
 *
 * @author xj
 */
public abstract class VisualMap extends VisiblePart {

	final private VisualMapType type;
	private Number min, max;
	private Number[] range;
	private Object dimension;

	/**
	 * Constructor.
	 *
	 * @param VisualMap type.
	 */
	protected VisualMap(VisualMapType type) {
		this.type = type;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("type", type);
		property("min", min);
		property("max", max);
		property("range", range);
		property("dimension", dimension);
	}

	@Override
	public void validate() {
	}

	public VisualMapType getType() {
		return this.type;
	}

	public boolean isType(VisualMapType type) {
		return Objects.equals(this.type, type);
	}

	public VisualMap setMin(Number min) {
		this.min = min;
		return this;
	}

	public VisualMap setMax(Number max) {
		this.max = max;
		return this;
	}

	public VisualMap setRange(Number from, Number to) {
		if (range == null) {
			range = new Number[2];
		}
		range[0] = from;
		range[1] = to;

		return this;
	}

	public VisualMap noRange() {
		this.range = null;
		return this;
	}

	public VisualMap setDimension(Integer dimension) {
		this.dimension = dimension;
		return this;
	}

	public VisualMap setDimension(String dimension) {
		this.dimension = dimension;
		return this;
	}

	@Override
	protected boolean encodeJsonWhenHide() {
		return true;
	}

	public static enum VisualMapType {
		continuous, //
		piecewise; //
	}
}