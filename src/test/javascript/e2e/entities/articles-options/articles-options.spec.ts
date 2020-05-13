import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ArticlesOptionsComponentsPage, { ArticlesOptionsDeleteDialog } from './articles-options.page-object';
import ArticlesOptionsUpdatePage from './articles-options-update.page-object';
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

describe('ArticlesOptions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let articlesOptionsComponentsPage: ArticlesOptionsComponentsPage;
  let articlesOptionsUpdatePage: ArticlesOptionsUpdatePage;
  let articlesOptionsDeleteDialog: ArticlesOptionsDeleteDialog;
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

  it('should load ArticlesOptions', async () => {
    await navBarPage.getEntityPage('articles-options');
    articlesOptionsComponentsPage = new ArticlesOptionsComponentsPage();
    expect(await articlesOptionsComponentsPage.title.getText()).to.match(/Articles Options/);

    expect(await articlesOptionsComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([articlesOptionsComponentsPage.noRecords, articlesOptionsComponentsPage.table]);

    beforeRecordsCount = (await isVisible(articlesOptionsComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(articlesOptionsComponentsPage.table);
  });

  it('should load create ArticlesOptions page', async () => {
    await articlesOptionsComponentsPage.createButton.click();
    articlesOptionsUpdatePage = new ArticlesOptionsUpdatePage();
    expect(await articlesOptionsUpdatePage.getPageTitle().getAttribute('id')).to.match(/yoplaApp.articlesOptions.home.createOrEditLabel/);
    await articlesOptionsUpdatePage.cancel();
  });

  it('should create and save ArticlesOptions', async () => {
    await articlesOptionsComponentsPage.createButton.click();
    await articlesOptionsUpdatePage.setNameInput('name');
    expect(await articlesOptionsUpdatePage.getNameInput()).to.match(/name/);
    await articlesOptionsUpdatePage.setChoiceInput('choice');
    expect(await articlesOptionsUpdatePage.getChoiceInput()).to.match(/choice/);
    await articlesOptionsUpdatePage.setPriceInput('5');
    expect(await articlesOptionsUpdatePage.getPriceInput()).to.eq('5');
    await waitUntilDisplayed(articlesOptionsUpdatePage.saveButton);
    await articlesOptionsUpdatePage.save();
    await waitUntilHidden(articlesOptionsUpdatePage.saveButton);
    expect(await isVisible(articlesOptionsUpdatePage.saveButton)).to.be.false;

    expect(await articlesOptionsComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(articlesOptionsComponentsPage.table);

    await waitUntilCount(articlesOptionsComponentsPage.records, beforeRecordsCount + 1);
    expect(await articlesOptionsComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last ArticlesOptions', async () => {
    const deleteButton = articlesOptionsComponentsPage.getDeleteButton(articlesOptionsComponentsPage.records.last());
    await click(deleteButton);

    articlesOptionsDeleteDialog = new ArticlesOptionsDeleteDialog();
    await waitUntilDisplayed(articlesOptionsDeleteDialog.deleteModal);
    expect(await articlesOptionsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/yoplaApp.articlesOptions.delete.question/);
    await articlesOptionsDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(articlesOptionsDeleteDialog.deleteModal);

    expect(await isVisible(articlesOptionsDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([articlesOptionsComponentsPage.noRecords, articlesOptionsComponentsPage.table]);

    const afterCount = (await isVisible(articlesOptionsComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(articlesOptionsComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
