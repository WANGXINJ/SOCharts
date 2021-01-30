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

import com.storedobject.chart.coordinate_system.Axis;

/**
 * Tooltip to display. Basic support only. Future versions will provide more
 * detailed features.
 *
 * @author Syam
 */
public class Tooltip extends VisiblePart implements Component, SinglePart, SkipPart {

	private Trigger trigger;
	private Axis.Pointer axisPointer;

	public Trigger getTrigger() {
		return trigger;
	}

	public Tooltip setTrigger(Trigger trigger) {
		this.trigger = trigger;
		return this;
	}

	public Axis.Pointer getAxisPointer(boolean create) {
		if (axisPointer == null && create) {
			axisPointer = new Axis.Pointer();
		}
		return axisPointer;
	}

	public Tooltip setAxisointer(Axis.Pointer axisPointer) {
		this.axisPointer = axisPointer;
		return this;
	}

	public Axis.PointerType getAxisPointerType() {
		return axisPointer != null ? axisPointer.getType() : Axis.PointerType.LINE;
	}

	public void setAxisPointerType(Axis.PointerType axisPointerType) {
		getAxisPointer(true).setType(axisPointerType);
	}

	@Override
	protected void buildProperties() {
		super.buildProperties();

		property("trigger", trigger);
		property("axisPointer", axisPointer);
	}

	@Override
	public void validate() {
	}

	public static enum Trigger {
		item, //
		axis, //
		none //
		;
	}
}
