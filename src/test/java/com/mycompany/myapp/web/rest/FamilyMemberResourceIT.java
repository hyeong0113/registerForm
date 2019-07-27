package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RegisterFormApp;
import com.mycompany.myapp.domain.FamilyMember;
import com.mycompany.myapp.repository.FamilyMemberRepository;
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
 * Integration tests for the {@Link FamilyMemberResource} REST controller.
 */
@SpringBootTest(classes = RegisterFormApp.class)
public class FamilyMemberResourceIT {

    private static final String DEFAULT_RELATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RELATION_STATUS = "BBBBBBBBBB";

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

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CHURCH_SERVED = "AAAAAAAAAA";
    private static final String UPDATED_CHURCH_SERVED = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR_SERVED = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_SERVED = "BBBBBBBBBB";

    private static final String DEFAULT_DUTY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DUTY_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PREV_CHURCH = "AAAAAAAAAA";
    private static final String UPDATED_PREV_CHURCH = "BBBBBBBBBB";

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Mock
    private FamilyMemberRepository familyMemberRepositoryMock;

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

    private MockMvc restFamilyMemberMockMvc;

    private FamilyMember familyMember;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilyMemberResource familyMemberResource = new FamilyMemberResource(familyMemberRepository);
        this.restFamilyMemberMockMvc = MockMvcBuilders.standaloneSetup(familyMemberResource)
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
    public static FamilyMember createEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember()
            .relationStatus(DEFAULT_RELATION_STATUS)
            .previousRegister(DEFAULT_PREVIOUS_REGISTER)
            .korName(DEFAULT_KOR_NAME)
            .engName(DEFAULT_ENG_NAME)
            .birthday(DEFAULT_BIRTHDAY)
            .gender(DEFAULT_GENDER)
            .profession(DEFAULT_PROFESSION)
            .companyName(DEFAULT_COMPANY_NAME)
            .cellPhone(DEFAULT_CELL_PHONE)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .churchServed(DEFAULT_CHURCH_SERVED)
            .yearServed(DEFAULT_YEAR_SERVED)
            .dutyStatus(DEFAULT_DUTY_STATUS)
            .prevChurch(DEFAULT_PREV_CHURCH);
        return familyMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyMember createUpdatedEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember()
            .relationStatus(UPDATED_RELATION_STATUS)
            .previousRegister(UPDATED_PREVIOUS_REGISTER)
            .korName(UPDATED_KOR_NAME)
            .engName(UPDATED_ENG_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .profession(UPDATED_PROFESSION)
            .companyName(UPDATED_COMPANY_NAME)
            .cellPhone(UPDATED_CELL_PHONE)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .churchServed(UPDATED_CHURCH_SERVED)
            .yearServed(UPDATED_YEAR_SERVED)
            .dutyStatus(UPDATED_DUTY_STATUS)
            .prevChurch(UPDATED_PREV_CHURCH);
        return familyMember;
    }

