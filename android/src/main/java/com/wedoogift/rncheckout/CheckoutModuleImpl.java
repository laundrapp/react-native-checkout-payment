package com.wedoogift.rncheckout;

import android.util.Log;

import com.android.volley.VolleyError;
import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.Environment;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import javax.annotation.Nonnull;

/**
 * Impl of CheckoutModule for the React Native bridge.
 */
public class CheckoutModuleImpl extends ReactContextBaseJavaModule implements CheckoutModule {

    private CheckoutAPIClient checkoutClient;

    private CheckoutAPIClient.OnTokenGenerated tokenGeneratedListener;

    private Promise pendingTokenisationPromise;

    private final static String CARD_NUMBERS_KEY = "card";
    private final static String CARD_NAME = "name";
    private final static String CARD_EXPIRATION_MONTH = "expiryMonth";
    private final static String CARD_EXPIRATION_YEAR = "expiryYear";
    private final static String CARD_EXPIRATION_CVV = "cvv";
    private final static String POSTCODE = "postcode";

    CheckoutModuleImpl(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);

        tokenGeneratedListener = new CheckoutAPIClient.OnTokenGenerated() {
            @Override
            public void onTokenGenerated(CardTokenisationResponse response) {
                if (pendingTokenisationPromise == null) {
                    Log.w("CheckoutModuleImpl",
                            "Received CardTokenisationResponse while no tokenisation is pending.");
                    return;
                }
                pendingTokenisationPromise.resolve(CheckoutTools.convertResponseToMap(response));
                pendingTokenisationPromise = null;
            }

            @Override
            public void onError(CardTokenisationFail error) {
                if (pendingTokenisationPromise == null) {
                    Log.w("CheckoutModuleImpl",
                            "Received CardTokenisationFail while no tokenisation is pending.");
                    return;
                }
                if (error.getErrors() != null){
                    StringBuilder builder = new StringBuilder();
                    for(String s : error.getErrors()) {
                      builder.append("\n" + s);
                    }
                    String str = builder.toString();
                    pendingTokenisationPromise.reject("CHECKOUT" + error.getErrorCode() ,error.getMessage() + str);
                } else {
                    pendingTokenisationPromise.reject("CHECKOUT" + error.getErrorCode(),error.getMessage());
                }
                pendingTokenisationPromise = null;
            }

            @Override
            public void onNetworkError(VolleyError error) {
                if (pendingTokenisationPromise == null) {
                    Log.w("CheckoutModuleImpl",
                            "Received VolleyError (network) while no tokenisation is pending.");
                    return;
                }
                pendingTokenisationPromise.reject(error);
                pendingTokenisationPromise = null;
            }
        };
    }

    @Nonnull
    @Override
    public String getName() {
        return "CheckoutModule";
    }

    @Override
    @ReactMethod
    public void initialize(String publicKey, String environment, Promise returnValue) {
        Environment checkoutEnv = null;
        if ("live".equals(environment)) {
            checkoutEnv = Environment.LIVE;
        } else if ("sandbox".equals(environment)) {
            checkoutEnv = Environment.SANDBOX;
        }

        if (checkoutEnv == null) {
            returnValue.reject(
                    new IllegalArgumentException("environment must be 'live' or 'sandbox'."));
        }

        checkoutClient = new CheckoutAPIClient(
                this.getReactApplicationContext(),
                publicKey,
                checkoutEnv);
        checkoutClient.setTokenListener(tokenGeneratedListener);
        returnValue.resolve(null);
    }

    @Override
    @ReactMethod
    public void generateToken(ReadableMap data, Promise returnValue) {
        // Current implementation has a limit.
        // No multiple cards token can be generated in parallel.
        if (pendingTokenisationPromise != null) {
            returnValue.reject(new IllegalStateException(
                    "can't generate another token will one is currently being generated."));
        }

        if (!data.hasKey(CARD_NUMBERS_KEY) || !data.hasKey(CARD_EXPIRATION_CVV)
                || !data.hasKey(CARD_EXPIRATION_MONTH)
                || !data.hasKey(CARD_EXPIRATION_YEAR)) {
            returnValue.reject(new IllegalArgumentException(
                    "missing fields for the card token generation."));
        }

        pendingTokenisationPromise = returnValue;
        BillingModel billing = null;
        if (data.getString(POSTCODE) != null) {
            billing = new BillingModel("", "", data.getString(POSTCODE), "", "", "", null);
        }
        checkoutClient.generateToken(new CardTokenisationRequest(
        data.getString(CARD_NUMBERS_KEY),
        data.hasKey(CARD_NAME) ? data.getString(CARD_NAME) : null,
        data.getString(CARD_EXPIRATION_MONTH),
        data.getString(CARD_EXPIRATION_YEAR),
        data.getString(CARD_EXPIRATION_CVV),
        billing
        ));
    }
}
