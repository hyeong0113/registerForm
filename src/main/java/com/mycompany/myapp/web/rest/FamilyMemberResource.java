package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FamilyMember;
import com.mycompany.myapp.repository.FamilyMemberRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.FamilyMember}.
 */
@RestController
@RequestMapping("/api")
public class FamilyMemberResource {

    private final Logger log = LoggerFactory.getLogger(FamilyMemberResource.class);

    private static final String ENTITY_NAME = "familyMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberResource(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    /**
     * {@code POST  /family-members} : Create a new familyMember.
     *
     * @param familyMember the familyMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyMember, or with status {@code 400 (Bad Request)} if the familyMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/family-members")
    public ResponseEntity<FamilyMember> createFamilyMember(@Valid @RequestBody FamilyMember familyMember) throws URISyntaxException {
        log.debug("REST request to save FamilyMember : {}", familyMember);
        if (familyMember.getId() != null) {
            throw new BadRequestAlertException("A new familyMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyMember result = familyMemberRepository.save(familyMember);
        return ResponseEntity.created(new URI("/api/family-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /family-members} : Updates an existing familyMember.
     *
     * @param familyMember the familyMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyMember,
     * or with status {@code 400 (Bad Request)} if the familyMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/family-members")
    public ResponseEntity<FamilyMember> updateFamilyMember(@Valid @RequestBody FamilyMember familyMember) throws URISyntaxException {
        log.debug("REST request to update FamilyMember : {}", familyMember);
        if (familyMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyMember result = familyMemberRepository.save(familyMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /family-members} : get all the familyMembers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyMembers in body.
     */
    @GetMapping("/family-members")
    public ResponseEntity<List<FamilyMember>> getAllFamilyMembers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of FamilyMembers");
        Page<FamilyMember> page;
        if (eagerload) {
            page = familyMemberRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = familyMemberRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /family-members/:id} : get the "id" familyMember.
     *
     * @param id the id of the familyMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/family-members/{id}")
    public ResponseEntity<FamilyMember> getFamilyMember(@PathVariable Long id) {
        log.debug("REST request to get FamilyMember : {}", id);
        Optional<FamilyMember> familyMember = familyMemberRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(familyMember);
    }

    /**
     * {@code DELETE  /family-members/:id} : delete the "id" familyMember.
     *
     * @param id the id of the familyMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/family-members/{id}")
    public ResponseEntity<Void> deleteFamilyMember(@PathVariable Long id) {
        log.debug("REST request to delete FamilyMember : {}", id);
        familyMemberRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
