package com.storedobject.chart.encoder;

import static com.storedobject.chart.SOChart.isDataSetEncoding;

import java.util.List;
import java.util.stream.Collectors;

import com.storedobject.chart.component.ComponentPart;
import com.storedobject.chart.coordinate_system.XAxis;
import com.storedobject.chart.data.CategoryDataProvider;

public class XAxisEncoder extends ComponentEncoder {

	public XAxisEncoder() {
		super("xAxis", XAxis.XAxisWrapper.class);
	}

	@Override
	protected void afterPartEncode(StringBuilder sb, List<ComponentPart> parts) {
		if (isDataSetEncoding(parts))
			return;

		long xAxisCount = parts.stream().filter(part -> support(part)).count();
		if (xAxisCount > 1)
			return;

		List<CategoryDataProvider> categoryDataList = parts.stream()
				.filter(dataProvider -> dataProvider instanceof CategoryDataProvider)
				.map(dataProvider -> (CategoryDataProvider) dataProvider).collect(Collectors.toList());
		if (categoryDataList.size() != 1)
			return;

		CategoryDataProvider categoryData = categoryDataList.get(0);
		categoryData.encodeData(sb);
	}
}
