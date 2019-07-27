package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RegisterFormApp;
import com.mycompany.myapp.domain.Volunteer;
import com.mycompany.myapp.repository.VolunteerRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link VolunteerResource} REST controller.
 */
@SpringBootTest(classes = RegisterFormApp.class)
public class VolunteerResourceIT {

    private static final String DEFAULT_VOLUNTEER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VOLUNTEER_TYPE = "BBBBBBBBBB";

    @Autowired
    private VolunteerRepository volunteerRepository;

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

    private MockMvc restVolunteerMockMvc;

    private Volunteer volunteer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VolunteerResource volunteerResource = new VolunteerResource(volunteerRepository);
        this.restVolunteerMockMvc = MockMvcBuilders.standaloneSetup(volunteerResource)
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
    public static Volunteer createEntity(EntityManager em) {
        Volunteer volunteer = new Volunteer()
            .volunteerType(DEFAULT_VOLUNTEER_TYPE);
        return volunteer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Volunteer createUpdatedEntity(EntityManager em) {
        Volunteer volunteer = new Volunteer()
            .volunteerType(UPDATED_VOLUNTEER_TYPE);
        return volunteer;
    }

    @BeforeEach
    public void initTest() {
        volunteer = createEntity(em);
    }

    @Test
    @Transactional
    public void createVolunteer() throws Exception {
        int databaseSizeBeforeCreate = volunteerRepository.findAll().size();

        // Create the Volunteer
        restVolunteerMockMvc.perform(post("/api/volunteers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(volunteer)))
            .andExpect(status().isCreated());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeCreate + 1);
        Volunteer testVolunteer = volunteerList.get(volunteerList.size() - 1);
        assertThat(testVolunteer.getVolunteerType()).isEqualTo(DEFAULT_VOLUNTEER_TYPE);
    }

    @Test
    @Transactional
    public void createVolunteerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = volunteerRepository.findAll().size();

        // Create the Volunteer with an existing ID
        volunteer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVolunteerMockMvc.perform(post("/api/volunteers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(volunteer)))
            .andExpect(status().isBadRequest());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVolunteers() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        // Get all the volunteerList
        restVolunteerMockMvc.perform(get("/api/volunteers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(volunteer.getId().intValue())))
            .andExpect(jsonPath("$.[*].volunteerType").value(hasItem(DEFAULT_VOLUNTEER_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getVolunteer() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        // Get the volunteer
        restVolunteerMockMvc.perform(get("/api/volunteers/{id}", volunteer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(volunteer.getId().intValue()))
            .andExpect(jsonPath("$.volunteerType").value(DEFAULT_VOLUNTEER_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVolunteer() throws Exception {
        // Get the volunteer
        restVolunteerMockMvc.perform(get("/api/volunteers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVolunteer() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        int databaseSizeBeforeUpdate = volunteerRepository.findAll().size();

        // Update the volunteer
        Volunteer updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();
        // Disconnect from session so that the updates on updatedVolunteer are not directly saved in db
        em.detach(updatedVolunteer);
        updatedVolunteer
            .volunteerType(UPDATED_VOLUNTEER_TYPE);

        restVolunteerMockMvc.perform(put("/api/volunteers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVolunteer)))
            .andExpect(status().isOk());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeUpdate);
        Volunteer testVolunteer = volunteerList.get(volunteerList.size() - 1);
        assertThat(testVolunteer.getVolunteerType()).isEqualTo(UPDATED_VOLUNTEER_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingVolunteer() throws Exception {
        int databaseSizeBeforeUpdate = volunteerRepository.findAll().size();

        // Create the Volunteer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVolunteerMockMvc.perform(put("/api/volunteers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(volunteer)))
            .andExpect(status().isBadRequest());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVolunteer() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        int databaseSizeBeforeDelete = volunteerRepository.findAll().size();

        // Delete the volunteer
        restVolunteerMockMvc.perform(delete("/api/volunteers/{id}", volunteer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Volunteer.class);
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        Volunteer volunteer2 = new Volunteer();
        volunteer2.setId(volunteer1.getId());
        assertThat(volunteer1).isEqualTo(volunteer2);
        volunteer2.setId(2L);
        assertThat(volunteer1).isNotEqualTo(volunteer2);
        volunteer1.setId(null);
        assertThat(volunteer1).isNotEqualTo(volunteer2);
    }
}
