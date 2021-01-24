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
 * Represents a common base for {@link ComponentProperty} with visibility as a
 * property.
 *
 * @author Syam
 */
public abstract class VisibleComponentProperty extends BaseComponentProperty {

	private Boolean show;

	protected VisibleComponentProperty(String name) {
		super(name);
	}

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
}
