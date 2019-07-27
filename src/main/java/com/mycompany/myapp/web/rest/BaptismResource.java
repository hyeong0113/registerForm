package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Baptism;
import com.mycompany.myapp.repository.BaptismRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Baptism}.
 */
@RestController
@RequestMapping("/api")
public class BaptismResource {

    private final Logger log = LoggerFactory.getLogger(BaptismResource.class);

    private static final String ENTITY_NAME = "baptism";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaptismRepository baptismRepository;

    public BaptismResource(BaptismRepository baptismRepository) {
        this.baptismRepository = baptismRepository;
    }

    /**
     * {@code POST  /baptisms} : Create a new baptism.
     *
     * @param baptism the baptism to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baptism, or with status {@code 400 (Bad Request)} if the baptism has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/baptisms")
    public ResponseEntity<Baptism> createBaptism(@RequestBody Baptism baptism) throws URISyntaxException {
        log.debug("REST request to save Baptism : {}", baptism);
        if (baptism.getId() != null) {
            throw new BadRequestAlertException("A new baptism cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Baptism result = baptismRepository.save(baptism);
        return ResponseEntity.created(new URI("/api/baptisms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /baptisms} : Updates an existing baptism.
     *
     * @param baptism the baptism to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baptism,
     * or with status {@code 400 (Bad Request)} if the baptism is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baptism couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/baptisms")
    public ResponseEntity<Baptism> updateBaptism(@RequestBody Baptism baptism) throws URISyntaxException {
        log.debug("REST request to update Baptism : {}", baptism);
        if (baptism.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Baptism result = baptismRepository.save(baptism);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baptism.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /baptisms} : get all the baptisms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baptisms in body.
     */
    @GetMapping("/baptisms")
    public List<Baptism> getAllBaptisms() {
        log.debug("REST request to get all Baptisms");
        return baptismRepository.findAll();
    }

    /**
     * {@code GET  /baptisms/:id} : get the "id" baptism.
     *
     * @param id the id of the baptism to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baptism, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/baptisms/{id}")
    public ResponseEntity<Baptism> getBaptism(@PathVariable Long id) {
        log.debug("REST request to get Baptism : {}", id);
        Optional<Baptism> baptism = baptismRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(baptism);
    }

    /**
     * {@code DELETE  /baptisms/:id} : delete the "id" baptism.
     *
     * @param id the id of the baptism to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/baptisms/{id}")
    public ResponseEntity<Void> deleteBaptism(@PathVariable Long id) {
        log.debug("REST request to delete Baptism : {}", id);
        baptismRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
