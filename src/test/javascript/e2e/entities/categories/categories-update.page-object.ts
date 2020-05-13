import { element, by, ElementFinder } from 'protractor';

export default class CategoriesUpdatePage {
  pageTitle: ElementFinder = element(by.id('yoplaApp.categories.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  categoryNameInput: ElementFinder = element(by.css('input#categories-categoryName'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCategoryNameInput(categoryName) {
    await this.categoryNameInput.sendKeys(categoryName);
  }

  async getCategoryNameInput() {
    return this.categoryNameInput.getAttribute('value');
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
