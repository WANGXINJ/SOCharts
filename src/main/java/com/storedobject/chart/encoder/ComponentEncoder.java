package com.storedobject.chart.encoder;

import static com.storedobject.chart.component.ComponentPart.addComma;
import static com.storedobject.chart.util.ComponentPropertyUtil.camelName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.storedobject.chart.component.ComponentPart;
import com.storedobject.chart.component.ComponentParts;

public abstract class ComponentEncoder {
	final String label;
	final Class<? extends ComponentPart> partType;

	protected ComponentEncoder(Class<? extends ComponentPart> partType) {
		this(null, partType);
	}

	protected ComponentEncoder(String label, Class<? extends ComponentPart> partType) {
		this.partType = partType;
		if (label == null) {
			label = camelName(partType.getSimpleName());
		}
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public Class<? extends ComponentPart> getPartType() {
		return partType;
	}

	public boolean support(ComponentPart part) {
		return partType.isAssignableFrom(part.getClass());
	}

	public boolean exact(ComponentPart part) {
		return partType == part.getClass();
	}

	public void encode(StringBuilder sb, ComponentParts parts) {
		parts = supportedParts(parts);
		if (parts.size() < 1)
			return;

		encodeLabel(sb);
		encodeParts(sb, parts);
	}

	protected void encodeParts(StringBuilder sb, ComponentParts parts) {
		boolean first = true;
		int serial = -2;
		int partCount = parts.size();
		for (ComponentPart part : parts) {
			if (part.getSerial() < serial)
				break;

			serial = part.getSerial();
			if (first) {
				first = false;
				begin(sb, partCount);

			} else {
				addComma(sb);
			}

			partBegin(sb);

			part.encodeJSON(sb);
			afterPartEncode(sb, parts);

			ComponentPart.removeComma(sb);
			partEnd(sb);
		}

		if (!first) {
			end(sb, partCount);
		}
	}

	protected ComponentParts supportedParts(ComponentParts parts) {
		Set<Integer> serials = new HashSet<>();
		List<ComponentPart> partList = parts.stream().filter(this::support).filter(part -> {
			int serial = part.getSerial();
			boolean first = !serials.contains(serial);
			if (first) {
				serials.add(serial);
			}
			return first;
		}).collect(Collectors.toList());

		return ComponentParts.of(partList);
	}

	protected void encodeLabel(StringBuilder sb) {
		addComma(sb);
		sb.append('"').append(label).append("\":");
	}

	protected void begin(StringBuilder sb, int partCount) {
		if (partCount > 1) {
			sb.append('[');
		}
	}

	protected void partBegin(StringBuilder sb) {
		sb.append('{');
	}

	protected void partEnd(StringBuilder sb) {
		sb.append('}');
	}

	protected void end(StringBuilder sb, int partCount) {
		if (partCount > 1) {
			sb.append(']');
		}
	}

	protected void afterPartEncode(StringBuilder sb, ComponentParts parts) {
		// FI
	}
}
