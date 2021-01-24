package com.storedobject.chart.encoder;

import java.util.List;
import java.util.stream.Collectors;

import com.storedobject.chart.component.ComponentParts;
import com.storedobject.chart.coordinate_system.Axis;
import com.storedobject.chart.coordinate_system.Axis.AxisWrapper;
import com.storedobject.chart.data.CategoryDataProvider;
import com.storedobject.chart.data.DataType;

public abstract class AxisEncoder extends ComponentEncoder {

	protected AxisEncoder(String label, Class<? extends AxisWrapper> axisType) {
		super(label, axisType);
	}

	@Override
	protected void afterPartEncode(StringBuilder sb, ComponentParts parts) {
		if (parts.isDataSetEncoding())
			return;

		encodeCategoryDataIfNecessary(sb, parts);
	}

	protected void encodeCategoryDataIfNecessary(StringBuilder sb, ComponentParts parts) {
		@SuppressWarnings("unchecked")
		List<AxisWrapper> axisWrapperList = (List<AxisWrapper>) parts.stream().filter(this::support)
				.collect(Collectors.toList());
		if (axisWrapperList.size() != 1)
			return;

		Axis axis = axisWrapperList.get(0).getAxis();
		if (!axis.isDataType(DataType.CATEGORY))
			return;

		List<CategoryDataProvider> categoryDataList = parts.categoryDataProviderStream().collect(Collectors.toList());
		if (categoryDataList.size() != 1)
			return;

		CategoryDataProvider categoryData = categoryDataList.get(0);
		categoryData.encodeData(sb);
	}
}
