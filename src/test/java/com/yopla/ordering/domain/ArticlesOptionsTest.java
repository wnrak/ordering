package com.yopla.ordering.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.yopla.ordering.web.rest.TestUtil;

public class ArticlesOptionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticlesOptions.class);
        ArticlesOptions articlesOptions1 = new ArticlesOptions();
        articlesOptions1.setId(1L);
        ArticlesOptions articlesOptions2 = new ArticlesOptions();
        articlesOptions2.setId(articlesOptions1.getId());
        assertThat(articlesOptions1).isEqualTo(articlesOptions2);
        articlesOptions2.setId(2L);
        assertThat(articlesOptions1).isNotEqualTo(articlesOptions2);
        articlesOptions1.setId(null);
        assertThat(articlesOptions1).isNotEqualTo(articlesOptions2);
    }
}
