import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import restaurant, {
  RestaurantState
} from 'app/entities/restaurant/restaurant.reducer';
// prettier-ignore
import categories, {
  CategoriesState
} from 'app/entities/categories/categories.reducer';
// prettier-ignore
import articlesOptionGroups, {
  ArticlesOptionGroupsState
} from 'app/entities/articles-option-groups/articles-option-groups.reducer';
// prettier-ignore
import articles, {
  ArticlesState
} from 'app/entities/articles/articles.reducer';
// prettier-ignore
import articlesOptions, {
  ArticlesOptionsState
} from 'app/entities/articles-options/articles-options.reducer';
// prettier-ignore
import qrCodeTables, {
  QrCodeTablesState
} from 'app/entities/qr-code-tables/qr-code-tables.reducer';
// prettier-ignore
import orders, {
  OrdersState
} from 'app/entities/orders/orders.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly restaurant: RestaurantState;
  readonly categories: CategoriesState;
  readonly articlesOptionGroups: ArticlesOptionGroupsState;
  readonly articles: ArticlesState;
  readonly articlesOptions: ArticlesOptionsState;
  readonly qrCodeTables: QrCodeTablesState;
  readonly orders: OrdersState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  restaurant,
  categories,
  articlesOptionGroups,
  articles,
  articlesOptions,
  qrCodeTables,
  orders,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
