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

package com.storedobject.chart.data;

import com.storedobject.chart.util.TriConsumer;

/**
 * Numeric data. (A list-based data provider).
 *
 * @author Syam
 */
public class Data extends AbstractData<Number> implements DataProvider {
	private static final long serialVersionUID = 8498054049320509830L;

	private TriConsumer<StringBuilder, Number, Integer> _dataEncoder;

	/**
	 * Constructor.
	 *
	 * @param data Initial data to add
	 */
	public Data(Number... data) {
		super(Number.class, data);
	}

	@Override
	public TriConsumer<StringBuilder, Number, Integer> getDataEncoder() {
		if (_dataEncoder == null) {
			_dataEncoder = super.getDataEncoder();
		}

		return _dataEncoder;
	}

	public Data setDataEncoder(TriConsumer<StringBuilder, Number, Integer> dataEncoder) {
		_dataEncoder = dataEncoder;

		return this;
	}
}
