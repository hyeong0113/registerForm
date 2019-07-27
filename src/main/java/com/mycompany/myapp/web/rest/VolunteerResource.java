package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Volunteer;
import com.mycompany.myapp.repository.VolunteerRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Volunteer}.
 */
@RestController
@RequestMapping("/api")
public class VolunteerResource {

    private final Logger log = LoggerFactory.getLogger(VolunteerResource.class);

    private static final String ENTITY_NAME = "volunteer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VolunteerRepository volunteerRepository;

    public VolunteerResource(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * {@code POST  /volunteers} : Create a new volunteer.
     *
     * @param volunteer the volunteer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new volunteer, or with status {@code 400 (Bad Request)} if the volunteer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/volunteers")
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) throws URISyntaxException {
        log.debug("REST request to save Volunteer : {}", volunteer);
        if (volunteer.getId() != null) {
            throw new BadRequestAlertException("A new volunteer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Volunteer result = volunteerRepository.save(volunteer);
        return ResponseEntity.created(new URI("/api/volunteers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /volunteers} : Updates an existing volunteer.
     *
     * @param volunteer the volunteer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated volunteer,
     * or with status {@code 400 (Bad Request)} if the volunteer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the volunteer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/volunteers")
    public ResponseEntity<Volunteer> updateVolunteer(@RequestBody Volunteer volunteer) throws URISyntaxException {
        log.debug("REST request to update Volunteer : {}", volunteer);
        if (volunteer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Volunteer result = volunteerRepository.save(volunteer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, volunteer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /volunteers} : get all the volunteers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of volunteers in body.
     */
    @GetMapping("/volunteers")
    public List<Volunteer> getAllVolunteers() {
        log.debug("REST request to get all Volunteers");
        return volunteerRepository.findAll();
    }

    /**
     * {@code GET  /volunteers/:id} : get the "id" volunteer.
     *
     * @param id the id of the volunteer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the volunteer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/volunteers/{id}")
    public ResponseEntity<Volunteer> getVolunteer(@PathVariable Long id) {
        log.debug("REST request to get Volunteer : {}", id);
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(volunteer);
    }

    /**
     * {@code DELETE  /volunteers/:id} : delete the "id" volunteer.
     *
     * @param id the id of the volunteer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/volunteers/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        log.debug("REST request to delete Volunteer : {}", id);
        volunteerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
