export interface IArticlesOptionGroups {
  id?: number;
  optionName?: string;
  activeOptionName?: string;
  minValue?: number;
  maxValue?: number;
}

export const defaultValue: Readonly<IArticlesOptionGroups> = {};
