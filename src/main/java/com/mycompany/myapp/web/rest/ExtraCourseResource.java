package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ExtraCourse;

import com.mycompany.myapp.repository.ExtraCourseRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExtraCourse.
 */
@RestController
@RequestMapping("/api")
public class ExtraCourseResource {

    private final Logger log = LoggerFactory.getLogger(ExtraCourseResource.class);

    private static final String ENTITY_NAME = "extraCourse";

    private final ExtraCourseRepository extraCourseRepository;

    public ExtraCourseResource(ExtraCourseRepository extraCourseRepository) {
        this.extraCourseRepository = extraCourseRepository;
    }

    /**
     * POST  /extra-courses : Create a new extraCourse.
     *
     * @param extraCourse the extraCourse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraCourse, or with status 400 (Bad Request) if the extraCourse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extra-courses")
    @Timed
    public ResponseEntity<ExtraCourse> createExtraCourse(@RequestBody ExtraCourse extraCourse) throws URISyntaxException {
        log.debug("REST request to save ExtraCourse : {}", extraCourse);
        if (extraCourse.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new extraCourse cannot already have an ID")).body(null);
        }
        ExtraCourse result = extraCourseRepository.save(extraCourse);
        return ResponseEntity.created(new URI("/api/extra-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extra-courses : Updates an existing extraCourse.
     *
     * @param extraCourse the extraCourse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraCourse,
     * or with status 400 (Bad Request) if the extraCourse is not valid,
     * or with status 500 (Internal Server Error) if the extraCourse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extra-courses")
    @Timed
    public ResponseEntity<ExtraCourse> updateExtraCourse(@RequestBody ExtraCourse extraCourse) throws URISyntaxException {
        log.debug("REST request to update ExtraCourse : {}", extraCourse);
        if (extraCourse.getId() == null) {
            return createExtraCourse(extraCourse);
        }
        ExtraCourse result = extraCourseRepository.save(extraCourse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extraCourse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extra-courses : get all the extraCourses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of extraCourses in body
     */
    @GetMapping("/extra-courses")
    @Timed
    public List<ExtraCourse> getAllExtraCourses() {
        log.debug("REST request to get all ExtraCourses");
        return extraCourseRepository.findAll();
    }

    /**
     * GET  /extra-courses/:id : get the "id" extraCourse.
     *
     * @param id the id of the extraCourse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraCourse, or with status 404 (Not Found)
     */
    @GetMapping("/extra-courses/{id}")
    @Timed
    public ResponseEntity<ExtraCourse> getExtraCourse(@PathVariable Long id) {
        log.debug("REST request to get ExtraCourse : {}", id);
        ExtraCourse extraCourse = extraCourseRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(extraCourse));
    }

    /**
     * DELETE  /extra-courses/:id : delete the "id" extraCourse.
     *
     * @param id the id of the extraCourse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extra-courses/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtraCourse(@PathVariable Long id) {
        log.debug("REST request to delete ExtraCourse : {}", id);
        extraCourseRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
