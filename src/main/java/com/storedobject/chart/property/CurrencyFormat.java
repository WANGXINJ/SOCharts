package com.storedobject.chart.property;

import java.util.Currency;

public class CurrencyFormat extends AbstractNumberFormat {

	final private Currency currency;

	public CurrencyFormat(String dataKey, int fraction, boolean kSeparate, Currency currency) {
		super("n", dataKey, fraction, kSeparate);

		this.currency = currency;

		setPrefix(currency.getSymbol());
	}

	@Override
	public CurrencyFormat clone(String dataKey) {
		return new CurrencyFormat(dataKey, getFraction(), isKSeparate(), currency);
	}
}
