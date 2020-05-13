package com.yopla.ordering.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.yopla.ordering.web.rest.TestUtil;

public class ArticlesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Articles.class);
        Articles articles1 = new Articles();
        articles1.setId(1L);
        Articles articles2 = new Articles();
        articles2.setId(articles1.getId());
        assertThat(articles1).isEqualTo(articles2);
        articles2.setId(2L);
        assertThat(articles1).isNotEqualTo(articles2);
        articles1.setId(null);
        assertThat(articles1).isNotEqualTo(articles2);
    }
}
