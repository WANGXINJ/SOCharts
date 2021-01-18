package com.storedobject.chart.property;

import java.util.ArrayList;

import com.storedobject.chart.component.ComponentPart;

public class DefaultColors extends ArrayList<Color> implements ComponentPart {
	private static final long serialVersionUID = -8713125353157770367L;

	private static final String[] colors = new String[] { "0000ff", "c23531", "2f4554", "61a0a8", "d48265", "91c7ae",
			"749f83", "ca8622", "bda29a", "6e7074", "546570", "c4ccd3" };
	private int serial;

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public void validate() {
	}

	@Override
	public void encodeJSON(StringBuilder sb) {
		sb.append("\"color\":[");
		int count = 0;
		boolean first = true;
		for (Color c : this) {
			if (c == null) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append(c);
			++count;
		}
		Color c;
		for (int i = 0; count < 11; i++) {
			c = new Color(colors[i]);
			if (this.contains(c)) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append(c);
			++count;
		}
		sb.append(']');
	}

	@Override
	public final int getSerial() {
		return serial;
	}

	@Override
	public void setSerial(int serial) {
		this.serial = serial;
	}
}
