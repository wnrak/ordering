export interface IArticlesOptions {
  id?: number;
  name?: string;
  choice?: string;
  price?: number;
}

export const defaultValue: Readonly<IArticlesOptions> = {};
