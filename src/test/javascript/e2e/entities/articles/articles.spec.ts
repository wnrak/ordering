import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ArticlesComponentsPage, { ArticlesDeleteDialog } from './articles.page-object';
import ArticlesUpdatePage from './articles-update.page-object';
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

describe('Articles e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let articlesComponentsPage: ArticlesComponentsPage;
  let articlesUpdatePage: ArticlesUpdatePage;
  let articlesDeleteDialog: ArticlesDeleteDialog;
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

  it('should load Articles', async () => {
    await navBarPage.getEntityPage('articles');
    articlesComponentsPage = new ArticlesComponentsPage();
    expect(await articlesComponentsPage.title.getText()).to.match(/Articles/);

    expect(await articlesComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([articlesComponentsPage.noRecords, articlesComponentsPage.table]);

    beforeRecordsCount = (await isVisible(articlesComponentsPage.noRecords)) ? 0 : await getRecordsCount(articlesComponentsPage.table);
  });

  it('should load create Articles page', async () => {
    await articlesComponentsPage.createButton.click();
    articlesUpdatePage = new ArticlesUpdatePage();
    expect(await articlesUpdatePage.getPageTitle().getAttribute('id')).to.match(/yoplaApp.articles.home.createOrEditLabel/);
    await articlesUpdatePage.cancel();
  });

  it('should create and save Articles', async () => {
    await articlesComponentsPage.createButton.click();
    await articlesUpdatePage.setArticleNameInput('articleName');
    expect(await articlesUpdatePage.getArticleNameInput()).to.match(/articleName/);
    await articlesUpdatePage.setPriceInput('5');
    expect(await articlesUpdatePage.getPriceInput()).to.eq('5');
    await articlesUpdatePage.setTaxRateIfPickUpInput('5');
    expect(await articlesUpdatePage.getTaxRateIfPickUpInput()).to.eq('5');
    await articlesUpdatePage.setTaxRateIfDineInInput('5');
    expect(await articlesUpdatePage.getTaxRateIfDineInInput()).to.eq('5');
    await articlesUpdatePage.setInformationInput('information');
    expect(await articlesUpdatePage.getInformationInput()).to.match(/information/);
    await articlesUpdatePage.setIngredientInput('ingredient');
    expect(await articlesUpdatePage.getIngredientInput()).to.match(/ingredient/);
    await articlesUpdatePage.setImageInput('image');
    expect(await articlesUpdatePage.getImageInput()).to.match(/image/);
    await waitUntilDisplayed(articlesUpdatePage.saveButton);
    await articlesUpdatePage.save();
    await waitUntilHidden(articlesUpdatePage.saveButton);
    expect(await isVisible(articlesUpdatePage.saveButton)).to.be.false;

    expect(await articlesComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(articlesComponentsPage.table);

    await waitUntilCount(articlesComponentsPage.records, beforeRecordsCount + 1);
    expect(await articlesComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last Articles', async () => {
    const deleteButton = articlesComponentsPage.getDeleteButton(articlesComponentsPage.records.last());
    await click(deleteButton);

    articlesDeleteDialog = new ArticlesDeleteDialog();
    await waitUntilDisplayed(articlesDeleteDialog.deleteModal);
    expect(await articlesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/yoplaApp.articles.delete.question/);
    await articlesDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(articlesDeleteDialog.deleteModal);

    expect(await isVisible(articlesDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([articlesComponentsPage.noRecords, articlesComponentsPage.table]);

    const afterCount = (await isVisible(articlesComponentsPage.noRecords)) ? 0 : await getRecordsCount(articlesComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
