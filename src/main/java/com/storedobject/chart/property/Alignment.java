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

/**
 * Representation of Alignment property (both horizontal and vertical).
 *
 * @author Syam
 */
public class Alignment extends PropertyComponentValue implements ComponentProperty {

	private String prefix, justify, align;

	/**
	 * Constructor.
	 */
	public Alignment() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property(p("align"), justify);
		property(p("verticalAlign"), align);
	}

	/**
	 * Justify left.
	 */
	public Alignment justifyLeft() {
		this.justify = "left";
		return this;
	}

	/**
	 * Justify right.
	 */
	public Alignment justifyRight() {
		this.justify = "right";
		return this;
	}

	/**
	 * Justify center.
	 */
	public Alignment justifyCenter() {
		this.justify = "center";
		return this;
	}

	/**
	 * Align top.
	 */
	public Alignment alignTop() {
		align = "top";
		return this;
	}

	/**
	 * Align center/middle.
	 */
	public Alignment alignCenter() {
		align = "middle";
		return this;
	}

	/**
	 * Align bottom.
	 */
	public Alignment alignBottom() {
		align = "bottom";
		return this;
	}

	/**
	 * Align and justify at the center.
	 */
	public Alignment center() {
		justifyCenter();
		alignCenter();
		return this;
	}

	public Alignment setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	private String p(String any) {
		if (prefix == null) {
			return any;
		}
		return prefix + any.substring(0, 1).toUpperCase() + any.substring(1);
	}
}
