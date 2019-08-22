const path = require('path');

module.exports = {
    dependency: {
        platforms: {
            ios: { podspecPath: path.join(__dirname, 'react-native-checkout-payment.podspec') },
            android: {
                packageImportPath: 'import com.wedoogift.rncheckout.CheckoutPackage;',
                packageInstance: 'new CheckoutPackage()',
            },
        },
    },
};
