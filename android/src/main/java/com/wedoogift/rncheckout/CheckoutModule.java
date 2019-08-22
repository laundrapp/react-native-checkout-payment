package com.wedoogift.rncheckout;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;

/**
 * A module exposing the main features of Checkout.
 */
public interface CheckoutModule {
    /**
     * Initialize the Checkout client.
     * @param publicKey the public key
     * @param environment the environment, either 'sandbox' or 'live'.
     * @param returnValue a promise (returned by the JS bridge) fulfilled when the
     *                    initialization is done.
     */
    void initialize(String publicKey, String environment, Promise returnValue);

    /**
     * Generate a Checkout token for a credit card.
     * @param data an object matching this interface :
     *             {
     *             // FIXME doc
     *             }
     * @param returnValue a promise that emits the token if it succeed.
     */
    void generateToken(ReadableMap data, Promise returnValue);
}
