import { IUser } from 'app/shared/model/user.model';

export interface IOrders {
  id?: number;
  orderNumber?: number;
  orderStatus?: string;
  paymentStatus?: string;
  tableNumber?: string;
  customerName?: string;
  email?: string;
  phoneNumber?: string;
  address?: string;
  totalAmount?: number;
  user?: IUser;
}

export const defaultValue: Readonly<IOrders> = {};
