/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import FamilyMemberComponentsPage from './family-member.page-object';
import { FamilyMemberDeleteDialog } from './family-member.page-object';
import FamilyMemberUpdatePage from './family-member-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('FamilyMember e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let familyMemberUpdatePage: FamilyMemberUpdatePage;
  let familyMemberComponentsPage: FamilyMemberComponentsPage;
  let familyMemberDeleteDialog: FamilyMemberDeleteDialog;

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

  it('should load FamilyMembers', async () => {
    await navBarPage.getEntityPage('family-member');
    familyMemberComponentsPage = new FamilyMemberComponentsPage();
    expect(await familyMemberComponentsPage.getTitle().getText()).to.match(/Family Members/);
  });

  it('should load create FamilyMember page', async () => {
    await familyMemberComponentsPage.clickOnCreateButton();
    familyMemberUpdatePage = new FamilyMemberUpdatePage();
    expect(await familyMemberUpdatePage.getPageTitle().getAttribute('id')).to.match(/registerFormApp.familyMember.home.createOrEditLabel/);
    await familyMemberUpdatePage.cancel();
  });

  it('should create and save FamilyMembers', async () => {
    async function createFamilyMember() {
      await familyMemberComponentsPage.clickOnCreateButton();
      await familyMemberUpdatePage.setRelationStatusInput('relationStatus');
      expect(await familyMemberUpdatePage.getRelationStatusInput()).to.match(/relationStatus/);
      await familyMemberUpdatePage.setPreviousRegisterInput('previousRegister');
      expect(await familyMemberUpdatePage.getPreviousRegisterInput()).to.match(/previousRegister/);
      await familyMemberUpdatePage.setKorNameInput('korName');
      expect(await familyMemberUpdatePage.getKorNameInput()).to.match(/korName/);
      await familyMemberUpdatePage.setEngNameInput('engName');
      expect(await familyMemberUpdatePage.getEngNameInput()).to.match(/engName/);
      await familyMemberUpdatePage.setBirthdayInput('01-01-2001');
      expect(await familyMemberUpdatePage.getBirthdayInput()).to.eq('2001-01-01');
      await familyMemberUpdatePage.setGenderInput('gender');
      expect(await familyMemberUpdatePage.getGenderInput()).to.match(/gender/);
      await familyMemberUpdatePage.setProfessionInput('profession');
      expect(await familyMemberUpdatePage.getProfessionInput()).to.match(/profession/);
      await familyMemberUpdatePage.setCompanyNameInput('companyName');
      expect(await familyMemberUpdatePage.getCompanyNameInput()).to.match(/companyName/);
      await familyMemberUpdatePage.setCellPhoneInput('cellPhone');
      expect(await familyMemberUpdatePage.getCellPhoneInput()).to.match(/cellPhone/);
      await familyMemberUpdatePage.setEmailAddressInput('emailAddress');
      expect(await familyMemberUpdatePage.getEmailAddressInput()).to.match(/emailAddress/);
      await familyMemberUpdatePage.setChurchServedInput('churchServed');
      expect(await familyMemberUpdatePage.getChurchServedInput()).to.match(/churchServed/);
      await familyMemberUpdatePage.setYearServedInput('yearServed');
      expect(await familyMemberUpdatePage.getYearServedInput()).to.match(/yearServed/);
      await familyMemberUpdatePage.setDutyStatusInput('dutyStatus');
      expect(await familyMemberUpdatePage.getDutyStatusInput()).to.match(/dutyStatus/);
      await familyMemberUpdatePage.setPrevChurchInput('prevChurch');
      expect(await familyMemberUpdatePage.getPrevChurchInput()).to.match(/prevChurch/);
      // familyMemberUpdatePage.volunteerSelectLastOption();
      await familyMemberUpdatePage.registerMemberSelectLastOption();
      await waitUntilDisplayed(familyMemberUpdatePage.getSaveButton());
      await familyMemberUpdatePage.save();
      await waitUntilHidden(familyMemberUpdatePage.getSaveButton());
      expect(await familyMemberUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createFamilyMember();
    await familyMemberComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await familyMemberComponentsPage.countDeleteButtons();
    await createFamilyMember();

    await familyMemberComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await familyMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last FamilyMember', async () => {
    await familyMemberComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await familyMemberComponentsPage.countDeleteButtons();
    await familyMemberComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    familyMemberDeleteDialog = new FamilyMemberDeleteDialog();
    expect(await familyMemberDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/registerFormApp.familyMember.delete.question/);
    await familyMemberDeleteDialog.clickOnConfirmButton();

    await familyMemberComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await familyMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
