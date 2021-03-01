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

import java.util.stream.Stream;

import com.storedobject.helper.StreamJava9;

/**
 * Implementation of serially increasing/decreasing numbers as data.
 *
 * @author Syam
 */
public class SerialData implements AbstractDataProvider<Integer> {

	private final int start, end, step;
	private int datasetIndex;

	/**
	 * Constructor.
	 *
	 * @param start Starting value.
	 * @param end   Ending value.
	 */
	public SerialData(int start, int end) {
		this(start, end, 1);
	}

	/**
	 * Constructor.
	 *
	 * @param start Starting value.
	 * @param end   Ending value.
	 * @param step  Step value.
	 */
	public SerialData(int start, int end, int step) {
		this.step = step == 0 ? 1 : step;
		if (this.step > 0) {
			this.start = Math.min(start, end);
			this.end = Math.max(start, end);
		} else {
			this.start = Math.max(start, end);
			this.end = Math.min(start, end);
		}
	}

	@Override
	public int getDatasetIndex() {
		return datasetIndex;
	}

	@Override
	public void setDatasetIndex(int datasetIndex) {
		this.datasetIndex = datasetIndex;
	}

	@Override
	public Stream<Integer> stream() {
		return StreamJava9.iterate(start, i -> (step > 1 ? i <= end : i >= end), i -> i + step);
	}

	@Override
	public DataType getDataType() {
		return DataType.NUMBER;
	}
}
