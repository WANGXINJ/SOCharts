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

import static com.storedobject.chart.util.ComponentPropertyUtil.encodeStream;
import static com.storedobject.chart.util.ComponentPropertyUtil.escape;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.storedobject.chart.component.ComponentPart;
import com.storedobject.chart.util.ChartException;
import com.storedobject.chart.util.TriConsumer;

/**
 * Abstract data provider interface. The type of data can be anything that can
 * be used for charting. In charting, we need to distinguish between "numeric",
 * "date/time", "categories" and "logarithmic" values types. (See
 * {@link DataType}).
 *
 * @param <T> Data class type.
 * @author Syam
 */
public interface AbstractDataProvider<T> extends ComponentPart {

	/**
	 * Data provided by this provider as a stream.
	 *
	 * @return Stream of data values.
	 */
	Stream<T> stream();

	/**
	 * Collect all data values into a list.
	 *
	 * @return Data values as a list.
	 */
	default List<T> asList() {
		if (this instanceof List) {
			// noinspection unchecked
			@SuppressWarnings("unchecked")
			List<T> list = (List<T>) this;
			return list;
		}
		List<T> list = new ArrayList<>();
		stream().forEach(list::add);
		return list;
	}

	/**
	 * Get the value type of the data.
	 *
	 * @return value type.
	 */
	DataType getDataType();

	@Override
	default void encodeJSON(StringBuilder sb) {
		sb.append("\"").append(datasetName()).append("\":");
		encodeDataSet(sb);
	}

	/**
	 * Append the JSON encoding of this to the given string builder.
	 *
	 * @param sb Append the JSONified string to this.
	 */
	default void encodeDataSet(StringBuilder sb) {
		if (isDataSetEncoding()) {
			encodeDataContent(sb);
		} else {
			sb.append("[]");
		}
	}

	default StringBuilder encodeData(StringBuilder sb) {
		sb.append(",\"data\":");
		return encodeDataContent(sb);
	}

	default StringBuilder encodeDataContent(StringBuilder sb) {
		return encodeStream(sb, stream(), "[", "]", true, getDataEncoder());
	}

	public default boolean isDataSetEncoding() {
		return getDataEncoder() == DEFAULT_DATA_ENCODER;
	}

	public default boolean nonDataSetEncoding() {
		return !isDataSetEncoding();
	}

	public static TriConsumer<StringBuilder, Object, Integer> DEFAULT_DATA_ENCODER = (sb, data, index) -> sb
			.append(escape(data));

	default TriConsumer<StringBuilder, T, Integer> getDataEncoder() {
		@SuppressWarnings("unchecked")
		TriConsumer<StringBuilder, T, Integer> defaultDataEncoder = (TriConsumer<StringBuilder, T, Integer>) DEFAULT_DATA_ENCODER;
		return defaultDataEncoder;
	}

	@Override
	default long getId() {
		return -1L;
	}

	default String datasetName() {
		return "d" + getSerial();
	}

	@Override
	default void validate() throws ChartException {
	}
}
