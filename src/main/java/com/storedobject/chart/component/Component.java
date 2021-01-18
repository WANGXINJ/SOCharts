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

import com.storedobject.chart.SOChart;
import com.storedobject.chart.coordinate_system.CoordinateSystem;
import com.storedobject.chart.coordinate_system.Position;
import com.storedobject.chart.coordinate_system.RectangularCoordinate;

/**
 * <p>
 * Chart component. Chart components can be added to the {@link SOChart} for
 * rendering using {@link SOChart#add(Component...)} method. Some chart
 * components have other components as its parts. Thus, those will be
 * automatically added to the same chart when the component is added. For
 * example: If a {@link ChartType#Line} chart, plotted on a
 * {@link RectangularCoordinate}, is added, the {@link RectangularCoordinate}
 * will be automatically added. However, it is not an error if you add both of
 * them separately.
 * </p>
 * <p>
 * You may have this question: Why would I ever add a {@link CoordinateSystem}
 * if I am not going to plot any chart on it? The answer is, sometimes, it may
 * be useful for creating some special effects! {@link SOChart} is like a
 * "canvas", whatever you add as components will be rendered there, one on top
 * of others, unless you position them within the canvas (Many
 * {@link Component}s and {@link ComponentPart}s support
 * setPosition({@link Position} method).
 * </p>
 *
 * @author Syam
 */
public interface Component extends ComponentPart {

	/**
	 * Add parts of this component to the chart. This will be invoked if the
	 * component was already added to the chart and it is about to be rendered.
	 *
	 * @param soChart Chart to which components to be added.
	 */
	default void addParts(SOChart soChart) {
	}

	/**
	 * This method is invoked whenever chart is getting updated and no data is
	 * passed to the client. The component's {@link #encodeJSON(StringBuilder)}
	 * method may not encode any data-specific part in this mode. (Most of the
	 * {@link Chart}s handle data in a centralized way and in that case, this method
	 * needs not take care of anything special. However, there are charts that
	 * handle data in a specific way - example: {@link GaugeChart},
	 * {@link RadarChart} etc.). This is invoked even before {@link #validate()} is
	 * invoked.
	 *
	 * @param skipping True if skipping data.
	 */
	default void skippingData(boolean skipping) {
	}
}