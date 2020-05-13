package com.yopla.ordering.web.rest;

import com.yopla.ordering.domain.ArticlesOptionGroups;
import com.yopla.ordering.repository.ArticlesOptionGroupsRepository;
import com.yopla.ordering.repository.search.ArticlesOptionGroupsSearchRepository;
import com.yopla.ordering.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.yopla.ordering.domain.ArticlesOptionGroups}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArticlesOptionGroupsResource {

    private final Logger log = LoggerFactory.getLogger(ArticlesOptionGroupsResource.class);

    private static final String ENTITY_NAME = "articlesOptionGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticlesOptionGroupsRepository articlesOptionGroupsRepository;

    private final ArticlesOptionGroupsSearchRepository articlesOptionGroupsSearchRepository;

    public ArticlesOptionGroupsResource(ArticlesOptionGroupsRepository articlesOptionGroupsRepository, ArticlesOptionGroupsSearchRepository articlesOptionGroupsSearchRepository) {
        this.articlesOptionGroupsRepository = articlesOptionGroupsRepository;
        this.articlesOptionGroupsSearchRepository = articlesOptionGroupsSearchRepository;
    }

    /**
     * {@code POST  /articles-option-groups} : Create a new articlesOptionGroups.
     *
     * @param articlesOptionGroups the articlesOptionGroups to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articlesOptionGroups, or with status {@code 400 (Bad Request)} if the articlesOptionGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articles-option-groups")
    public ResponseEntity<ArticlesOptionGroups> createArticlesOptionGroups(@Valid @RequestBody ArticlesOptionGroups articlesOptionGroups) throws URISyntaxException {
        log.debug("REST request to save ArticlesOptionGroups : {}", articlesOptionGroups);
        if (articlesOptionGroups.getId() != null) {
            throw new BadRequestAlertException("A new articlesOptionGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticlesOptionGroups result = articlesOptionGroupsRepository.save(articlesOptionGroups);
        articlesOptionGroupsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/articles-option-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articles-option-groups} : Updates an existing articlesOptionGroups.
     *
     * @param articlesOptionGroups the articlesOptionGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articlesOptionGroups,
     * or with status {@code 400 (Bad Request)} if the articlesOptionGroups is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articlesOptionGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articles-option-groups")
    public ResponseEntity<ArticlesOptionGroups> updateArticlesOptionGroups(@Valid @RequestBody ArticlesOptionGroups articlesOptionGroups) throws URISyntaxException {
        log.debug("REST request to update ArticlesOptionGroups : {}", articlesOptionGroups);
        if (articlesOptionGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticlesOptionGroups result = articlesOptionGroupsRepository.save(articlesOptionGroups);
        articlesOptionGroupsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articlesOptionGroups.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articles-option-groups} : get all the articlesOptionGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articlesOptionGroups in body.
     */
    @GetMapping("/articles-option-groups")
    public List<ArticlesOptionGroups> getAllArticlesOptionGroups() {
        log.debug("REST request to get all ArticlesOptionGroups");
        return articlesOptionGroupsRepository.findAll();
    }

    /**
     * {@code GET  /articles-option-groups/:id} : get the "id" articlesOptionGroups.
     *
     * @param id the id of the articlesOptionGroups to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articlesOptionGroups, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articles-option-groups/{id}")
    public ResponseEntity<ArticlesOptionGroups> getArticlesOptionGroups(@PathVariable Long id) {
        log.debug("REST request to get ArticlesOptionGroups : {}", id);
        Optional<ArticlesOptionGroups> articlesOptionGroups = articlesOptionGroupsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(articlesOptionGroups);
    }

    /**
     * {@code DELETE  /articles-option-groups/:id} : delete the "id" articlesOptionGroups.
     *
     * @param id the id of the articlesOptionGroups to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articles-option-groups/{id}")
    public ResponseEntity<Void> deleteArticlesOptionGroups(@PathVariable Long id) {
        log.debug("REST request to delete ArticlesOptionGroups : {}", id);
        articlesOptionGroupsRepository.deleteById(id);
        articlesOptionGroupsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/articles-option-groups?query=:query} : search for the articlesOptionGroups corresponding
     * to the query.
     *
     * @param query the query of the articlesOptionGroups search.
     * @return the result of the search.
     */
    @GetMapping("/_search/articles-option-groups")
    public List<ArticlesOptionGroups> searchArticlesOptionGroups(@RequestParam String query) {
        log.debug("REST request to search ArticlesOptionGroups for query {}", query);
        return StreamSupport
            .stream(articlesOptionGroupsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
