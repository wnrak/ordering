import { element, by, ElementFinder } from 'protractor';

export default class ArticlesOptionGroupsUpdatePage {
  pageTitle: ElementFinder = element(by.id('yoplaApp.articlesOptionGroups.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  optionNameInput: ElementFinder = element(by.css('input#articles-option-groups-optionName'));
  activeOptionNameInput: ElementFinder = element(by.css('input#articles-option-groups-activeOptionName'));
  minValueInput: ElementFinder = element(by.css('input#articles-option-groups-minValue'));
  maxValueInput: ElementFinder = element(by.css('input#articles-option-groups-maxValue'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setOptionNameInput(optionName) {
    await this.optionNameInput.sendKeys(optionName);
  }

  async getOptionNameInput() {
    return this.optionNameInput.getAttribute('value');
  }

  async setActiveOptionNameInput(activeOptionName) {
    await this.activeOptionNameInput.sendKeys(activeOptionName);
  }

  async getActiveOptionNameInput() {
    return this.activeOptionNameInput.getAttribute('value');
  }

  async setMinValueInput(minValue) {
    await this.minValueInput.sendKeys(minValue);
  }

  async getMinValueInput() {
    return this.minValueInput.getAttribute('value');
  }

  async setMaxValueInput(maxValue) {
    await this.maxValueInput.sendKeys(maxValue);
  }

  async getMaxValueInput() {
    return this.maxValueInput.getAttribute('value');
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
