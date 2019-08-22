# react-native-checkout-payment

## Getting started

`$ npm install react-native-checkout-payment --save`

### Mostly automatic installation

`$ react-native link react-native-checkout-payment`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-checkout-payment` and add `CheckoutPayment.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libCheckoutPayment.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.wedoogift.rncheckout.CheckoutPaymentPackage;` to the imports at the top of the file
  - Add `new CheckoutPaymentPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-checkout-payment'
  	project(':react-native-checkout-payment').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-checkout-payment/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-checkout-payment')
  	```


## Usage
```javascript
import CheckoutPayment from 'react-native-checkout-payment';

// TODO: What to do with the module?
CheckoutPayment;
```
