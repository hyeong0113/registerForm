/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RegisterMemberComponentsPage from './register-member.page-object';
import { RegisterMemberDeleteDialog } from './register-member.page-object';
import RegisterMemberUpdatePage from './register-member-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('RegisterMember e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let registerMemberUpdatePage: RegisterMemberUpdatePage;
  let registerMemberComponentsPage: RegisterMemberComponentsPage;
  let registerMemberDeleteDialog: RegisterMemberDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
  });

  it('should load RegisterMembers', async () => {
    await navBarPage.getEntityPage('register-member');
    registerMemberComponentsPage = new RegisterMemberComponentsPage();
    expect(await registerMemberComponentsPage.getTitle().getText()).to.match(/Register Members/);
  });

  it('should load create RegisterMember page', async () => {
    await registerMemberComponentsPage.clickOnCreateButton();
    registerMemberUpdatePage = new RegisterMemberUpdatePage();
    expect(await registerMemberUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /registerFormApp.registerMember.home.createOrEditLabel/
    );
    await registerMemberUpdatePage.cancel();
  });

  it('should create and save RegisterMembers', async () => {
    async function createRegisterMember() {
      await registerMemberComponentsPage.clickOnCreateButton();
      await registerMemberUpdatePage.setPreviousRegisterInput('previousRegister');
      expect(await registerMemberUpdatePage.getPreviousRegisterInput()).to.match(/previousRegister/);
      await registerMemberUpdatePage.setKorNameInput('korName');
      expect(await registerMemberUpdatePage.getKorNameInput()).to.match(/korName/);
      await registerMemberUpdatePage.setEngNameInput('engName');
      expect(await registerMemberUpdatePage.getEngNameInput()).to.match(/engName/);
      await registerMemberUpdatePage.setBirthdayInput('01-01-2001');
      expect(await registerMemberUpdatePage.getBirthdayInput()).to.eq('2001-01-01');
      await registerMemberUpdatePage.setGenderInput('gender');
      expect(await registerMemberUpdatePage.getGenderInput()).to.match(/gender/);
      await registerMemberUpdatePage.setFirstStreetInput('firstStreet');
      expect(await registerMemberUpdatePage.getFirstStreetInput()).to.match(/firstStreet/);
      await registerMemberUpdatePage.setSecondStreetInput('secondStreet');
      expect(await registerMemberUpdatePage.getSecondStreetInput()).to.match(/secondStreet/);
      await registerMemberUpdatePage.setCityInput('city');
      expect(await registerMemberUpdatePage.getCityInput()).to.match(/city/);
      await registerMemberUpdatePage.setProvinceInput('province');
      expect(await registerMemberUpdatePage.getProvinceInput()).to.match(/province/);
      await registerMemberUpdatePage.setPostalCodeInput('postalCode');
      expect(await registerMemberUpdatePage.getPostalCodeInput()).to.match(/postalCode/);
      await registerMemberUpdatePage.setCellPhoneInput('cellPhone');
      expect(await registerMemberUpdatePage.getCellPhoneInput()).to.match(/cellPhone/);
      await registerMemberUpdatePage.setResidentialPhoneInput('residentialPhone');
      expect(await registerMemberUpdatePage.getResidentialPhoneInput()).to.match(/residentialPhone/);
      await registerMemberUpdatePage.setEmailAddressInput('emailAddress');
      expect(await registerMemberUpdatePage.getEmailAddressInput()).to.match(/emailAddress/);
      await registerMemberUpdatePage.setProfessionInput('profession');
      expect(await registerMemberUpdatePage.getProfessionInput()).to.match(/profession/);
      await registerMemberUpdatePage.setCompanyNameInput('companyName');
      expect(await registerMemberUpdatePage.getCompanyNameInput()).to.match(/companyName/);
      await registerMemberUpdatePage.setFirstLicenseplateInput('firstLicenseplate');
      expect(await registerMemberUpdatePage.getFirstLicenseplateInput()).to.match(/firstLicenseplate/);
      await registerMemberUpdatePage.setSecondLicenseplateInput('secondLicenseplate');
      expect(await registerMemberUpdatePage.getSecondLicenseplateInput()).to.match(/secondLicenseplate/);
      await registerMemberUpdatePage.setResidenceStatusInput('residenceStatus');
      expect(await registerMemberUpdatePage.getResidenceStatusInput()).to.match(/residenceStatus/);
      await registerMemberUpdatePage.setDutyStatusInput('dutyStatus');
      expect(await registerMemberUpdatePage.getDutyStatusInput()).to.match(/dutyStatus/);
      await registerMemberUpdatePage.setPrevChurchInput('prevChurch');
      expect(await registerMemberUpdatePage.getPrevChurchInput()).to.match(/prevChurch/);
      await registerMemberUpdatePage.setInstructorInput('instructor');
      expect(await registerMemberUpdatePage.getInstructorInput()).to.match(/instructor/);
      // registerMemberUpdatePage.volunteerSelectLastOption();
      await waitUntilDisplayed(registerMemberUpdatePage.getSaveButton());
      await registerMemberUpdatePage.save();
      await waitUntilHidden(registerMemberUpdatePage.getSaveButton());
      expect(await registerMemberUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createRegisterMember();
    await registerMemberComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await registerMemberComponentsPage.countDeleteButtons();
    await createRegisterMember();

    await registerMemberComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await registerMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last RegisterMember', async () => {
    await registerMemberComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await registerMemberComponentsPage.countDeleteButtons();
    await registerMemberComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    registerMemberDeleteDialog = new RegisterMemberDeleteDialog();
    expect(await registerMemberDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/registerFormApp.registerMember.delete.question/);
    await registerMemberDeleteDialog.clickOnConfirmButton();

    await registerMemberComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await registerMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
