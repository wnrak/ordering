import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import QrCodeTablesComponentsPage, { QrCodeTablesDeleteDialog } from './qr-code-tables.page-object';
import QrCodeTablesUpdatePage from './qr-code-tables-update.page-object';
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

describe('QrCodeTables e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let qrCodeTablesComponentsPage: QrCodeTablesComponentsPage;
  let qrCodeTablesUpdatePage: QrCodeTablesUpdatePage;
  let qrCodeTablesDeleteDialog: QrCodeTablesDeleteDialog;
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

  it('should load QrCodeTables', async () => {
    await navBarPage.getEntityPage('qr-code-tables');
    qrCodeTablesComponentsPage = new QrCodeTablesComponentsPage();
    expect(await qrCodeTablesComponentsPage.title.getText()).to.match(/Qr Code Tables/);

    expect(await qrCodeTablesComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([qrCodeTablesComponentsPage.noRecords, qrCodeTablesComponentsPage.table]);

    beforeRecordsCount = (await isVisible(qrCodeTablesComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(qrCodeTablesComponentsPage.table);
  });

  it('should load create QrCodeTables page', async () => {
    await qrCodeTablesComponentsPage.createButton.click();
    qrCodeTablesUpdatePage = new QrCodeTablesUpdatePage();
    expect(await qrCodeTablesUpdatePage.getPageTitle().getAttribute('id')).to.match(/yoplaApp.qrCodeTables.home.createOrEditLabel/);
    await qrCodeTablesUpdatePage.cancel();
  });

  it('should create and save QrCodeTables', async () => {
    await qrCodeTablesComponentsPage.createButton.click();
    await qrCodeTablesUpdatePage.setTableNumberInput('tableNumber');
    expect(await qrCodeTablesUpdatePage.getTableNumberInput()).to.match(/tableNumber/);
    await qrCodeTablesUpdatePage.setQrCodeInput('qrCode');
    expect(await qrCodeTablesUpdatePage.getQrCodeInput()).to.match(/qrCode/);
    await waitUntilDisplayed(qrCodeTablesUpdatePage.saveButton);
    await qrCodeTablesUpdatePage.save();
    await waitUntilHidden(qrCodeTablesUpdatePage.saveButton);
    expect(await isVisible(qrCodeTablesUpdatePage.saveButton)).to.be.false;

    expect(await qrCodeTablesComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(qrCodeTablesComponentsPage.table);

    await waitUntilCount(qrCodeTablesComponentsPage.records, beforeRecordsCount + 1);
    expect(await qrCodeTablesComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last QrCodeTables', async () => {
    const deleteButton = qrCodeTablesComponentsPage.getDeleteButton(qrCodeTablesComponentsPage.records.last());
    await click(deleteButton);

    qrCodeTablesDeleteDialog = new QrCodeTablesDeleteDialog();
    await waitUntilDisplayed(qrCodeTablesDeleteDialog.deleteModal);
    expect(await qrCodeTablesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/yoplaApp.qrCodeTables.delete.question/);
    await qrCodeTablesDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(qrCodeTablesDeleteDialog.deleteModal);

    expect(await isVisible(qrCodeTablesDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([qrCodeTablesComponentsPage.noRecords, qrCodeTablesComponentsPage.table]);

    const afterCount = (await isVisible(qrCodeTablesComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(qrCodeTablesComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
