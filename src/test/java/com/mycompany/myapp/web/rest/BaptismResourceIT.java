package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RegisterFormApp;
import com.mycompany.myapp.domain.Baptism;
import com.mycompany.myapp.repository.BaptismRepository;
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
 * Integration tests for the {@Link BaptismResource} REST controller.
 */
@SpringBootTest(classes = RegisterFormApp.class)
public class BaptismResourceIT {

    private static final String DEFAULT_BAPTISM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BAPTISM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BAPTISM_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_BAPTISM_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_BAPTISM_CHURCH = "AAAAAAAAAA";
    private static final String UPDATED_BAPTISM_CHURCH = "BBBBBBBBBB";

    @Autowired
    private BaptismRepository baptismRepository;

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

    private MockMvc restBaptismMockMvc;

    private Baptism baptism;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BaptismResource baptismResource = new BaptismResource(baptismRepository);
        this.restBaptismMockMvc = MockMvcBuilders.standaloneSetup(baptismResource)
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
    public static Baptism createEntity(EntityManager em) {
        Baptism baptism = new Baptism()
            .baptismType(DEFAULT_BAPTISM_TYPE)
            .baptismYear(DEFAULT_BAPTISM_YEAR)
            .baptismChurch(DEFAULT_BAPTISM_CHURCH);
        return baptism;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Baptism createUpdatedEntity(EntityManager em) {
        Baptism baptism = new Baptism()
            .baptismType(UPDATED_BAPTISM_TYPE)
            .baptismYear(UPDATED_BAPTISM_YEAR)
            .baptismChurch(UPDATED_BAPTISM_CHURCH);
        return baptism;
    }

    @BeforeEach
    public void initTest() {
        baptism = createEntity(em);
    }

    @Test
    @Transactional
    public void createBaptism() throws Exception {
        int databaseSizeBeforeCreate = baptismRepository.findAll().size();

        // Create the Baptism
        restBaptismMockMvc.perform(post("/api/baptisms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baptism)))
            .andExpect(status().isCreated());

        // Validate the Baptism in the database
        List<Baptism> baptismList = baptismRepository.findAll();
        assertThat(baptismList).hasSize(databaseSizeBeforeCreate + 1);
        Baptism testBaptism = baptismList.get(baptismList.size() - 1);
        assertThat(testBaptism.getBaptismType()).isEqualTo(DEFAULT_BAPTISM_TYPE);
        assertThat(testBaptism.getBaptismYear()).isEqualTo(DEFAULT_BAPTISM_YEAR);
        assertThat(testBaptism.getBaptismChurch()).isEqualTo(DEFAULT_BAPTISM_CHURCH);
    }

    @Test
    @Transactional
    public void createBaptismWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baptismRepository.findAll().size();

        // Create the Baptism with an existing ID
        baptism.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaptismMockMvc.perform(post("/api/baptisms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baptism)))
            .andExpect(status().isBadRequest());

        // Validate the Baptism in the database
        List<Baptism> baptismList = baptismRepository.findAll();
        assertThat(baptismList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBaptisms() throws Exception {
        // Initialize the database
        baptismRepository.saveAndFlush(baptism);

        // Get all the baptismList
        restBaptismMockMvc.perform(get("/api/baptisms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baptism.getId().intValue())))
            .andExpect(jsonPath("$.[*].baptismType").value(hasItem(DEFAULT_BAPTISM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].baptismYear").value(hasItem(DEFAULT_BAPTISM_YEAR.toString())))
            .andExpect(jsonPath("$.[*].baptismChurch").value(hasItem(DEFAULT_BAPTISM_CHURCH.toString())));
    }
    
    @Test
    @Transactional
    public void getBaptism() throws Exception {
        // Initialize the database
        baptismRepository.saveAndFlush(baptism);

        // Get the baptism
        restBaptismMockMvc.perform(get("/api/baptisms/{id}", baptism.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(baptism.getId().intValue()))
            .andExpect(jsonPath("$.baptismType").value(DEFAULT_BAPTISM_TYPE.toString()))
            .andExpect(jsonPath("$.baptismYear").value(DEFAULT_BAPTISM_YEAR.toString()))
            .andExpect(jsonPath("$.baptismChurch").value(DEFAULT_BAPTISM_CHURCH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBaptism() throws Exception {
        // Get the baptism
        restBaptismMockMvc.perform(get("/api/baptisms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBaptism() throws Exception {
        // Initialize the database
        baptismRepository.saveAndFlush(baptism);

        int databaseSizeBeforeUpdate = baptismRepository.findAll().size();

        // Update the baptism
        Baptism updatedBaptism = baptismRepository.findById(baptism.getId()).get();
        // Disconnect from session so that the updates on updatedBaptism are not directly saved in db
        em.detach(updatedBaptism);
        updatedBaptism
            .baptismType(UPDATED_BAPTISM_TYPE)
            .baptismYear(UPDATED_BAPTISM_YEAR)
            .baptismChurch(UPDATED_BAPTISM_CHURCH);

        restBaptismMockMvc.perform(put("/api/baptisms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBaptism)))
            .andExpect(status().isOk());

        // Validate the Baptism in the database
        List<Baptism> baptismList = baptismRepository.findAll();
        assertThat(baptismList).hasSize(databaseSizeBeforeUpdate);
        Baptism testBaptism = baptismList.get(baptismList.size() - 1);
        assertThat(testBaptism.getBaptismType()).isEqualTo(UPDATED_BAPTISM_TYPE);
        assertThat(testBaptism.getBaptismYear()).isEqualTo(UPDATED_BAPTISM_YEAR);
        assertThat(testBaptism.getBaptismChurch()).isEqualTo(UPDATED_BAPTISM_CHURCH);
    }

    @Test
    @Transactional
    public void updateNonExistingBaptism() throws Exception {
        int databaseSizeBeforeUpdate = baptismRepository.findAll().size();

        // Create the Baptism

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaptismMockMvc.perform(put("/api/baptisms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baptism)))
            .andExpect(status().isBadRequest());

        // Validate the Baptism in the database
        List<Baptism> baptismList = baptismRepository.findAll();
        assertThat(baptismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBaptism() throws Exception {
        // Initialize the database
        baptismRepository.saveAndFlush(baptism);

        int databaseSizeBeforeDelete = baptismRepository.findAll().size();

        // Delete the baptism
        restBaptismMockMvc.perform(delete("/api/baptisms/{id}", baptism.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Baptism> baptismList = baptismRepository.findAll();
        assertThat(baptismList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Baptism.class);
        Baptism baptism1 = new Baptism();
        baptism1.setId(1L);
        Baptism baptism2 = new Baptism();
        baptism2.setId(baptism1.getId());
        assertThat(baptism1).isEqualTo(baptism2);
        baptism2.setId(2L);
        assertThat(baptism1).isNotEqualTo(baptism2);
        baptism1.setId(null);
        assertThat(baptism1).isNotEqualTo(baptism2);
    }
}
