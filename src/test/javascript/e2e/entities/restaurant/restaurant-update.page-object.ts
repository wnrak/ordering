import { element, by, ElementFinder } from 'protractor';

export default class RestaurantUpdatePage {
  pageTitle: ElementFinder = element(by.id('yoplaApp.restaurant.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  restaurantNameInput: ElementFinder = element(by.css('input#restaurant-restaurantName'));
  locationInput: ElementFinder = element(by.css('input#restaurant-location'));
  bannerInput: ElementFinder = element(by.css('input#restaurant-banner'));
  logoInput: ElementFinder = element(by.css('input#restaurant-logo'));
  numberOfTablesInput: ElementFinder = element(by.css('input#restaurant-numberOfTables'));
  availabilityInput: ElementFinder = element(by.css('input#restaurant-availability'));
  apiKeyInput: ElementFinder = element(by.css('input#restaurant-apiKey'));
  payLaterInput: ElementFinder = element(by.css('input#restaurant-payLater'));
  askForServiceInput: ElementFinder = element(by.css('input#restaurant-askForService'));
  enableSmsInput: ElementFinder = element(by.css('input#restaurant-enableSms'));
  slugInput: ElementFinder = element(by.css('input#restaurant-slug'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setRestaurantNameInput(restaurantName) {
    await this.restaurantNameInput.sendKeys(restaurantName);
  }

  async getRestaurantNameInput() {
    return this.restaurantNameInput.getAttribute('value');
  }

  async setLocationInput(location) {
    await this.locationInput.sendKeys(location);
  }

  async getLocationInput() {
    return this.locationInput.getAttribute('value');
  }

  async setBannerInput(banner) {
    await this.bannerInput.sendKeys(banner);
  }

  async getBannerInput() {
    return this.bannerInput.getAttribute('value');
  }

  async setLogoInput(logo) {
    await this.logoInput.sendKeys(logo);
  }

  async getLogoInput() {
    return this.logoInput.getAttribute('value');
  }

  async setNumberOfTablesInput(numberOfTables) {
    await this.numberOfTablesInput.sendKeys(numberOfTables);
  }

  async getNumberOfTablesInput() {
    return this.numberOfTablesInput.getAttribute('value');
  }

  getAvailabilityInput() {
    return this.availabilityInput;
  }
  async setApiKeyInput(apiKey) {
    await this.apiKeyInput.sendKeys(apiKey);
  }

  async getApiKeyInput() {
    return this.apiKeyInput.getAttribute('value');
  }

  getPayLaterInput() {
    return this.payLaterInput;
  }
  getAskForServiceInput() {
    return this.askForServiceInput;
  }
  getEnableSmsInput() {
    return this.enableSmsInput;
  }
  async setSlugInput(slug) {
    await this.slugInput.sendKeys(slug);
  }

  async getSlugInput() {
    return this.slugInput.getAttribute('value');
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
