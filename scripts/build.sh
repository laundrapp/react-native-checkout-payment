#!/usr/bin/env bash
cd ..

echo Compiling Typescript...
tsc
cp package.json build/package.json

echo Copying files...
cp react-native.config.js build/react-native.config.js
cp README.md build/README.md
cp LICENSE build/LICENSE
cp react-native-checkout-payment.podspec build/react-native-checkout-payment.podspec
cp -R ios build/ios
cp -R android build/android
