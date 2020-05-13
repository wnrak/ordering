export interface IArticles {
  id?: number;
  articleName?: string;
  price?: number;
  taxRateIfPickUp?: number;
  taxRateIfDineIn?: number;
  information?: string;
  ingredient?: string;
  image?: string;
}

export const defaultValue: Readonly<IArticles> = {};
