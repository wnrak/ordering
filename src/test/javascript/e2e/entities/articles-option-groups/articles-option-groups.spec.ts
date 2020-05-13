import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ArticlesOptionGroupsComponentsPage, { ArticlesOptionGroupsDeleteDialog } from './articles-option-groups.page-object';
import ArticlesOptionGroupsUpdatePage from './articles-option-groups-update.page-object';
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

describe('ArticlesOptionGroups e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let articlesOptionGroupsComponentsPage: ArticlesOptionGroupsComponentsPage;
  let articlesOptionGroupsUpdatePage: ArticlesOptionGroupsUpdatePage;
  let articlesOptionGroupsDeleteDialog: ArticlesOptionGroupsDeleteDialog;
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

  it('should load ArticlesOptionGroups', async () => {
    await navBarPage.getEntityPage('articles-option-groups');
    articlesOptionGroupsComponentsPage = new ArticlesOptionGroupsComponentsPage();
    expect(await articlesOptionGroupsComponentsPage.title.getText()).to.match(/Articles Option Groups/);

    expect(await articlesOptionGroupsComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([articlesOptionGroupsComponentsPage.noRecords, articlesOptionGroupsComponentsPage.table]);

    beforeRecordsCount = (await isVisible(articlesOptionGroupsComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(articlesOptionGroupsComponentsPage.table);
  });

  it('should load create ArticlesOptionGroups page', async () => {
    await articlesOptionGroupsComponentsPage.createButton.click();
    articlesOptionGroupsUpdatePage = new ArticlesOptionGroupsUpdatePage();
    expect(await articlesOptionGroupsUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /yoplaApp.articlesOptionGroups.home.createOrEditLabel/
    );
    await articlesOptionGroupsUpdatePage.cancel();
  });

  it('should create and save ArticlesOptionGroups', async () => {
    await articlesOptionGroupsComponentsPage.createButton.click();
    await articlesOptionGroupsUpdatePage.setOptionNameInput('optionName');
    expect(await articlesOptionGroupsUpdatePage.getOptionNameInput()).to.match(/optionName/);
    await articlesOptionGroupsUpdatePage.setActiveOptionNameInput('activeOptionName');
    expect(await articlesOptionGroupsUpdatePage.getActiveOptionNameInput()).to.match(/activeOptionName/);
    await articlesOptionGroupsUpdatePage.setMinValueInput('5');
    expect(await articlesOptionGroupsUpdatePage.getMinValueInput()).to.eq('5');
    await articlesOptionGroupsUpdatePage.setMaxValueInput('5');
    expect(await articlesOptionGroupsUpdatePage.getMaxValueInput()).to.eq('5');
    await waitUntilDisplayed(articlesOptionGroupsUpdatePage.saveButton);
    await articlesOptionGroupsUpdatePage.save();
    await waitUntilHidden(articlesOptionGroupsUpdatePage.saveButton);
    expect(await isVisible(articlesOptionGroupsUpdatePage.saveButton)).to.be.false;

    expect(await articlesOptionGroupsComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(articlesOptionGroupsComponentsPage.table);

    await waitUntilCount(articlesOptionGroupsComponentsPage.records, beforeRecordsCount + 1);
    expect(await articlesOptionGroupsComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last ArticlesOptionGroups', async () => {
    const deleteButton = articlesOptionGroupsComponentsPage.getDeleteButton(articlesOptionGroupsComponentsPage.records.last());
    await click(deleteButton);

    articlesOptionGroupsDeleteDialog = new ArticlesOptionGroupsDeleteDialog();
    await waitUntilDisplayed(articlesOptionGroupsDeleteDialog.deleteModal);
    expect(await articlesOptionGroupsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /yoplaApp.articlesOptionGroups.delete.question/
    );
    await articlesOptionGroupsDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(articlesOptionGroupsDeleteDialog.deleteModal);

    expect(await isVisible(articlesOptionGroupsDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([articlesOptionGroupsComponentsPage.noRecords, articlesOptionGroupsComponentsPage.table]);

    const afterCount = (await isVisible(articlesOptionGroupsComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(articlesOptionGroupsComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
