/*
 * File making the bridge between Obj-C and Swift as React Native only recognize Obj-C.
 */

#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(CheckoutModule, NSObject)

RCT_EXTERN_METHOD(initialize:(NSString *)publicKey environment:(NSString *)environment
                  resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(generateToken:(NSDictionary *)data resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end
