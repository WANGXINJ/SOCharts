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

import com.storedobject.chart.PieChart;

/**
 * Represents label property. Certain charts supports this property (Example:
 * {@link PieChart}.
 *
 * @author xj
 */
public class LabelProperty extends BaseComponentProperty {

	private Integer fontSize;

	public LabelProperty() {
		super("label");
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public LabelProperty setFontSize(Integer fontSize) {
		this.fontSize = fontSize;

		return this;
	}

	@Override
	protected void addProperties() {
		super.addProperties();

		property("fontSize", fontSize);
	}
}
