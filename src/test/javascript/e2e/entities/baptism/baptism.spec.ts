/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import BaptismComponentsPage from './baptism.page-object';
import { BaptismDeleteDialog } from './baptism.page-object';
import BaptismUpdatePage from './baptism-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Baptism e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let baptismUpdatePage: BaptismUpdatePage;
  let baptismComponentsPage: BaptismComponentsPage;
  let baptismDeleteDialog: BaptismDeleteDialog;

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

  it('should load Baptisms', async () => {
    await navBarPage.getEntityPage('baptism');
    baptismComponentsPage = new BaptismComponentsPage();
    expect(await baptismComponentsPage.getTitle().getText()).to.match(/Baptisms/);
  });

  it('should load create Baptism page', async () => {
    await baptismComponentsPage.clickOnCreateButton();
    baptismUpdatePage = new BaptismUpdatePage();
    expect(await baptismUpdatePage.getPageTitle().getAttribute('id')).to.match(/registerFormApp.baptism.home.createOrEditLabel/);
    await baptismUpdatePage.cancel();
  });

  it('should create and save Baptisms', async () => {
    async function createBaptism() {
      await baptismComponentsPage.clickOnCreateButton();
      await baptismUpdatePage.setBaptismTypeInput('baptismType');
      expect(await baptismUpdatePage.getBaptismTypeInput()).to.match(/baptismType/);
      await baptismUpdatePage.setBaptismYearInput('baptismYear');
      expect(await baptismUpdatePage.getBaptismYearInput()).to.match(/baptismYear/);
      await baptismUpdatePage.setBaptismChurchInput('baptismChurch');
      expect(await baptismUpdatePage.getBaptismChurchInput()).to.match(/baptismChurch/);
      await baptismUpdatePage.registerMemberSelectLastOption();
      await waitUntilDisplayed(baptismUpdatePage.getSaveButton());
      await baptismUpdatePage.save();
      await waitUntilHidden(baptismUpdatePage.getSaveButton());
      expect(await baptismUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createBaptism();
    await baptismComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await baptismComponentsPage.countDeleteButtons();
    await createBaptism();

    await baptismComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await baptismComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Baptism', async () => {
    await baptismComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await baptismComponentsPage.countDeleteButtons();
    await baptismComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    baptismDeleteDialog = new BaptismDeleteDialog();
    expect(await baptismDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/registerFormApp.baptism.delete.question/);
    await baptismDeleteDialog.clickOnConfirmButton();

    await baptismComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await baptismComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
