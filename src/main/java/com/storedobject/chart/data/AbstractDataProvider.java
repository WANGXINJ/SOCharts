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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
		sb.append("\"d").append(getSerial()).append("\":");
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

	default void encodeData(StringBuilder sb) {
		sb.append(",\"data\":");
		encodeDataContent(sb);
	}

	default void encodeDataContent(StringBuilder sb) {
		encode(sb, stream(), "[", "]", true, getDataEncoder());
	}

	public default boolean isDataSetEncoding() {
		return getDataEncoder() == DEFAULT_DATA_ENCODER;
	}

	public static TriConsumer<StringBuilder, Object, Integer> DEFAULT_DATA_ENCODER = (sb, data, index) -> sb
			.append(ComponentPart.escape(data));

	default TriConsumer<StringBuilder, T, Integer> getDataEncoder() {
		@SuppressWarnings("unchecked")
		TriConsumer<StringBuilder, T, Integer> defaultDataEncoder = (TriConsumer<StringBuilder, T, Integer>) DEFAULT_DATA_ENCODER;
		return defaultDataEncoder;
	}

	/**
	 * Append the JSON encoding of all values coming from a stream to the given
	 * string builder.
	 *
	 * @param sb           Append the JSONified string to this.
	 * @param dataStream   Sream of data values.
	 * @param prefix       Prefix to add.
	 * @param suffix       Suffix to add.
	 * @param appendAnyway Append prefix and suffix even if data is empty.
	 * @param dataEncoder  Encoder for the value read from the stream. If
	 *                     <code>null</code> is passed, stringified version will be
	 *                     appended.
	 * @param <DATA>       Type of data value in the stream.
	 */
	static <DATA> void encode(StringBuilder sb, Stream<DATA> dataStream, String prefix, String suffix,
			boolean appendAnyway, TriConsumer<StringBuilder, DATA, Integer> dataEncoder) {
		AtomicInteger index = new AtomicInteger(-1);
		dataStream.forEach(data -> {
			boolean first = index.incrementAndGet() == 0;
			if (first) {
				sb.append(prefix);
			} else {
				sb.append(',');
			}

			dataEncoder.accept(sb, data, index.get());
		});

		if (index.get() == -1) {
			if (appendAnyway) {
				sb.append(prefix).append(suffix);
			}
		} else {
			sb.append(suffix);
		}
	}

	@Override
	default long getId() {
		return -1L;
	}

	@Override
	default void validate() throws ChartException {
	}
}
