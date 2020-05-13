import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CategoriesComponentsPage, { CategoriesDeleteDialog } from './categories.page-object';
import CategoriesUpdatePage from './categories-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible
} from '../../util/utils';

const expect = chai.expect;

describe('Categories e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let categoriesComponentsPage: CategoriesComponentsPage;
  let categoriesUpdatePage: CategoriesUpdatePage;
  let categoriesDeleteDialog: CategoriesDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load Categories', async () => {
    await navBarPage.getEntityPage('categories');
    categoriesComponentsPage = new CategoriesComponentsPage();
    expect(await categoriesComponentsPage.title.getText()).to.match(/Categories/);

    expect(await categoriesComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([categoriesComponentsPage.noRecords, categoriesComponentsPage.table]);

    beforeRecordsCount = (await isVisible(categoriesComponentsPage.noRecords)) ? 0 : await getRecordsCount(categoriesComponentsPage.table);
  });

  it('should load create Categories page', async () => {
    await categoriesComponentsPage.createButton.click();
    categoriesUpdatePage = new CategoriesUpdatePage();
    expect(await categoriesUpdatePage.getPageTitle().getAttribute('id')).to.match(/yoplaApp.categories.home.createOrEditLabel/);
    await categoriesUpdatePage.cancel();
  });

  it('should create and save Categories', async () => {
    await categoriesComponentsPage.createButton.click();
    await categoriesUpdatePage.setCategoryNameInput('categoryName');
    expect(await categoriesUpdatePage.getCategoryNameInput()).to.match(/categoryName/);
    await waitUntilDisplayed(categoriesUpdatePage.saveButton);
    await categoriesUpdatePage.save();
    await waitUntilHidden(categoriesUpdatePage.saveButton);
    expect(await isVisible(categoriesUpdatePage.saveButton)).to.be.false;

    expect(await categoriesComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(categoriesComponentsPage.table);

    await waitUntilCount(categoriesComponentsPage.records, beforeRecordsCount + 1);
    expect(await categoriesComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last Categories', async () => {
    const deleteButton = categoriesComponentsPage.getDeleteButton(categoriesComponentsPage.records.last());
    await click(deleteButton);

    categoriesDeleteDialog = new CategoriesDeleteDialog();
    await waitUntilDisplayed(categoriesDeleteDialog.deleteModal);
    expect(await categoriesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/yoplaApp.categories.delete.question/);
    await categoriesDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(categoriesDeleteDialog.deleteModal);

    expect(await isVisible(categoriesDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([categoriesComponentsPage.noRecords, categoriesComponentsPage.table]);

    const afterCount = (await isVisible(categoriesComponentsPage.noRecords)) ? 0 : await getRecordsCount(categoriesComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
