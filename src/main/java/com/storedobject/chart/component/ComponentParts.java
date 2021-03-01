package com.storedobject.chart.component;

import static com.storedobject.chart.SOChart.SKIP_DATA;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.vaadin.event.SerializableEventListener;
import com.vaadin.util.ReflectTools;

public class ComponentParts implements Iterable<ComponentPart> {

	final private List<ComponentPart> parts = new ArrayList<>();
	private boolean datasetByChart;
	private boolean skippingData;

	public boolean isDatasetByChart() {
		return datasetByChart;
	}

	public ComponentParts setDatasetByChart(boolean datasetByChart) {
		this.datasetByChart = datasetByChart;
		return this;
	}

	public boolean isSkippingData() {
		return skippingData;
	}

	public ComponentParts setSkippingData(boolean skipping) {
		this.skippingData = skipping;
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

		if (skippingData && part instanceof SkipPart) {
			return this;
		}

// TODO check to remove
//		if (part instanceof Collection && ((Collection<?>) part).isEmpty()) {
//			return this;
//		}

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
		clear().setSkippingData(skipData);

		for (Component component : components) {
			component.addPartsInto(this);
		}

		for (ComponentPart part : parts) {
			if (skippingData) {
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

	public void setupPartSerial() {
		setupExactEqualParts();

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
	}

	public ComponentParts clear() {
		parts.clear();
		skippingData = false;

		return this;
	}

	@Override
	public Iterator<ComponentPart> iterator() {
		return parts.iterator();
	}

	public Stream<? extends ComponentPart> stream() {
		return parts.stream();
	}

	public int size() {
		return parts.size();
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

	public Stream<Chart> chartStream() {
		return stream().filter(part -> part instanceof Chart).map(part -> (Chart) part);
	}

	public <PART extends ComponentPart> Stream<PART> partStream(Class<PART> partClass) {
		@SuppressWarnings("unchecked")
		Stream<PART> stream = (Stream<PART>) stream().filter(part -> partClass.isAssignableFrom(part.getClass()));
		return stream;
	}

	private void setupExactEqualParts() {
		partStream(ExactEqualPart.class).filter(part -> part instanceof AbstractDataProvider)
				.forEach(part -> part.setExactEqual(isDatasetByChart()));
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

	public static ComponentParts of(Iterable<? extends ComponentPart> parts) {
		return new ComponentParts().addAll(parts);
	}

	public static class Setup {

		public interface Listener extends SerializableEventListener {
			public static final Method SETUP_METHOD = ReflectTools.findMethod(Listener.class, "onSetup", Event.class);

			void onSetup(Event event);
		}

		public static class Event extends com.vaadin.ui.Component.Event {
			private static final long serialVersionUID = -1374743977934914349L;

			private ComponentParts parts;

			public Event(SOChart chart, ComponentParts parts) {
				super(chart);

				this.parts = parts;
			}

			public SOChart getChart() {
				return (SOChart) getSource();
			}

			public ComponentParts getParts() {
				return parts;
			}
		}
	}
}
