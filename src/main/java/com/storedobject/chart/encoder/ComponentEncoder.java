package com.storedobject.chart.encoder;

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
			label = partType.getName();
			label = label.substring(label.lastIndexOf('.') + 1);
			label = Character.toLowerCase(label.charAt(0)) + label.substring(1);
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
		Set<Integer> serials = new HashSet<>();
		List<ComponentPart> partList = parts.stream().filter(this::support).filter(part -> {
			int serial = part.getSerial();
			boolean first = !serials.contains(serial);
			if (first) {
				serials.add(serial);
			}
			return first;
		}).collect(Collectors.toList());

		boolean first = true;
		int serial = -2;
		int partCount = partList.size();
		for (ComponentPart part : partList) {
			if (part.getSerial() < serial)
				break;

			serial = part.getSerial();
			if (first) {
				first = false;
				if (sb.length() > 1) {
					sb.append(',');
				}
				sb.append('"').append(label).append("\":");
				begin(sb, partCount);

			} else {
				sb.append(',');
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
