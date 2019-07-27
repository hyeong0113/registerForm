package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.RegisterMember;
import com.mycompany.myapp.repository.RegisterMemberRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RegisterMember}.
 */
@RestController
@RequestMapping("/api")
public class RegisterMemberResource {

    private final Logger log = LoggerFactory.getLogger(RegisterMemberResource.class);

    private static final String ENTITY_NAME = "registerMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegisterMemberRepository registerMemberRepository;

    public RegisterMemberResource(RegisterMemberRepository registerMemberRepository) {
        this.registerMemberRepository = registerMemberRepository;
    }

    /**
     * {@code POST  /register-members} : Create a new registerMember.
     *
     * @param registerMember the registerMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registerMember, or with status {@code 400 (Bad Request)} if the registerMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/register-members")
    public ResponseEntity<RegisterMember> createRegisterMember(@Valid @RequestBody RegisterMember registerMember) throws URISyntaxException {
        log.debug("REST request to save RegisterMember : {}", registerMember);
        if (registerMember.getId() != null) {
            throw new BadRequestAlertException("A new registerMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegisterMember result = registerMemberRepository.save(registerMember);
        return ResponseEntity.created(new URI("/api/register-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /register-members} : Updates an existing registerMember.
     *
     * @param registerMember the registerMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registerMember,
     * or with status {@code 400 (Bad Request)} if the registerMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registerMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/register-members")
    public ResponseEntity<RegisterMember> updateRegisterMember(@Valid @RequestBody RegisterMember registerMember) throws URISyntaxException {
        log.debug("REST request to update RegisterMember : {}", registerMember);
        if (registerMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegisterMember result = registerMemberRepository.save(registerMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registerMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /register-members} : get all the registerMembers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registerMembers in body.
     */
    @GetMapping("/register-members")
    public ResponseEntity<List<RegisterMember>> getAllRegisterMembers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RegisterMembers");
        Page<RegisterMember> page;
        if (eagerload) {
            page = registerMemberRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = registerMemberRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /register-members/:id} : get the "id" registerMember.
     *
     * @param id the id of the registerMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registerMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/register-members/{id}")
    public ResponseEntity<RegisterMember> getRegisterMember(@PathVariable Long id) {
        log.debug("REST request to get RegisterMember : {}", id);
        Optional<RegisterMember> registerMember = registerMemberRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(registerMember);
    }

    /**
     * {@code DELETE  /register-members/:id} : delete the "id" registerMember.
     *
     * @param id the id of the registerMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/register-members/{id}")
    public ResponseEntity<Void> deleteRegisterMember(@PathVariable Long id) {
        log.debug("REST request to delete RegisterMember : {}", id);
        registerMemberRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
