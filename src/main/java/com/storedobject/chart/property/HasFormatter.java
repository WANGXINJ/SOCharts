package com.storedobject.chart.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface HasFormatter<PROPERTY extends ComponentProperty> {

	public String getFormatter();

	public PROPERTY setFormatter(Formatter fomatter);

	public default PROPERTY setFormatter(String formatter, Format... formats) {
		return setFormatter(new Formatter(formatter, formats));
	}

	public default PROPERTY setFormatter(Format... formats) {
		return setFormatter(new Formatter(formats));
	}

	public default PROPERTY setFormatter(Format format) {
		return setFormatter(new Formatter(format));
	}

	public static class Formatter {
		private String pattern;
		final private List<Format> formatList;

		public Formatter(Format... formats) {
			String pattern = null;
			List<Format> formatList = new ArrayList<>();
			if (formats != null && formats.length != 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < formats.length; i++) {
					Format format = formats[i];
					if (format == null)
						continue;

					sb.append("%s");
					formatList.add(format);
				}

				pattern = sb.toString();
			}

			this.pattern = pattern;
			this.formatList = formatList;
		}

		public Formatter(String pattern, Format... formats) {
			this(pattern, formats != null ? Arrays.asList(formats) : null);
		}

		public Formatter(String pattern, List<Format> formatList) {
			this.pattern = pattern;
			this.formatList = new ArrayList<>(formatList);
		}

		public Formatter appendFormat(Format format) {
			pattern = pattern != null ? pattern + "%s" : "%s";
			formatList.add(format);
			return this;
		}

		public int countFormats() {
			return formatList.size();
		}

		public boolean containsFormat(Format format) {
			return containsFormat(format.getDatakey());
		}

		public boolean containsFormat(String dataKey) {
			return formatList.stream().anyMatch(format -> Objects.equals(format.getDatakey(), dataKey));
		}

		public Formatter cloneFormatTo(int index, String dataKey) {
			if (!(index > formatList.size() - 1)) {
				formatList.set(index, formatList.get(index).clone(dataKey));
			}
			return this;
		}

		@Override
		public String toString() {
			return pattern != null ? String.format(pattern, formatList.toArray()) : null;
		}
	}
}
