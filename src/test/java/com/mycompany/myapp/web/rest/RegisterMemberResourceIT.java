package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RegisterFormApp;
import com.mycompany.myapp.domain.RegisterMember;
import com.mycompany.myapp.repository.RegisterMemberRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RegisterMemberResource} REST controller.
 */
@SpringBootTest(classes = RegisterFormApp.class)
public class RegisterMemberResourceIT {

    private static final String DEFAULT_PREVIOUS_REGISTER = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_REGISTER = "BBBBBBBBBB";

    private static final String DEFAULT_KOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_KOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENG_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_STREET = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_STREET = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENTIAL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENTIAL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_LICENSEPLATE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_LICENSEPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_LICENSEPLATE = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_LICENSEPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DUTY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DUTY_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PREV_CHURCH = "AAAAAAAAAA";
    private static final String UPDATED_PREV_CHURCH = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUCTOR = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTOR = "BBBBBBBBBB";

    @Autowired
    private RegisterMemberRepository registerMemberRepository;

    @Mock
    private RegisterMemberRepository registerMemberRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRegisterMemberMockMvc;

    private RegisterMember registerMember;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegisterMemberResource registerMemberResource = new RegisterMemberResource(registerMemberRepository);
        this.restRegisterMemberMockMvc = MockMvcBuilders.standaloneSetup(registerMemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegisterMember createEntity(EntityManager em) {
        RegisterMember registerMember = new RegisterMember()
            .previousRegister(DEFAULT_PREVIOUS_REGISTER)
            .korName(DEFAULT_KOR_NAME)
            .engName(DEFAULT_ENG_NAME)
            .birthday(DEFAULT_BIRTHDAY)
            .gender(DEFAULT_GENDER)
            .firstStreet(DEFAULT_FIRST_STREET)
            .secondStreet(DEFAULT_SECOND_STREET)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE)
            .postalCode(DEFAULT_POSTAL_CODE)
            .cellPhone(DEFAULT_CELL_PHONE)
            .residentialPhone(DEFAULT_RESIDENTIAL_PHONE)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .profession(DEFAULT_PROFESSION)
            .companyName(DEFAULT_COMPANY_NAME)
            .firstLicenseplate(DEFAULT_FIRST_LICENSEPLATE)
            .secondLicenseplate(DEFAULT_SECOND_LICENSEPLATE)
            .residenceStatus(DEFAULT_RESIDENCE_STATUS)
            .dutyStatus(DEFAULT_DUTY_STATUS)
            .prevChurch(DEFAULT_PREV_CHURCH)
            .instructor(DEFAULT_INSTRUCTOR);
        return registerMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegisterMember createUpdatedEntity(EntityManager em) {
        RegisterMember registerMember = new RegisterMember()
            .previousRegister(UPDATED_PREVIOUS_REGISTER)
            .korName(UPDATED_KOR_NAME)
            .engName(UPDATED_ENG_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .firstStreet(UPDATED_FIRST_STREET)
            .secondStreet(UPDATED_SECOND_STREET)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .postalCode(UPDATED_POSTAL_CODE)
            .cellPhone(UPDATED_CELL_PHONE)
            .residentialPhone(UPDATED_RESIDENTIAL_PHONE)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .profession(UPDATED_PROFESSION)
            .companyName(UPDATED_COMPANY_NAME)
            .firstLicenseplate(UPDATED_FIRST_LICENSEPLATE)
            .secondLicenseplate(UPDATED_SECOND_LICENSEPLATE)
            .residenceStatus(UPDATED_RESIDENCE_STATUS)
            .dutyStatus(UPDATED_DUTY_STATUS)
            .prevChurch(UPDATED_PREV_CHURCH)
            .instructor(UPDATED_INSTRUCTOR);
        return registerMember;
    }

    @BeforeEach
    public void initTest() {
        registerMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegisterMember() throws Exception {
        int databaseSizeBeforeCreate = registerMemberRepository.findAll().size();

        // Create the RegisterMember
        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isCreated());

        // Validate the RegisterMember in the database
        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeCreate + 1);
        RegisterMember testRegisterMember = registerMemberList.get(registerMemberList.size() - 1);
        assertThat(testRegisterMember.getPreviousRegister()).isEqualTo(DEFAULT_PREVIOUS_REGISTER);
        assertThat(testRegisterMember.getKorName()).isEqualTo(DEFAULT_KOR_NAME);
        assertThat(testRegisterMember.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testRegisterMember.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testRegisterMember.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testRegisterMember.getFirstStreet()).isEqualTo(DEFAULT_FIRST_STREET);
        assertThat(testRegisterMember.getSecondStreet()).isEqualTo(DEFAULT_SECOND_STREET);
        assertThat(testRegisterMember.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testRegisterMember.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testRegisterMember.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testRegisterMember.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testRegisterMember.getResidentialPhone()).isEqualTo(DEFAULT_RESIDENTIAL_PHONE);
        assertThat(testRegisterMember.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testRegisterMember.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testRegisterMember.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testRegisterMember.getFirstLicenseplate()).isEqualTo(DEFAULT_FIRST_LICENSEPLATE);
        assertThat(testRegisterMember.getSecondLicenseplate()).isEqualTo(DEFAULT_SECOND_LICENSEPLATE);
        assertThat(testRegisterMember.getResidenceStatus()).isEqualTo(DEFAULT_RESIDENCE_STATUS);
        assertThat(testRegisterMember.getDutyStatus()).isEqualTo(DEFAULT_DUTY_STATUS);
        assertThat(testRegisterMember.getPrevChurch()).isEqualTo(DEFAULT_PREV_CHURCH);
        assertThat(testRegisterMember.getInstructor()).isEqualTo(DEFAULT_INSTRUCTOR);
    }

    @Test
    @Transactional
    public void createRegisterMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registerMemberRepository.findAll().size();

        // Create the RegisterMember with an existing ID
        registerMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        // Validate the RegisterMember in the database
        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPreviousRegisterIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setPreviousRegister(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setKorName(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEngNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setEngName(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setBirthday(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setGender(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setFirstStreet(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setCity(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setProvince(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setPostalCode(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCellPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setCellPhone(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setEmailAddress(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResidenceStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerMemberRepository.findAll().size();
        // set the field null
        registerMember.setResidenceStatus(null);

        // Create the RegisterMember, which fails.

        restRegisterMemberMockMvc.perform(post("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegisterMembers() throws Exception {
        // Initialize the database
        registerMemberRepository.saveAndFlush(registerMember);

        // Get all the registerMemberList
        restRegisterMemberMockMvc.perform(get("/api/register-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registerMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].previousRegister").value(hasItem(DEFAULT_PREVIOUS_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].korName").value(hasItem(DEFAULT_KOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].firstStreet").value(hasItem(DEFAULT_FIRST_STREET.toString())))
            .andExpect(jsonPath("$.[*].secondStreet").value(hasItem(DEFAULT_SECOND_STREET.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].residentialPhone").value(hasItem(DEFAULT_RESIDENTIAL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstLicenseplate").value(hasItem(DEFAULT_FIRST_LICENSEPLATE.toString())))
            .andExpect(jsonPath("$.[*].secondLicenseplate").value(hasItem(DEFAULT_SECOND_LICENSEPLATE.toString())))
            .andExpect(jsonPath("$.[*].residenceStatus").value(hasItem(DEFAULT_RESIDENCE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dutyStatus").value(hasItem(DEFAULT_DUTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].prevChurch").value(hasItem(DEFAULT_PREV_CHURCH.toString())))
            .andExpect(jsonPath("$.[*].instructor").value(hasItem(DEFAULT_INSTRUCTOR.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRegisterMembersWithEagerRelationshipsIsEnabled() throws Exception {
        RegisterMemberResource registerMemberResource = new RegisterMemberResource(registerMemberRepositoryMock);
        when(registerMemberRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRegisterMemberMockMvc = MockMvcBuilders.standaloneSetup(registerMemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegisterMemberMockMvc.perform(get("/api/register-members?eagerload=true"))
        .andExpect(status().isOk());

        verify(registerMemberRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRegisterMembersWithEagerRelationshipsIsNotEnabled() throws Exception {
        RegisterMemberResource registerMemberResource = new RegisterMemberResource(registerMemberRepositoryMock);
            when(registerMemberRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRegisterMemberMockMvc = MockMvcBuilders.standaloneSetup(registerMemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegisterMemberMockMvc.perform(get("/api/register-members?eagerload=true"))
        .andExpect(status().isOk());

            verify(registerMemberRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRegisterMember() throws Exception {
        // Initialize the database
        registerMemberRepository.saveAndFlush(registerMember);

        // Get the registerMember
        restRegisterMemberMockMvc.perform(get("/api/register-members/{id}", registerMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registerMember.getId().intValue()))
            .andExpect(jsonPath("$.previousRegister").value(DEFAULT_PREVIOUS_REGISTER.toString()))
            .andExpect(jsonPath("$.korName").value(DEFAULT_KOR_NAME.toString()))
            .andExpect(jsonPath("$.engName").value(DEFAULT_ENG_NAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.firstStreet").value(DEFAULT_FIRST_STREET.toString()))
            .andExpect(jsonPath("$.secondStreet").value(DEFAULT_SECOND_STREET.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE.toString()))
            .andExpect(jsonPath("$.residentialPhone").value(DEFAULT_RESIDENTIAL_PHONE.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.firstLicenseplate").value(DEFAULT_FIRST_LICENSEPLATE.toString()))
            .andExpect(jsonPath("$.secondLicenseplate").value(DEFAULT_SECOND_LICENSEPLATE.toString()))
            .andExpect(jsonPath("$.residenceStatus").value(DEFAULT_RESIDENCE_STATUS.toString()))
            .andExpect(jsonPath("$.dutyStatus").value(DEFAULT_DUTY_STATUS.toString()))
            .andExpect(jsonPath("$.prevChurch").value(DEFAULT_PREV_CHURCH.toString()))
            .andExpect(jsonPath("$.instructor").value(DEFAULT_INSTRUCTOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegisterMember() throws Exception {
        // Get the registerMember
        restRegisterMemberMockMvc.perform(get("/api/register-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegisterMember() throws Exception {
        // Initialize the database
        registerMemberRepository.saveAndFlush(registerMember);

        int databaseSizeBeforeUpdate = registerMemberRepository.findAll().size();

        // Update the registerMember
        RegisterMember updatedRegisterMember = registerMemberRepository.findById(registerMember.getId()).get();
        // Disconnect from session so that the updates on updatedRegisterMember are not directly saved in db
        em.detach(updatedRegisterMember);
        updatedRegisterMember
            .previousRegister(UPDATED_PREVIOUS_REGISTER)
            .korName(UPDATED_KOR_NAME)
            .engName(UPDATED_ENG_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .firstStreet(UPDATED_FIRST_STREET)
            .secondStreet(UPDATED_SECOND_STREET)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .postalCode(UPDATED_POSTAL_CODE)
            .cellPhone(UPDATED_CELL_PHONE)
            .residentialPhone(UPDATED_RESIDENTIAL_PHONE)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .profession(UPDATED_PROFESSION)
            .companyName(UPDATED_COMPANY_NAME)
            .firstLicenseplate(UPDATED_FIRST_LICENSEPLATE)
            .secondLicenseplate(UPDATED_SECOND_LICENSEPLATE)
            .residenceStatus(UPDATED_RESIDENCE_STATUS)
            .dutyStatus(UPDATED_DUTY_STATUS)
            .prevChurch(UPDATED_PREV_CHURCH)
            .instructor(UPDATED_INSTRUCTOR);

        restRegisterMemberMockMvc.perform(put("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegisterMember)))
            .andExpect(status().isOk());

        // Validate the RegisterMember in the database
        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeUpdate);
        RegisterMember testRegisterMember = registerMemberList.get(registerMemberList.size() - 1);
        assertThat(testRegisterMember.getPreviousRegister()).isEqualTo(UPDATED_PREVIOUS_REGISTER);
        assertThat(testRegisterMember.getKorName()).isEqualTo(UPDATED_KOR_NAME);
        assertThat(testRegisterMember.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testRegisterMember.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testRegisterMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testRegisterMember.getFirstStreet()).isEqualTo(UPDATED_FIRST_STREET);
        assertThat(testRegisterMember.getSecondStreet()).isEqualTo(UPDATED_SECOND_STREET);
        assertThat(testRegisterMember.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testRegisterMember.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testRegisterMember.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testRegisterMember.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testRegisterMember.getResidentialPhone()).isEqualTo(UPDATED_RESIDENTIAL_PHONE);
        assertThat(testRegisterMember.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testRegisterMember.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testRegisterMember.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testRegisterMember.getFirstLicenseplate()).isEqualTo(UPDATED_FIRST_LICENSEPLATE);
        assertThat(testRegisterMember.getSecondLicenseplate()).isEqualTo(UPDATED_SECOND_LICENSEPLATE);
        assertThat(testRegisterMember.getResidenceStatus()).isEqualTo(UPDATED_RESIDENCE_STATUS);
        assertThat(testRegisterMember.getDutyStatus()).isEqualTo(UPDATED_DUTY_STATUS);
        assertThat(testRegisterMember.getPrevChurch()).isEqualTo(UPDATED_PREV_CHURCH);
        assertThat(testRegisterMember.getInstructor()).isEqualTo(UPDATED_INSTRUCTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingRegisterMember() throws Exception {
        int databaseSizeBeforeUpdate = registerMemberRepository.findAll().size();

        // Create the RegisterMember

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegisterMemberMockMvc.perform(put("/api/register-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerMember)))
            .andExpect(status().isBadRequest());

        // Validate the RegisterMember in the database
        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegisterMember() throws Exception {
        // Initialize the database
        registerMemberRepository.saveAndFlush(registerMember);

        int databaseSizeBeforeDelete = registerMemberRepository.findAll().size();

        // Delete the registerMember
        restRegisterMemberMockMvc.perform(delete("/api/register-members/{id}", registerMember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegisterMember> registerMemberList = registerMemberRepository.findAll();
        assertThat(registerMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegisterMember.class);
        RegisterMember registerMember1 = new RegisterMember();
        registerMember1.setId(1L);
        RegisterMember registerMember2 = new RegisterMember();
        registerMember2.setId(registerMember1.getId());
        assertThat(registerMember1).isEqualTo(registerMember2);
        registerMember2.setId(2L);
        assertThat(registerMember1).isNotEqualTo(registerMember2);
        registerMember1.setId(null);
        assertThat(registerMember1).isNotEqualTo(registerMember2);
    }
}
