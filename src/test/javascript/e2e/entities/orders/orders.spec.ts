import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import OrdersComponentsPage, { OrdersDeleteDialog } from './orders.page-object';
import OrdersUpdatePage from './orders-update.page-object';
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

describe('Orders e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ordersComponentsPage: OrdersComponentsPage;
  let ordersUpdatePage: OrdersUpdatePage;
  let ordersDeleteDialog: OrdersDeleteDialog;
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

  it('should load Orders', async () => {
    await navBarPage.getEntityPage('orders');
    ordersComponentsPage = new OrdersComponentsPage();
    expect(await ordersComponentsPage.title.getText()).to.match(/Orders/);

    expect(await ordersComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([ordersComponentsPage.noRecords, ordersComponentsPage.table]);

    beforeRecordsCount = (await isVisible(ordersComponentsPage.noRecords)) ? 0 : await getRecordsCount(ordersComponentsPage.table);
  });

  it('should load create Orders page', async () => {
    await ordersComponentsPage.createButton.click();
    ordersUpdatePage = new OrdersUpdatePage();
    expect(await ordersUpdatePage.getPageTitle().getAttribute('id')).to.match(/yoplaApp.orders.home.createOrEditLabel/);
    await ordersUpdatePage.cancel();
  });

  it('should create and save Orders', async () => {
    await ordersComponentsPage.createButton.click();
    await ordersUpdatePage.setOrderNumberInput('5');
    expect(await ordersUpdatePage.getOrderNumberInput()).to.eq('5');
    await ordersUpdatePage.setOrderStatusInput('orderStatus');
    expect(await ordersUpdatePage.getOrderStatusInput()).to.match(/orderStatus/);
    await ordersUpdatePage.setPaymentStatusInput('paymentStatus');
    expect(await ordersUpdatePage.getPaymentStatusInput()).to.match(/paymentStatus/);
    await ordersUpdatePage.setTableNumberInput('tableNumber');
    expect(await ordersUpdatePage.getTableNumberInput()).to.match(/tableNumber/);
    await ordersUpdatePage.setCustomerNameInput('customerName');
    expect(await ordersUpdatePage.getCustomerNameInput()).to.match(/customerName/);
    await ordersUpdatePage.setEmailInput('email');
    expect(await ordersUpdatePage.getEmailInput()).to.match(/email/);
    await ordersUpdatePage.setPhoneNumberInput('phoneNumber');
    expect(await ordersUpdatePage.getPhoneNumberInput()).to.match(/phoneNumber/);
    await ordersUpdatePage.setAddressInput('address');
    expect(await ordersUpdatePage.getAddressInput()).to.match(/address/);
    await ordersUpdatePage.setTotalAmountInput('5');
    expect(await ordersUpdatePage.getTotalAmountInput()).to.eq('5');
    await ordersUpdatePage.userSelectLastOption();
    await waitUntilDisplayed(ordersUpdatePage.saveButton);
    await ordersUpdatePage.save();
    await waitUntilHidden(ordersUpdatePage.saveButton);
    expect(await isVisible(ordersUpdatePage.saveButton)).to.be.false;

    expect(await ordersComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(ordersComponentsPage.table);

    await waitUntilCount(ordersComponentsPage.records, beforeRecordsCount + 1);
    expect(await ordersComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last Orders', async () => {
    const deleteButton = ordersComponentsPage.getDeleteButton(ordersComponentsPage.records.last());
    await click(deleteButton);

    ordersDeleteDialog = new OrdersDeleteDialog();
    await waitUntilDisplayed(ordersDeleteDialog.deleteModal);
    expect(await ordersDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/yoplaApp.orders.delete.question/);
    await ordersDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(ordersDeleteDialog.deleteModal);

    expect(await isVisible(ordersDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([ordersComponentsPage.noRecords, ordersComponentsPage.table]);

    const afterCount = (await isVisible(ordersComponentsPage.noRecords)) ? 0 : await getRecordsCount(ordersComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
