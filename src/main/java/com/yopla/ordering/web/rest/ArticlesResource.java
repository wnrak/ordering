package com.yopla.ordering.web.rest;

import com.yopla.ordering.domain.Articles;
import com.yopla.ordering.repository.ArticlesRepository;
import com.yopla.ordering.repository.search.ArticlesSearchRepository;
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
 * REST controller for managing {@link com.yopla.ordering.domain.Articles}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArticlesResource {

    private final Logger log = LoggerFactory.getLogger(ArticlesResource.class);

    private static final String ENTITY_NAME = "articles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticlesRepository articlesRepository;

    private final ArticlesSearchRepository articlesSearchRepository;

    public ArticlesResource(ArticlesRepository articlesRepository, ArticlesSearchRepository articlesSearchRepository) {
        this.articlesRepository = articlesRepository;
        this.articlesSearchRepository = articlesSearchRepository;
    }

    /**
     * {@code POST  /articles} : Create a new articles.
     *
     * @param articles the articles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articles, or with status {@code 400 (Bad Request)} if the articles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articles")
    public ResponseEntity<Articles> createArticles(@Valid @RequestBody Articles articles) throws URISyntaxException {
        log.debug("REST request to save Articles : {}", articles);
        if (articles.getId() != null) {
            throw new BadRequestAlertException("A new articles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Articles result = articlesRepository.save(articles);
        articlesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articles} : Updates an existing articles.
     *
     * @param articles the articles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articles,
     * or with status {@code 400 (Bad Request)} if the articles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articles")
    public ResponseEntity<Articles> updateArticles(@Valid @RequestBody Articles articles) throws URISyntaxException {
        log.debug("REST request to update Articles : {}", articles);
        if (articles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Articles result = articlesRepository.save(articles);
        articlesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articles.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articles} : get all the articles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("/articles")
    public List<Articles> getAllArticles() {
        log.debug("REST request to get all Articles");
        return articlesRepository.findAll();
    }

    /**
     * {@code GET  /articles/:id} : get the "id" articles.
     *
     * @param id the id of the articles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articles/{id}")
    public ResponseEntity<Articles> getArticles(@PathVariable Long id) {
        log.debug("REST request to get Articles : {}", id);
        Optional<Articles> articles = articlesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(articles);
    }

    /**
     * {@code DELETE  /articles/:id} : delete the "id" articles.
     *
     * @param id the id of the articles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticles(@PathVariable Long id) {
        log.debug("REST request to delete Articles : {}", id);
        articlesRepository.deleteById(id);
        articlesSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/articles?query=:query} : search for the articles corresponding
     * to the query.
     *
     * @param query the query of the articles search.
     * @return the result of the search.
     */
    @GetMapping("/_search/articles")
    public List<Articles> searchArticles(@RequestParam String query) {
        log.debug("REST request to search Articles for query {}", query);
        return StreamSupport
            .stream(articlesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
