import { element, by, ElementFinder } from 'protractor';

export default class QrCodeTablesUpdatePage {
  pageTitle: ElementFinder = element(by.id('yoplaApp.qrCodeTables.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tableNumberInput: ElementFinder = element(by.css('input#qr-code-tables-tableNumber'));
  qrCodeInput: ElementFinder = element(by.css('input#qr-code-tables-qrCode'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTableNumberInput(tableNumber) {
    await this.tableNumberInput.sendKeys(tableNumber);
  }

  async getTableNumberInput() {
    return this.tableNumberInput.getAttribute('value');
  }

  async setQrCodeInput(qrCode) {
    await this.qrCodeInput.sendKeys(qrCode);
  }

  async getQrCodeInput() {
    return this.qrCodeInput.getAttribute('value');
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
