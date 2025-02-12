package com.storedobject.chart.property;

import java.util.Currency;
import java.util.Locale;

public abstract class Format {

	private final static Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.getDefault());

	final private String dataKey;
	final private String key;
	private String hide = "", prefix = "", suffix = "";

	protected Format(String key, String dataKey) {
		this.key = key;
		this.dataKey = dataKey;
	}

	public String getDatakey() {
		return dataKey;
	}

	public String getPrefix() {
		return prefix;
	}

	public Format setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public String getSuffix() {
		return suffix;
	}

	public Format setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

	public Format hide() {
		this.hide = "?";
		return this;
	}

	@Override
	public String toString() {
		String pattern = "";
		if (key != null) {
			pattern = "%" + hide + key + prefix + "<" + pattern() + ">" + suffix;
		}
		return "{" + dataKey + pattern + "}";
	}

	@Override
	public Format clone() {
		return clone(dataKey);
	}

	public abstract Format clone(String dataKey);

	protected abstract String pattern();

	public static StringFormat stringFormat(String dataKey) {
		return new StringFormat(dataKey);
	}

	public static NumberFormat intFormat(String dataKey) {
		return numberFormat(dataKey, 0);
	}

	public static NumberFormat doubleFormat(String dataKey) {
		return numberFormat(dataKey, 2);
	}

	public static NumberFormat numberFormat(String dataKey, int fraction) {
		return numberFormat(dataKey, fraction, true);
	}

	public static NumberFormat numberFormat(String dataKey, int fraction, boolean kSeparate) {
		return new NumberFormat(dataKey, fraction, kSeparate);
	}

	public static PercentFormat percentFormat(String dataKey) {
		return percentFormat(dataKey, 0);
	}

	public static PercentFormat percentFormat(String dataKey, int fraction) {
		return percentFormat(dataKey, fraction, false);
	}

	public static PercentFormat percentFormat(String dataKey, int fraction, boolean kSeparate) {
		return new PercentFormat(dataKey, fraction, kSeparate);
	}

	public static CurrencyFormat currencyFormat(String dataKey) {
		return currencyFormat(dataKey, DEFAULT_CURRENCY);
	}

	public static CurrencyFormat currencyFormat(String dataKey, Currency currency) {
		return currencyFormat(dataKey, 2, currency);
	}

	public static CurrencyFormat currencyFormat(String dataKey, int fraction) {
		return currencyFormat(dataKey, fraction, DEFAULT_CURRENCY);
	}

	public static CurrencyFormat currencyFormat(String dataKey, int fraction, Currency currency) {
		return currencyFormat(dataKey, fraction, true, currency);
	}

	public static CurrencyFormat currencyFormat(String dataKey, int fraction, boolean kSeparate) {
		return currencyFormat(dataKey, fraction, kSeparate, DEFAULT_CURRENCY);
	}

	public static CurrencyFormat currencyFormat(String dataKey, int fraction, boolean kSeparate, Currency currency) {
		return new CurrencyFormat(dataKey, fraction, kSeparate, currency);
	}

	public static DateFormat dateFormat(String dataKey, String format) {
		return new DateFormat(dataKey, format);
	}
}
