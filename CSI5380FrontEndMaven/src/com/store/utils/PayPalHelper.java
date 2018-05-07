package com.store.utils;

import com.paypal.base.rest.APIContext;

public abstract class PayPalHelper {
	private static final String clientId = "{paypal_client_id}";
	private static final String clientSecret = "{paypal_clientSecret}";
	private static final APIContext context = new APIContext(clientId,clientSecret,"sandbox");

	public static APIContext getAPIContext() {
		return context;
	}
}
