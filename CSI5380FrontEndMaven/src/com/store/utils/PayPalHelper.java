package com.store.utils;
import com.paypal.base.rest.APIContext;

public abstract class PayPalHelper {
	private static final String clientId = "Afl-_BBp23lC8f-BxadSKq11ssYooJJL7VKiBg2J6DwNI2aJ72PotSn76H1BbpxP14nSUSQ4PraKvQMg";
	private static final String clientSecret = "EJQlGyodc4zZ7X8VAqAy04O-dq6J5KitIdlI2VLz6UkygtAKzJXGSy9V77XKhFXDDECg0zcEtQoF0-pw";
	private static final APIContext context = new APIContext(clientId, clientSecret, "sandbox");

	public static APIContext getAPIContext() {
		return context;
	}
}
