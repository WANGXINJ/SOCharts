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

import com.storedobject.chart.coordinate_system.HasPosition;
import com.storedobject.chart.property.AbstractComponentProperty;
import com.storedobject.chart.property.HasPadding;
import com.storedobject.chart.property.HasPolarProperty;
import com.storedobject.helper.ID;

import java.util.Objects;

/**
 * Represents an abstract {@link ComponentPart} with some common base
 * properties.
 *
 * @author Syam
 */
public abstract class AbstractPart extends AbstractComponentProperty implements ComponentPart {

	private int serial;
	private final long id = ID.newID();

	/**
	 * Get a unique Id for this part.
	 *
	 * @return Unique Id.
	 */
	@Override
	public final long getId() {
		return id;
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("id", id);
		property("name", getName());
		if (this instanceof HasPosition) {
			property(((HasPosition) this).getPosition(false));
		}
		if (this instanceof HasPadding) {
			property(((HasPadding) this).getPadding(false));
		}
		if (this instanceof HasPolarProperty) {
			property(((HasPolarProperty) this).getPolarProperty(false));
		}
	}

	/**
	 * Get the name of this part. It could be null if not set or not supported.
	 *
	 * @return Default is <code>null</code>.
	 */
	@Override
	public String getName() {
		return null;
	}

	@Override
	public final int getSerial() {
		return serial;
	}

	@Override
	public final void setSerial(int serial) {
		this.serial = serial;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AbstractPart that = (AbstractPart) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}