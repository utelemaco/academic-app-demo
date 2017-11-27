package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppNameApp;

import com.mycompany.myapp.domain.ExtraCourse;
import com.mycompany.myapp.repository.ExtraCourseRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExtraCourseResource REST controller.
 *
 * @see ExtraCourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppNameApp.class)
public class ExtraCourseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_GRADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRADE = new BigDecimal(2);

    @Autowired
    private ExtraCourseRepository extraCourseRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExtraCourseMockMvc;

    private ExtraCourse extraCourse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExtraCourseResource extraCourseResource = new ExtraCourseResource(extraCourseRepository);
        this.restExtraCourseMockMvc = MockMvcBuilders.standaloneSetup(extraCourseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraCourse createEntity(EntityManager em) {
        ExtraCourse extraCourse = new ExtraCourse()
            .name(DEFAULT_NAME)
            .grade(DEFAULT_GRADE);
        return extraCourse;
    }

    @Before
    public void initTest() {
        extraCourse = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtraCourse() throws Exception {
        int databaseSizeBeforeCreate = extraCourseRepository.findAll().size();

        // Create the ExtraCourse
        restExtraCourseMockMvc.perform(post("/api/extra-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraCourse)))
            .andExpect(status().isCreated());

        // Validate the ExtraCourse in the database
        List<ExtraCourse> extraCourseList = extraCourseRepository.findAll();
        assertThat(extraCourseList).hasSize(databaseSizeBeforeCreate + 1);
        ExtraCourse testExtraCourse = extraCourseList.get(extraCourseList.size() - 1);
        assertThat(testExtraCourse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExtraCourse.getGrade()).isEqualTo(DEFAULT_GRADE);
    }

    @Test
    @Transactional
    public void createExtraCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extraCourseRepository.findAll().size();

        // Create the ExtraCourse with an existing ID
        extraCourse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraCourseMockMvc.perform(post("/api/extra-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraCourse)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExtraCourse> extraCourseList = extraCourseRepository.findAll();
        assertThat(extraCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExtraCourses() throws Exception {
        // Initialize the database
        extraCourseRepository.saveAndFlush(extraCourse);

        // Get all the extraCourseList
        restExtraCourseMockMvc.perform(get("/api/extra-courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.intValue())));
    }

    @Test
    @Transactional
    public void getExtraCourse() throws Exception {
        // Initialize the database
        extraCourseRepository.saveAndFlush(extraCourse);

        // Get the extraCourse
        restExtraCourseMockMvc.perform(get("/api/extra-courses/{id}", extraCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extraCourse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExtraCourse() throws Exception {
        // Get the extraCourse
        restExtraCourseMockMvc.perform(get("/api/extra-courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtraCourse() throws Exception {
        // Initialize the database
        extraCourseRepository.saveAndFlush(extraCourse);
        int databaseSizeBeforeUpdate = extraCourseRepository.findAll().size();

        // Update the extraCourse
        ExtraCourse updatedExtraCourse = extraCourseRepository.findOne(extraCourse.getId());
        updatedExtraCourse
            .name(UPDATED_NAME)
            .grade(UPDATED_GRADE);

        restExtraCourseMockMvc.perform(put("/api/extra-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExtraCourse)))
            .andExpect(status().isOk());

        // Validate the ExtraCourse in the database
        List<ExtraCourse> extraCourseList = extraCourseRepository.findAll();
        assertThat(extraCourseList).hasSize(databaseSizeBeforeUpdate);
        ExtraCourse testExtraCourse = extraCourseList.get(extraCourseList.size() - 1);
        assertThat(testExtraCourse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExtraCourse.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void updateNonExistingExtraCourse() throws Exception {
        int databaseSizeBeforeUpdate = extraCourseRepository.findAll().size();

        // Create the ExtraCourse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExtraCourseMockMvc.perform(put("/api/extra-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraCourse)))
            .andExpect(status().isCreated());

        // Validate the ExtraCourse in the database
        List<ExtraCourse> extraCourseList = extraCourseRepository.findAll();
        assertThat(extraCourseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExtraCourse() throws Exception {
        // Initialize the database
        extraCourseRepository.saveAndFlush(extraCourse);
        int databaseSizeBeforeDelete = extraCourseRepository.findAll().size();

        // Get the extraCourse
        restExtraCourseMockMvc.perform(delete("/api/extra-courses/{id}", extraCourse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtraCourse> extraCourseList = extraCourseRepository.findAll();
        assertThat(extraCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraCourse.class);
        ExtraCourse extraCourse1 = new ExtraCourse();
        extraCourse1.setId(1L);
        ExtraCourse extraCourse2 = new ExtraCourse();
        extraCourse2.setId(extraCourse1.getId());
        assertThat(extraCourse1).isEqualTo(extraCourse2);
        extraCourse2.setId(2L);
        assertThat(extraCourse1).isNotEqualTo(extraCourse2);
        extraCourse1.setId(null);
        assertThat(extraCourse1).isNotEqualTo(extraCourse2);
    }
}
