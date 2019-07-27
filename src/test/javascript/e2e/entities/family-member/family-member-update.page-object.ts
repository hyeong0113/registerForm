import { element, by, ElementFinder } from 'protractor';

export default class FamilyMemberUpdatePage {
  pageTitle: ElementFinder = element(by.id('registerFormApp.familyMember.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  relationStatusInput: ElementFinder = element(by.css('input#family-member-relationStatus'));
  previousRegisterInput: ElementFinder = element(by.css('input#family-member-previousRegister'));
  korNameInput: ElementFinder = element(by.css('input#family-member-korName'));
  engNameInput: ElementFinder = element(by.css('input#family-member-engName'));
  birthdayInput: ElementFinder = element(by.css('input#family-member-birthday'));
  genderInput: ElementFinder = element(by.css('input#family-member-gender'));
  professionInput: ElementFinder = element(by.css('input#family-member-profession'));
  companyNameInput: ElementFinder = element(by.css('input#family-member-companyName'));
  cellPhoneInput: ElementFinder = element(by.css('input#family-member-cellPhone'));
  emailAddressInput: ElementFinder = element(by.css('input#family-member-emailAddress'));
  churchServedInput: ElementFinder = element(by.css('input#family-member-churchServed'));
  yearServedInput: ElementFinder = element(by.css('input#family-member-yearServed'));
  dutyStatusInput: ElementFinder = element(by.css('input#family-member-dutyStatus'));
  prevChurchInput: ElementFinder = element(by.css('input#family-member-prevChurch'));
  volunteerSelect: ElementFinder = element(by.css('select#family-member-volunteer'));
  registerMemberSelect: ElementFinder = element(by.css('select#family-member-registerMember'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setRelationStatusInput(relationStatus) {
    await this.relationStatusInput.sendKeys(relationStatus);
  }

  async getRelationStatusInput() {
    return this.relationStatusInput.getAttribute('value');
  }

  async setPreviousRegisterInput(previousRegister) {
    await this.previousRegisterInput.sendKeys(previousRegister);
  }

  async getPreviousRegisterInput() {
    return this.previousRegisterInput.getAttribute('value');
  }

  async setKorNameInput(korName) {
    await this.korNameInput.sendKeys(korName);
  }

  async getKorNameInput() {
    return this.korNameInput.getAttribute('value');
  }

  async setEngNameInput(engName) {
    await this.engNameInput.sendKeys(engName);
  }

  async getEngNameInput() {
    return this.engNameInput.getAttribute('value');
  }

  async setBirthdayInput(birthday) {
    await this.birthdayInput.sendKeys(birthday);
  }

  async getBirthdayInput() {
    return this.birthdayInput.getAttribute('value');
  }

  async setGenderInput(gender) {
    await this.genderInput.sendKeys(gender);
  }

  async getGenderInput() {
    return this.genderInput.getAttribute('value');
  }

  async setProfessionInput(profession) {
    await this.professionInput.sendKeys(profession);
  }

  async getProfessionInput() {
    return this.professionInput.getAttribute('value');
  }

  async setCompanyNameInput(companyName) {
    await this.companyNameInput.sendKeys(companyName);
  }

  async getCompanyNameInput() {
    return this.companyNameInput.getAttribute('value');
  }

  async setCellPhoneInput(cellPhone) {
    await this.cellPhoneInput.sendKeys(cellPhone);
  }

  async getCellPhoneInput() {
    return this.cellPhoneInput.getAttribute('value');
  }

  async setEmailAddressInput(emailAddress) {
    await this.emailAddressInput.sendKeys(emailAddress);
  }

  async getEmailAddressInput() {
    return this.emailAddressInput.getAttribute('value');
  }

  async setChurchServedInput(churchServed) {
    await this.churchServedInput.sendKeys(churchServed);
  }

  async getChurchServedInput() {
    return this.churchServedInput.getAttribute('value');
  }

  async setYearServedInput(yearServed) {
    await this.yearServedInput.sendKeys(yearServed);
  }

  async getYearServedInput() {
    return this.yearServedInput.getAttribute('value');
  }

  async setDutyStatusInput(dutyStatus) {
    await this.dutyStatusInput.sendKeys(dutyStatus);
  }

  async getDutyStatusInput() {
    return this.dutyStatusInput.getAttribute('value');
  }

  async setPrevChurchInput(prevChurch) {
    await this.prevChurchInput.sendKeys(prevChurch);
  }

  async getPrevChurchInput() {
    return this.prevChurchInput.getAttribute('value');
  }

  async volunteerSelectLastOption() {
    await this.volunteerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async volunteerSelectOption(option) {
    await this.volunteerSelect.sendKeys(option);
  }

  getVolunteerSelect() {
    return this.volunteerSelect;
  }

  async getVolunteerSelectedOption() {
    return this.volunteerSelect.element(by.css('option:checked')).getText();
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
