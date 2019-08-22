import { NativeModules } from 'react-native';

export enum CheckoutEnvironment {
  SANDBOX = 'sandbox',
  LIVE = 'live'
}

export interface CardTokenisationRequest {
  card: string;
  name: string;
  expiryMonth: string; // With always two digits.
  expiryYear: string; // With always two digits.
  cvv: string;
}

export interface CardTokenisationResponse {
  id: string;
  liveMode: string;
  used: string;
  created: string;
  card: {
    bin: string;
    name: string;
    expiryMonth: string;
    expiryYear: string;
    paymentMethod: string;
    lastFour: string;
  };
}

export interface CheckoutModuleInterface {
  initialize(
    publicKey: string,
    environment: CheckoutEnvironment
  ): Promise<void>;

  generateToken(
    data: CardTokenisationRequest
  ): Promise<CardTokenisationResponse>;
}

export const CheckoutModule: CheckoutModuleInterface =
  NativeModules.CheckoutModule;
