package com.yopla.ordering.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.yopla.ordering.web.rest.TestUtil;

public class ArticlesOptionGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticlesOptionGroups.class);
        ArticlesOptionGroups articlesOptionGroups1 = new ArticlesOptionGroups();
        articlesOptionGroups1.setId(1L);
        ArticlesOptionGroups articlesOptionGroups2 = new ArticlesOptionGroups();
        articlesOptionGroups2.setId(articlesOptionGroups1.getId());
        assertThat(articlesOptionGroups1).isEqualTo(articlesOptionGroups2);
        articlesOptionGroups2.setId(2L);
        assertThat(articlesOptionGroups1).isNotEqualTo(articlesOptionGroups2);
        articlesOptionGroups1.setId(null);
        assertThat(articlesOptionGroups1).isNotEqualTo(articlesOptionGroups2);
    }
}
