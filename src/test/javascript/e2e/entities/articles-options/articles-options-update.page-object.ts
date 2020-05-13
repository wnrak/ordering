import { element, by, ElementFinder } from 'protractor';

export default class ArticlesOptionsUpdatePage {
  pageTitle: ElementFinder = element(by.id('yoplaApp.articlesOptions.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#articles-options-name'));
  choiceInput: ElementFinder = element(by.css('input#articles-options-choice'));
  priceInput: ElementFinder = element(by.css('input#articles-options-price'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setChoiceInput(choice) {
    await this.choiceInput.sendKeys(choice);
  }

  async getChoiceInput() {
    return this.choiceInput.getAttribute('value');
  }

  async setPriceInput(price) {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput() {
    return this.priceInput.getAttribute('value');
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
