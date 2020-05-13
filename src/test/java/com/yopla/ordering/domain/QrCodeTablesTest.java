package com.yopla.ordering.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.yopla.ordering.web.rest.TestUtil;

public class QrCodeTablesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QrCodeTables.class);
        QrCodeTables qrCodeTables1 = new QrCodeTables();
        qrCodeTables1.setId(1L);
        QrCodeTables qrCodeTables2 = new QrCodeTables();
        qrCodeTables2.setId(qrCodeTables1.getId());
        assertThat(qrCodeTables1).isEqualTo(qrCodeTables2);
        qrCodeTables2.setId(2L);
        assertThat(qrCodeTables1).isNotEqualTo(qrCodeTables2);
        qrCodeTables1.setId(null);
        assertThat(qrCodeTables1).isNotEqualTo(qrCodeTables2);
    }
}
