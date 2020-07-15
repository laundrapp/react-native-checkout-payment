//
//  CheckoutModule.swift
//  wedoogift
//
//  Created by Victor Levasseur on 21/08/2019.
//  Copyright © 2019 Wedoogift. All rights reserved.
//

import Foundation
import Frames

@objc(CheckoutModule)
class CheckoutModule: NSObject {
    
    var checkoutApi: CheckoutAPIClient? = nil
    
    /**
     * Initialize the CheckoutModule.
     */
    @objc(initialize:environment:resolve:reject:)
    func initialize(publicKey: String, environment: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) -> Void {
        // Convert the environment
        var checkoutEnvironment: Environment? = nil
        if (environment == "sandbox") {
            checkoutEnvironment = .sandbox
        } else if (environment == "live") {
            checkoutEnvironment = .live
        }
        
        // If bad environment name, reject.
        if (checkoutEnvironment == nil) {
            reject("CHECKOUT_ILLEGAL_ARG", "environment must be 'sandbox' or 'live'.", nil)
            return
        }
        
        checkoutApi = CheckoutAPIClient(publicKey: publicKey, environment: checkoutEnvironment!)
        resolve(nil)
    }
    
    /**
     * Generate a token for the given card.
     */
    @objc(generateToken:resolve:reject:)
    func generateToken(data: NSDictionary, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
        if (checkoutApi != nil) {
            let card: String? = data.value(forKey: "card") as? String;
            let name: String? = data.value(forKey: "name") as? String;
            let expiryMonth: String? = data.value(forKey: "expiryMonth") as? String;
            let expiryYear: String? = data.value(forKey: "expiryYear") as? String;
            let cvv: String? = data.value(forKey: "cvv") as? String;
            
            var billingDetails: CkoAddress?
            if let postcode = data.value(forKey: "postcode") as? String{
                billingDetails = CkoAddress(postcode: postcode)
            }
            
            if let card = card,
                let expiryMonth = expiryMonth,
                let expiryYear = expiryYear,
                let cvv = cvv {
                checkoutApi!.createCardToken(
                    card: CkoCardTokenRequest(number: card, expiryMonth: expiryMonth, expiryYear: expiryYear, cvv: cvv, name: name, billingDetails: billingDetails),
                    successHandler: {(response) in
                        resolve([
                            "id": response.id,
                            "created": response.created,
                            "used": response.used,
                            "liveMode": response.liveMode,
                            "card": [
                                "bin": response.card.bin,
                                "expiryMonth": response.card.expiryMonth,
                                "expiryYear": response.card.expiryYear,
                                "paymentMethod": response.card.paymentMethod,
                                "lastFour": response.card.last4,
                                "name": response.card.name
                            ]
                            ])
                },
                    errorHandler: {(errorResponse) in
                        let errors = errorResponse.errors?.joined(separator: ",") ?? ""
                        reject("CHECKOUT" + errorResponse.errorCode, errorResponse.message + " [\(errors)]", nil)
                }
                )
            } else {
                reject("CHECKOUT_ILLEGAL_ARG", "generateToken missing required info.", nil)
            }
        } else {
            reject("CHECKOUT_UNINITIALIZED", "CheckoutModule must be initialized before calling generateToken.", nil);
        }
    }
    
}
