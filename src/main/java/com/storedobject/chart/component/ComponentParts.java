package com.storedobject.chart.component;

import static com.storedobject.chart.SOChart.SKIP_DATA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.storedobject.chart.SOChart;
import com.storedobject.chart.data.AbstractData;
import com.storedobject.chart.data.AbstractDataProvider;
import com.storedobject.chart.data.CategoryDataProvider;
import com.storedobject.chart.data.DataProvider;
import com.storedobject.chart.encoder.ComponentEncoder;
import com.storedobject.chart.util.ChartException;

public class ComponentParts implements Iterable<ComponentPart> {

	final private List<ComponentPart> parts = new ArrayList<>();
	private boolean skipping;

	public boolean isSkippingData() {
		return skipping;
	}

	public ComponentParts skippingData(boolean skipping) {
		this.skipping = skipping;
		return this;
	}

	public ComponentParts addAll(ComponentPart... parts) {
		if (parts == null) {
			return this;
		}

		return addAll(Arrays.asList(parts));
	}

	public ComponentParts addAll(Iterable<? extends ComponentPart> parts) {
		if (parts == null) {
			return this;
		}

		for (ComponentPart part : parts) {
			add(part);
		}

		return this;
	}

	public ComponentParts add(ComponentPart part) {
		if (part == null) {
			return this;
		}

		if (skipping && part instanceof SkipPart) {
			return this;
		}

		if (part instanceof Collection && ((Collection<?>) part).isEmpty()) {
			return this;
		}

		int index = -1;
		if (part instanceof SinglePart) {
			Class<?> singlePartClass = getClassWithInterface(part.getClass(), SinglePart.class);
			for (int i = 0; i < parts.size(); i++) {
				if (singlePartClass.isAssignableFrom(parts.get(i).getClass())) {
					index = i;
					break;
				}
			}
		}

		if (index == -1) {
			parts.add(part);
		}

		return this;
	}

	public ComponentParts init(List<Component> components, boolean skipData) throws ChartException {
		clear().skippingData(skipData);

		for (Component component : components) {
			component.addPartsInto(this);
		}

		for (ComponentPart part : parts) {
			if (skipping) {
				if (part instanceof AbstractData) {
					if (part.getSerial() < 0) {
						throw new ChartException(SKIP_DATA + part.className());
					}
					continue;
				}
			}
			part.validate();
			part.setSerial(-2);
		}

		parts.addAll(components);

		return this;
	}

	public ComponentParts setupPartSerial() {
		for (ComponentEncoder encoder : SOChart.encoders) {
			int serial = 0;
			List<ComponentPart> encodePartList = parts.stream()
					.filter(part -> encoder.support(part) && part.getSerial() == -2).collect(Collectors.toList());
			Map<ComponentPart, List<ComponentPart>> serialPartMap = encodePartList.stream()
					.collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.toList()));
			for (List<ComponentPart> serialPartList : serialPartMap.values()) {
				for (ComponentPart serialPart : serialPartList) {
					serialPart.setSerial(serial);
				}
				serial++;
			}
		}

		parts.sort(Comparator.comparing(ComponentPart::getSerial));

		return this;
	}

	public ComponentParts clear() {
		parts.clear();
		skipping = false;

		return this;
	}

	@Override
	public Iterator<ComponentPart> iterator() {
		return parts.iterator();
	}

	public Stream<? extends ComponentPart> stream() {
		return parts.stream();
	}

	public boolean isDataSetEncoding() {
		return dataProviderStream().allMatch(dataProvider -> dataProvider.isDataSetEncoding());
	}

	public Stream<AbstractDataProvider<?>> dataProviderStream() {
		return stream().filter(part -> part instanceof AbstractDataProvider)
				.map(part -> (AbstractDataProvider<?>) part);
	}

	public Stream<CategoryDataProvider> categoryDataProviderStream() {
		return stream().filter(part -> part instanceof CategoryDataProvider).map(part -> (CategoryDataProvider) part);
	}

	public Stream<DataProvider> valueDataProviderStream() {
		return stream().filter(part -> part instanceof DataProvider).map(part -> (DataProvider) part);
	}

	private Class<?> getClassWithInterface(Class<?> clazz, Class<?> interfaze) {
		while (!Objects.equals(clazz, Object.class)) {
			for (Class<?> clazzInterface : clazz.getInterfaces()) {
				if (Objects.equals(clazzInterface, interfaze)) {
					return clazz;
				}
			}

			clazz = clazz.getSuperclass();
		}

		return null;
	}

	public static ComponentParts of(ComponentPart... parts) {
		return new ComponentParts().addAll(parts);
	}
}
