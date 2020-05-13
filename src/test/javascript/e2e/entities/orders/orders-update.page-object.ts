import { element, by, ElementFinder } from 'protractor';

export default class OrdersUpdatePage {
  pageTitle: ElementFinder = element(by.id('yoplaApp.orders.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  orderNumberInput: ElementFinder = element(by.css('input#orders-orderNumber'));
  orderStatusInput: ElementFinder = element(by.css('input#orders-orderStatus'));
  paymentStatusInput: ElementFinder = element(by.css('input#orders-paymentStatus'));
  tableNumberInput: ElementFinder = element(by.css('input#orders-tableNumber'));
  customerNameInput: ElementFinder = element(by.css('input#orders-customerName'));
  emailInput: ElementFinder = element(by.css('input#orders-email'));
  phoneNumberInput: ElementFinder = element(by.css('input#orders-phoneNumber'));
  addressInput: ElementFinder = element(by.css('input#orders-address'));
  totalAmountInput: ElementFinder = element(by.css('input#orders-totalAmount'));
  userSelect: ElementFinder = element(by.css('select#orders-user'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setOrderNumberInput(orderNumber) {
    await this.orderNumberInput.sendKeys(orderNumber);
  }

  async getOrderNumberInput() {
    return this.orderNumberInput.getAttribute('value');
  }

  async setOrderStatusInput(orderStatus) {
    await this.orderStatusInput.sendKeys(orderStatus);
  }

  async getOrderStatusInput() {
    return this.orderStatusInput.getAttribute('value');
  }

  async setPaymentStatusInput(paymentStatus) {
    await this.paymentStatusInput.sendKeys(paymentStatus);
  }

  async getPaymentStatusInput() {
    return this.paymentStatusInput.getAttribute('value');
  }

  async setTableNumberInput(tableNumber) {
    await this.tableNumberInput.sendKeys(tableNumber);
  }

  async getTableNumberInput() {
    return this.tableNumberInput.getAttribute('value');
  }

  async setCustomerNameInput(customerName) {
    await this.customerNameInput.sendKeys(customerName);
  }

  async getCustomerNameInput() {
    return this.customerNameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return this.emailInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return this.phoneNumberInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setTotalAmountInput(totalAmount) {
    await this.totalAmountInput.sendKeys(totalAmount);
  }

  async getTotalAmountInput() {
    return this.totalAmountInput.getAttribute('value');
  }

  async userSelectLastOption() {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect() {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return this.userSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
