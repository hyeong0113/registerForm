import { element, by, ElementFinder } from 'protractor';

export default class RegisterMemberUpdatePage {
  pageTitle: ElementFinder = element(by.id('registerFormApp.registerMember.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  previousRegisterInput: ElementFinder = element(by.css('input#register-member-previousRegister'));
  korNameInput: ElementFinder = element(by.css('input#register-member-korName'));
  engNameInput: ElementFinder = element(by.css('input#register-member-engName'));
  birthdayInput: ElementFinder = element(by.css('input#register-member-birthday'));
  genderInput: ElementFinder = element(by.css('input#register-member-gender'));
  firstStreetInput: ElementFinder = element(by.css('input#register-member-firstStreet'));
  secondStreetInput: ElementFinder = element(by.css('input#register-member-secondStreet'));
  cityInput: ElementFinder = element(by.css('input#register-member-city'));
  provinceInput: ElementFinder = element(by.css('input#register-member-province'));
  postalCodeInput: ElementFinder = element(by.css('input#register-member-postalCode'));
  cellPhoneInput: ElementFinder = element(by.css('input#register-member-cellPhone'));
  residentialPhoneInput: ElementFinder = element(by.css('input#register-member-residentialPhone'));
  emailAddressInput: ElementFinder = element(by.css('input#register-member-emailAddress'));
  professionInput: ElementFinder = element(by.css('input#register-member-profession'));
  companyNameInput: ElementFinder = element(by.css('input#register-member-companyName'));
  firstLicenseplateInput: ElementFinder = element(by.css('input#register-member-firstLicenseplate'));
  secondLicenseplateInput: ElementFinder = element(by.css('input#register-member-secondLicenseplate'));
  residenceStatusInput: ElementFinder = element(by.css('input#register-member-residenceStatus'));
  dutyStatusInput: ElementFinder = element(by.css('input#register-member-dutyStatus'));
  prevChurchInput: ElementFinder = element(by.css('input#register-member-prevChurch'));
  instructorInput: ElementFinder = element(by.css('input#register-member-instructor'));
  volunteerSelect: ElementFinder = element(by.css('select#register-member-volunteer'));

  getPageTitle() {
    return this.pageTitle;
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

  async setFirstStreetInput(firstStreet) {
    await this.firstStreetInput.sendKeys(firstStreet);
  }

  async getFirstStreetInput() {
    return this.firstStreetInput.getAttribute('value');
  }

  async setSecondStreetInput(secondStreet) {
    await this.secondStreetInput.sendKeys(secondStreet);
  }

  async getSecondStreetInput() {
    return this.secondStreetInput.getAttribute('value');
  }

  async setCityInput(city) {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput() {
    return this.cityInput.getAttribute('value');
  }

  async setProvinceInput(province) {
    await this.provinceInput.sendKeys(province);
  }

  async getProvinceInput() {
    return this.provinceInput.getAttribute('value');
  }

  async setPostalCodeInput(postalCode) {
    await this.postalCodeInput.sendKeys(postalCode);
  }

  async getPostalCodeInput() {
    return this.postalCodeInput.getAttribute('value');
  }

  async setCellPhoneInput(cellPhone) {
    await this.cellPhoneInput.sendKeys(cellPhone);
  }

  async getCellPhoneInput() {
    return this.cellPhoneInput.getAttribute('value');
  }

  async setResidentialPhoneInput(residentialPhone) {
    await this.residentialPhoneInput.sendKeys(residentialPhone);
  }

  async getResidentialPhoneInput() {
    return this.residentialPhoneInput.getAttribute('value');
  }

  async setEmailAddressInput(emailAddress) {
    await this.emailAddressInput.sendKeys(emailAddress);
  }

  async getEmailAddressInput() {
    return this.emailAddressInput.getAttribute('value');
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

  async setFirstLicenseplateInput(firstLicenseplate) {
    await this.firstLicenseplateInput.sendKeys(firstLicenseplate);
  }

  async getFirstLicenseplateInput() {
    return this.firstLicenseplateInput.getAttribute('value');
  }

  async setSecondLicenseplateInput(secondLicenseplate) {
    await this.secondLicenseplateInput.sendKeys(secondLicenseplate);
  }

  async getSecondLicenseplateInput() {
    return this.secondLicenseplateInput.getAttribute('value');
  }

  async setResidenceStatusInput(residenceStatus) {
    await this.residenceStatusInput.sendKeys(residenceStatus);
  }

  async getResidenceStatusInput() {
    return this.residenceStatusInput.getAttribute('value');
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

  async setInstructorInput(instructor) {
    await this.instructorInput.sendKeys(instructor);
  }

  async getInstructorInput() {
    return this.instructorInput.getAttribute('value');
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
