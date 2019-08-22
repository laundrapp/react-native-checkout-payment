package com.wedoogift.rncheckout;

import com.checkout.android_sdk.Models.CardModel;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

class CheckoutTools {
    /**
     * Convert a CardTokenisationResponse to a WritableMap that can be used in the React
     * Native bridge.
     * @param response the Checkout response.
     * @return the writable map.
     */
    static WritableMap convertResponseToMap(CardTokenisationResponse response) {
        WritableMap result = Arguments.createMap();
        result.putString("id", response.getId());
        result.putString("created", response.getCreated());
        result.putString("used", response.getUsed());
        result.putString("liveMode", response.getLiveMode());

        if (response.getCard() != null) {
            CardModel cardModel = response.getCard();

            WritableMap cardModelMap = Arguments.createMap();
            cardModelMap.putString("bin", cardModel.getBin());
            cardModelMap.putString("name", cardModel.getName());
            cardModelMap.putString("expiryMonth", cardModel.getExpiryMonth());
            cardModelMap.putString("expiryYear", cardModel.getExpiryYear());
            cardModelMap.putString("paymentMethod", cardModel.getPaymentMethod());
            cardModelMap.putString("lastFour", cardModel.getLast4());

            result.putMap("card", cardModelMap);
        } else {
            result.putNull("card");
        }

        return result;
    }
}
