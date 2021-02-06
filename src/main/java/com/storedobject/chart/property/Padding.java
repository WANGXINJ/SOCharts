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

import com.storedobject.chart.component.ComponentPart;

/**
 * Padding. Several {@link ComponentPart}s use this property to define padding
 * around them.
 *
 * @author Syam
 */
public class Padding extends PropertyComponentValue {

	private int paddingTop = 5, paddingRight = 5, paddingBottom = 5, paddingLeft = 5;

	/**
	 * Constructor.
	 */
	public Padding() {
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		int[] padding;
		if (paddingTop == paddingBottom && paddingLeft == paddingRight) {
			if (paddingTop == paddingLeft) {
				padding = new int[] { paddingTop };
			} else {
				padding = new int[] { paddingTop, paddingLeft };
			}
		} else {
			padding = new int[] { paddingTop, paddingRight, paddingBottom, paddingLeft };
		}
		property("padding", padding);
	}

	/**
	 * Set top padding.
	 *
	 * @param paddingTop Padding.
	 */
	public Padding setPaddingTop(int paddingTop) {
		this.paddingTop = Math.max(0, paddingTop);
		return this;
	}

	/**
	 * Set right padding.
	 *
	 * @param paddingRight Padding.
	 */
	public Padding setPaddingRight(int paddingRight) {
		this.paddingRight = Math.max(0, paddingRight);
		return this;
	}

	/**
	 * Set bottom padding.
	 *
	 * @param paddingBottom Padding.
	 */
	public Padding setGetPaddingBottom(int paddingBottom) {
		this.paddingBottom = Math.max(0, paddingBottom);
		return this;
	}

	/**
	 * Set left padding.
	 *
	 * @param paddingLeft Padding.
	 */
	public Padding setGetPaddingLeft(int paddingLeft) {
		this.paddingLeft = Math.max(0, paddingLeft);
		return this;
	}
}
