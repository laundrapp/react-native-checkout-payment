# @wedoogift/react-native-checkout-payment

Provides a react-native implementation of the Checkout.com official 
Android and iOS SDK. This library uses both the native tools provided
by Checkout.com and regroup them in the same interface so that it can
be used seamlessly in React Native

## React Native version

The latest version currently supports React Native **0.60.x** on 
**Android** and **iOS**.

_It provides Typescript declaration file so it can be easily used with Typescript._

## Getting started

`$ yarn add @wedoogift/react-native-checkout-payment`

The linking is done automatically since React Native 0.60 (via Gradle or Pods).

## Usage

Before doing anything with the module, **you must 
initialize it** with your public key and the environment type:

```javascript
import { CheckoutModule } from '@wedoogift/react-native-checkout-payment';

CheckoutModule.initialize('ck_test_foobar', 'sandbox') 
    // or 'live' instead of 'sandbox' for production env.
    .then(() => {
        console.log('Initialization is done.');
    });
```

Then, you can start generating card tokens to make payments:
```javascript
import { CheckoutModule } from '@wedoogift/react-native-checkout-payment';

CheckoutModule.generateToken({
    card: '4242424242424242',
    name: 'Card Owner',
    expiryMonth: '06',
    expiryYear: '25',
    cvv: '100'
}) // or 'live' for production env.
    .then((result) => {
        console.log('Card token is ' + result.id);
        // See CardTokenisationResponse in index.ts or index.d.ts 
        // to see the data structure of the result.
    })
    .catch((error) => {
        console.warn('Failed because: ' + error.message);
    });
```

The resulting card token can be given to your own backend for use 
with the Checkout payment API.

Checkout documentation: https://docs.checkout.com/
