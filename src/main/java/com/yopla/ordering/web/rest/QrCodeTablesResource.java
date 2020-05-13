package com.yopla.ordering.web.rest;

import com.yopla.ordering.domain.QrCodeTables;
import com.yopla.ordering.repository.QrCodeTablesRepository;
import com.yopla.ordering.repository.search.QrCodeTablesSearchRepository;
import com.yopla.ordering.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.yopla.ordering.domain.QrCodeTables}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QrCodeTablesResource {

    private final Logger log = LoggerFactory.getLogger(QrCodeTablesResource.class);

    private static final String ENTITY_NAME = "qrCodeTables";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QrCodeTablesRepository qrCodeTablesRepository;

    private final QrCodeTablesSearchRepository qrCodeTablesSearchRepository;

    public QrCodeTablesResource(QrCodeTablesRepository qrCodeTablesRepository, QrCodeTablesSearchRepository qrCodeTablesSearchRepository) {
        this.qrCodeTablesRepository = qrCodeTablesRepository;
        this.qrCodeTablesSearchRepository = qrCodeTablesSearchRepository;
    }

    /**
     * {@code POST  /qr-code-tables} : Create a new qrCodeTables.
     *
     * @param qrCodeTables the qrCodeTables to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qrCodeTables, or with status {@code 400 (Bad Request)} if the qrCodeTables has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qr-code-tables")
    public ResponseEntity<QrCodeTables> createQrCodeTables(@RequestBody QrCodeTables qrCodeTables) throws URISyntaxException {
        log.debug("REST request to save QrCodeTables : {}", qrCodeTables);
        if (qrCodeTables.getId() != null) {
            throw new BadRequestAlertException("A new qrCodeTables cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QrCodeTables result = qrCodeTablesRepository.save(qrCodeTables);
        qrCodeTablesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/qr-code-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qr-code-tables} : Updates an existing qrCodeTables.
     *
     * @param qrCodeTables the qrCodeTables to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qrCodeTables,
     * or with status {@code 400 (Bad Request)} if the qrCodeTables is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qrCodeTables couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qr-code-tables")
    public ResponseEntity<QrCodeTables> updateQrCodeTables(@RequestBody QrCodeTables qrCodeTables) throws URISyntaxException {
        log.debug("REST request to update QrCodeTables : {}", qrCodeTables);
        if (qrCodeTables.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QrCodeTables result = qrCodeTablesRepository.save(qrCodeTables);
        qrCodeTablesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qrCodeTables.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /qr-code-tables} : get all the qrCodeTables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qrCodeTables in body.
     */
    @GetMapping("/qr-code-tables")
    public List<QrCodeTables> getAllQrCodeTables() {
        log.debug("REST request to get all QrCodeTables");
        return qrCodeTablesRepository.findAll();
    }

    /**
     * {@code GET  /qr-code-tables/:id} : get the "id" qrCodeTables.
     *
     * @param id the id of the qrCodeTables to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qrCodeTables, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qr-code-tables/{id}")
    public ResponseEntity<QrCodeTables> getQrCodeTables(@PathVariable Long id) {
        log.debug("REST request to get QrCodeTables : {}", id);
        Optional<QrCodeTables> qrCodeTables = qrCodeTablesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qrCodeTables);
    }

    /**
     * {@code DELETE  /qr-code-tables/:id} : delete the "id" qrCodeTables.
     *
     * @param id the id of the qrCodeTables to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qr-code-tables/{id}")
    public ResponseEntity<Void> deleteQrCodeTables(@PathVariable Long id) {
        log.debug("REST request to delete QrCodeTables : {}", id);
        qrCodeTablesRepository.deleteById(id);
        qrCodeTablesSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/qr-code-tables?query=:query} : search for the qrCodeTables corresponding
     * to the query.
     *
     * @param query the query of the qrCodeTables search.
     * @return the result of the search.
     */
    @GetMapping("/_search/qr-code-tables")
    public List<QrCodeTables> searchQrCodeTables(@RequestParam String query) {
        log.debug("REST request to search QrCodeTables for query {}", query);
        return StreamSupport
            .stream(qrCodeTablesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
