export interface IRestaurant {
  id?: number;
  restaurantName?: string;
  location?: string;
  banner?: string;
  logo?: string;
  numberOfTables?: number;
  availability?: boolean;
  apiKey?: string;
  payLater?: boolean;
  askForService?: boolean;
  enableSms?: boolean;
  slug?: string;
}

export const defaultValue: Readonly<IRestaurant> = {
  availability: false,
  payLater: false,
  askForService: false,
  enableSms: false
};
