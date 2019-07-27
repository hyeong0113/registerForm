import { element, by, ElementFinder } from 'protractor';

export default class VolunteerUpdatePage {
  pageTitle: ElementFinder = element(by.id('registerFormApp.volunteer.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  volunteerTypeInput: ElementFinder = element(by.css('input#volunteer-volunteerType'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setVolunteerTypeInput(volunteerType) {
    await this.volunteerTypeInput.sendKeys(volunteerType);
  }

  async getVolunteerTypeInput() {
    return this.volunteerTypeInput.getAttribute('value');
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
