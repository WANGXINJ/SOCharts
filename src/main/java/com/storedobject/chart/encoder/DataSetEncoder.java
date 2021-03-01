package com.storedobject.chart.encoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.storedobject.chart.component.Chart;
import com.storedobject.chart.component.ComponentParts;
import com.storedobject.chart.data.AbstractDataProvider;

public class DataSetEncoder extends ComponentEncoder {

	public DataSetEncoder() {
		super("dataset", AbstractDataProvider.class);
	}

	@Override
	public void encode(StringBuilder sb, ComponentParts parts) {
		if (parts.isSkippingData() || !parts.isDataSetEncoding())
			return;

		ComponentParts dataProviderParts = supportedParts(parts);
		if (dataProviderParts.size() < 1)
			return;

		Function<AbstractDataProvider<?>, Object> mapKey;
		if (dataProviderParts.isDatasetByChart()) {
			Chart dummyChart = new Chart();
			mapKey = dataProvider -> parts.chartStream() //
					.filter(chart -> Arrays.asList(chart.getData()).contains(dataProvider)) //
					.findAny().orElse(dummyChart);
		} else {
			mapKey = dataProvider -> dataProvider.stream().count();
		}

		Map<Object, List<AbstractDataProvider<?>>> dataProviderMap = dataProviderParts.stream()
				.map(part -> (AbstractDataProvider<?>) part)
				.collect(Collectors.groupingBy(mapKey, LinkedHashMap::new, Collectors.toList()));

		List<Object> dataProvidCountList = new ArrayList<>(dataProviderMap.keySet());
		boolean singleDataset = dataProvidCountList.size() == 1;
		encodeLabel(sb);
		if (!singleDataset) {
			sb.append("[");
		}
		for (int i = 0; i < dataProvidCountList.size(); i++) {
			List<AbstractDataProvider<?>> dataProviderList = dataProviderMap.get(dataProvidCountList.get(i));
			for (AbstractDataProvider<?> dataProvider : dataProviderList) {
				dataProvider.setDatasetIndex(i);
			}
			encodeParts(sb, ComponentParts.of(dataProviderList));
		}
		if (!singleDataset) {
			sb.append("],");
		}
	}

	@Override
	protected ComponentParts supportedParts(ComponentParts parts) {
		return super.supportedParts(parts).setDatasetByChart(parts.isDatasetByChart());
	}

	@Override
	protected void begin(StringBuilder sb, int partCount) {
		sb.append("{\"source\":{");
	}

	@Override
	protected void partBegin(StringBuilder sb) {
		// NOOP
	}

	@Override
	protected void partEnd(StringBuilder sb) {
		// NOOP
	}

	@Override
	protected void end(StringBuilder sb, int partCount) {
		sb.append("}},");
	}
}
