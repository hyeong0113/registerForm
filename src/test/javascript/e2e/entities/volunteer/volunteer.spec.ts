/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import VolunteerComponentsPage from './volunteer.page-object';
import { VolunteerDeleteDialog } from './volunteer.page-object';
import VolunteerUpdatePage from './volunteer-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Volunteer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let volunteerUpdatePage: VolunteerUpdatePage;
  let volunteerComponentsPage: VolunteerComponentsPage;
  let volunteerDeleteDialog: VolunteerDeleteDialog;

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

  it('should load Volunteers', async () => {
    await navBarPage.getEntityPage('volunteer');
    volunteerComponentsPage = new VolunteerComponentsPage();
    expect(await volunteerComponentsPage.getTitle().getText()).to.match(/Volunteers/);
  });

  it('should load create Volunteer page', async () => {
    await volunteerComponentsPage.clickOnCreateButton();
    volunteerUpdatePage = new VolunteerUpdatePage();
    expect(await volunteerUpdatePage.getPageTitle().getAttribute('id')).to.match(/registerFormApp.volunteer.home.createOrEditLabel/);
    await volunteerUpdatePage.cancel();
  });

  it('should create and save Volunteers', async () => {
    async function createVolunteer() {
      await volunteerComponentsPage.clickOnCreateButton();
      await volunteerUpdatePage.setVolunteerTypeInput('volunteerType');
      expect(await volunteerUpdatePage.getVolunteerTypeInput()).to.match(/volunteerType/);
      await waitUntilDisplayed(volunteerUpdatePage.getSaveButton());
      await volunteerUpdatePage.save();
      await waitUntilHidden(volunteerUpdatePage.getSaveButton());
      expect(await volunteerUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createVolunteer();
    await volunteerComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await volunteerComponentsPage.countDeleteButtons();
    await createVolunteer();

    await volunteerComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await volunteerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Volunteer', async () => {
    await volunteerComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await volunteerComponentsPage.countDeleteButtons();
    await volunteerComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    volunteerDeleteDialog = new VolunteerDeleteDialog();
    expect(await volunteerDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/registerFormApp.volunteer.delete.question/);
    await volunteerDeleteDialog.clickOnConfirmButton();

    await volunteerComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await volunteerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