    @BeforeEach
    public void initTest() {
        familyMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilyMember() throws Exception {
        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();

        // Create the FamilyMember
        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isCreated());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getRelationStatus()).isEqualTo(DEFAULT_RELATION_STATUS);
        assertThat(testFamilyMember.getPreviousRegister()).isEqualTo(DEFAULT_PREVIOUS_REGISTER);
        assertThat(testFamilyMember.getKorName()).isEqualTo(DEFAULT_KOR_NAME);
        assertThat(testFamilyMember.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testFamilyMember.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testFamilyMember.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testFamilyMember.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testFamilyMember.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testFamilyMember.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testFamilyMember.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testFamilyMember.getChurchServed()).isEqualTo(DEFAULT_CHURCH_SERVED);
        assertThat(testFamilyMember.getYearServed()).isEqualTo(DEFAULT_YEAR_SERVED);
        assertThat(testFamilyMember.getDutyStatus()).isEqualTo(DEFAULT_DUTY_STATUS);
        assertThat(testFamilyMember.getPrevChurch()).isEqualTo(DEFAULT_PREV_CHURCH);
    }

    @Test
    @Transactional
    public void createFamilyMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();

        // Create the FamilyMember with an existing ID
        familyMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRelationStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setRelationStatus(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreviousRegisterIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setPreviousRegister(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setKorName(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEngNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setEngName(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setBirthday(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setGender(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCellPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setCellPhone(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyMemberRepository.findAll().size();
        // set the field null
        familyMember.setEmailAddress(null);

        // Create the FamilyMember, which fails.

        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFamilyMembers() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get all the familyMemberList
        restFamilyMemberMockMvc.perform(get("/api/family-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].relationStatus").value(hasItem(DEFAULT_RELATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].previousRegister").value(hasItem(DEFAULT_PREVIOUS_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].korName").value(hasItem(DEFAULT_KOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].churchServed").value(hasItem(DEFAULT_CHURCH_SERVED.toString())))
            .andExpect(jsonPath("$.[*].yearServed").value(hasItem(DEFAULT_YEAR_SERVED.toString())))
            .andExpect(jsonPath("$.[*].dutyStatus").value(hasItem(DEFAULT_DUTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].prevChurch").value(hasItem(DEFAULT_PREV_CHURCH.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFamilyMembersWithEagerRelationshipsIsEnabled() throws Exception {
        FamilyMemberResource familyMemberResource = new FamilyMemberResource(familyMemberRepositoryMock);
        when(familyMemberRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFamilyMemberMockMvc = MockMvcBuilders.standaloneSetup(familyMemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFamilyMemberMockMvc.perform(get("/api/family-members?eagerload=true"))
        .andExpect(status().isOk());

        verify(familyMemberRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFamilyMembersWithEagerRelationshipsIsNotEnabled() throws Exception {
        FamilyMemberResource familyMemberResource = new FamilyMemberResource(familyMemberRepositoryMock);
            when(familyMemberRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFamilyMemberMockMvc = MockMvcBuilders.standaloneSetup(familyMemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFamilyMemberMockMvc.perform(get("/api/family-members?eagerload=true"))
        .andExpect(status().isOk());

            verify(familyMemberRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get the familyMember
        restFamilyMemberMockMvc.perform(get("/api/family-members/{id}", familyMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familyMember.getId().intValue()))
            .andExpect(jsonPath("$.relationStatus").value(DEFAULT_RELATION_STATUS.toString()))
            .andExpect(jsonPath("$.previousRegister").value(DEFAULT_PREVIOUS_REGISTER.toString()))
            .andExpect(jsonPath("$.korName").value(DEFAULT_KOR_NAME.toString()))
            .andExpect(jsonPath("$.engName").value(DEFAULT_ENG_NAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.churchServed").value(DEFAULT_CHURCH_SERVED.toString()))
            .andExpect(jsonPath("$.yearServed").value(DEFAULT_YEAR_SERVED.toString()))
            .andExpect(jsonPath("$.dutyStatus").value(DEFAULT_DUTY_STATUS.toString()))
            .andExpect(jsonPath("$.prevChurch").value(DEFAULT_PREV_CHURCH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilyMember() throws Exception {
        // Get the familyMember
        restFamilyMemberMockMvc.perform(get("/api/family-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Update the familyMember
        FamilyMember updatedFamilyMember = familyMemberRepository.findById(familyMember.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyMember are not directly saved in db
        em.detach(updatedFamilyMember);
        updatedFamilyMember
            .relationStatus(UPDATED_RELATION_STATUS)
            .previousRegister(UPDATED_PREVIOUS_REGISTER)
            .korName(UPDATED_KOR_NAME)
            .engName(UPDATED_ENG_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .profession(UPDATED_PROFESSION)
            .companyName(UPDATED_COMPANY_NAME)
            .cellPhone(UPDATED_CELL_PHONE)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .churchServed(UPDATED_CHURCH_SERVED)
            .yearServed(UPDATED_YEAR_SERVED)
            .dutyStatus(UPDATED_DUTY_STATUS)
            .prevChurch(UPDATED_PREV_CHURCH);

        restFamilyMemberMockMvc.perform(put("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilyMember)))
            .andExpect(status().isOk());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getRelationStatus()).isEqualTo(UPDATED_RELATION_STATUS);
        assertThat(testFamilyMember.getPreviousRegister()).isEqualTo(UPDATED_PREVIOUS_REGISTER);
        assertThat(testFamilyMember.getKorName()).isEqualTo(UPDATED_KOR_NAME);
        assertThat(testFamilyMember.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testFamilyMember.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testFamilyMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testFamilyMember.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testFamilyMember.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testFamilyMember.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testFamilyMember.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testFamilyMember.getChurchServed()).isEqualTo(UPDATED_CHURCH_SERVED);
        assertThat(testFamilyMember.getYearServed()).isEqualTo(UPDATED_YEAR_SERVED);
        assertThat(testFamilyMember.getDutyStatus()).isEqualTo(UPDATED_DUTY_STATUS);
        assertThat(testFamilyMember.getPrevChurch()).isEqualTo(UPDATED_PREV_CHURCH);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Create the FamilyMember

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc.perform(put("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeDelete = familyMemberRepository.findAll().size();

        // Delete the familyMember
        restFamilyMemberMockMvc.perform(delete("/api/family-members/{id}", familyMember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyMember.class);
        FamilyMember familyMember1 = new FamilyMember();
        familyMember1.setId(1L);
        FamilyMember familyMember2 = new FamilyMember();
        familyMember2.setId(familyMember1.getId());
        assertThat(familyMember1).isEqualTo(familyMember2);
        familyMember2.setId(2L);
        assertThat(familyMember1).isNotEqualTo(familyMember2);
        familyMember1.setId(null);
        assertThat(familyMember1).isNotEqualTo(familyMember2);
    }
}
