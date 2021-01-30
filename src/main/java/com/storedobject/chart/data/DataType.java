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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Type of data that can be used by charts.
 *
 * @author Syam
 */
public enum DataType {

	/**
	 * Numeric values.
	 */
	NUMBER("value", Number.class),
	/**
	 * Category values.
	 */
	CATEGORY("category", String.class),
	/**
	 * Date values.
	 */
	DATE("time", LocalDate.class),
	/**
	 * Time values.
	 */
	TIME("time", LocalDateTime.class),
	/**
	 * java.util.Date values, similar to TIME.
	 */
	LEGACY_DATE("time", Date.class),
	/**
	 * Logarithmic values.
	 */
	LOGARITHMIC("log", Number.class);

	private final String name;
	private final Class<?> type;

	/**
	 * Constructor (internally used).
	 *
	 * @param name Name.
	 * @param type Type.
	 */
	DataType(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Get the type.
	 *
	 * @return Type.
	 */
	final public Class<?> getType() {
		return type;
	}

	final public boolean support(Class<?> clazz) {
		return type.isAssignableFrom(clazz);
	}

	final public Object mapValue(Object value) {
		return mapValue(value, ZoneId.systemDefault());
	}

	final public Object mapValue(Object value, ZoneId zoneId) {
		if (value == null) {
			return null;
		}

		switch (this) {
		case CATEGORY:
			return value;

		case NUMBER:
		case LOGARITHMIC:
			break;

		case TIME:
			if (value instanceof LocalDate) {
				value = ((LocalDate) value).atStartOfDay();
			} else if (value instanceof Date) {
				value = ((Date) value).toInstant().atZone(zoneId).toLocalDateTime();
			}
			break;

		case DATE:
			if (value instanceof LocalDateTime) {
				value = ((LocalDateTime) value).toLocalDate();
			} else if (value instanceof Date) {
				value = ((Date) value).toInstant().atZone(zoneId).toLocalDate();
			}
			break;

		case LEGACY_DATE:
			if (value instanceof LocalDate) {
				value = Date.from(((LocalDate) value).atStartOfDay(zoneId).toInstant());
			} else if (value instanceof LocalDateTime) {
				value = Date.from(((LocalDateTime) value).atZone(zoneId).toInstant());
			}
			break;
		}

		return support(value.getClass()) ? value : null;
	}

	@Override
	public String toString() {
		return "\"" + name + "\"";
	}

	final static public DataType typeFor(Class<?> clazz) {
		return Arrays.stream(values()).filter(dataType -> dataType.support(clazz)).findAny().orElse(null);
	}

	final static public Object mapValue(Object value, DataType dataType, Consumer<DataType> setter) {
		return mapValue(value, dataType, ZoneId.systemDefault(), setter);
	}

	final static public Object mapValue(Object value, DataType dataType, ZoneId zoneId, Consumer<DataType> setter) {
		if (value == null) {
			return null;
		}

		if (dataType != null) {
			return dataType.mapValue(value, zoneId);
		}

		if (setter != null) {
			setter.accept(typeFor(value.getClass()));
		}
		return value;
	}
}
