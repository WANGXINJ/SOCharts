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

import static com.storedobject.chart.util.ComponentPropertyUtil.encodeValueProperty;

/**
 * Represents an abstract {@link ComponentPart} with some common base
 * properties.
 *
 * @author Syam
 */
public abstract class VisiblePart extends AbstractPart {

	boolean show = true;

	/**
	 * Show this part.
	 */
	public void show() {
		show = true;
	}

	/**
	 * Hide this part.
	 */
	public void hide() {
		show = false;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("show", show);
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		if (!show) {
			encodeValueProperty("show", false, sb);
			return;
		}

		super.encodeJSON(sb);
	}
}
