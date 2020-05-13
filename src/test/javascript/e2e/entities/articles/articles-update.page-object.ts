import { element, by, ElementFinder } from 'protractor';

export default class ArticlesUpdatePage {
  pageTitle: ElementFinder = element(by.id('yoplaApp.articles.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  articleNameInput: ElementFinder = element(by.css('input#articles-articleName'));
  priceInput: ElementFinder = element(by.css('input#articles-price'));
  taxRateIfPickUpInput: ElementFinder = element(by.css('input#articles-taxRateIfPickUp'));
  taxRateIfDineInInput: ElementFinder = element(by.css('input#articles-taxRateIfDineIn'));
  informationInput: ElementFinder = element(by.css('input#articles-information'));
  ingredientInput: ElementFinder = element(by.css('input#articles-ingredient'));
  imageInput: ElementFinder = element(by.css('input#articles-image'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setArticleNameInput(articleName) {
    await this.articleNameInput.sendKeys(articleName);
  }

  async getArticleNameInput() {
    return this.articleNameInput.getAttribute('value');
  }

  async setPriceInput(price) {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput() {
    return this.priceInput.getAttribute('value');
  }

  async setTaxRateIfPickUpInput(taxRateIfPickUp) {
    await this.taxRateIfPickUpInput.sendKeys(taxRateIfPickUp);
  }

  async getTaxRateIfPickUpInput() {
    return this.taxRateIfPickUpInput.getAttribute('value');
  }

  async setTaxRateIfDineInInput(taxRateIfDineIn) {
    await this.taxRateIfDineInInput.sendKeys(taxRateIfDineIn);
  }

  async getTaxRateIfDineInInput() {
    return this.taxRateIfDineInInput.getAttribute('value');
  }

  async setInformationInput(information) {
    await this.informationInput.sendKeys(information);
  }

  async getInformationInput() {
    return this.informationInput.getAttribute('value');
  }

  async setIngredientInput(ingredient) {
    await this.ingredientInput.sendKeys(ingredient);
  }

  async getIngredientInput() {
    return this.ingredientInput.getAttribute('value');
  }

  async setImageInput(image) {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput() {
    return this.imageInput.getAttribute('value');
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
