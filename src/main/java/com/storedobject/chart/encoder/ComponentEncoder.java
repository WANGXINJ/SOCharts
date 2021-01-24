package com.storedobject.chart.encoder;

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
		boolean first = true;
		int serial = -2;
		for (ComponentPart part : parts) {
			if (!support(part) || part.getSerial() == serial)
				continue;

			if (part.getSerial() < serial)
				break;

			serial = part.getSerial();
			if (first) {
				first = false;
				if (sb.length() > 1) {
					sb.append(',');
				}
				sb.append('"').append(label).append("\":");
				begin(sb);

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
			end(sb);
		}
	}

	protected void partEnd(StringBuilder sb) {
		sb.append('}');
	}

	protected void begin(StringBuilder sb) {
		sb.append('[');
	}

	protected void partBegin(StringBuilder sb) {
		sb.append('{');
	}

	protected void end(StringBuilder sb) {
		sb.append(']');
	}

	protected void afterPartEncode(StringBuilder sb, ComponentParts parts) {
		// FI
	}
}
