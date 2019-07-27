import { element, by, ElementFinder } from 'protractor';

export default class BaptismUpdatePage {
  pageTitle: ElementFinder = element(by.id('registerFormApp.baptism.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  baptismTypeInput: ElementFinder = element(by.css('input#baptism-baptismType'));
  baptismYearInput: ElementFinder = element(by.css('input#baptism-baptismYear'));
  baptismChurchInput: ElementFinder = element(by.css('input#baptism-baptismChurch'));
  registerMemberSelect: ElementFinder = element(by.css('select#baptism-registerMember'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setBaptismTypeInput(baptismType) {
    await this.baptismTypeInput.sendKeys(baptismType);
  }

  async getBaptismTypeInput() {
    return this.baptismTypeInput.getAttribute('value');
  }

  async setBaptismYearInput(baptismYear) {
    await this.baptismYearInput.sendKeys(baptismYear);
  }

  async getBaptismYearInput() {
    return this.baptismYearInput.getAttribute('value');
  }

  async setBaptismChurchInput(baptismChurch) {
    await this.baptismChurchInput.sendKeys(baptismChurch);
  }

  async getBaptismChurchInput() {
    return this.baptismChurchInput.getAttribute('value');
  }

  async registerMemberSelectLastOption() {
    await this.registerMemberSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async registerMemberSelectOption(option) {
    await this.registerMemberSelect.sendKeys(option);
  }

  getRegisterMemberSelect() {
    return this.registerMemberSelect;
  }

  async getRegisterMemberSelectedOption() {
    return this.registerMemberSelect.element(by.css('option:checked')).getText();
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
