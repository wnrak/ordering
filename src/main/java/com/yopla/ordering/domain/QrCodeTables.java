package com.yopla.ordering.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A QrCodeTables.
 */
@Entity
@Table(name = "qr_code_tables")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "qrcodetables")
public class QrCodeTables implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "table_number")
    private String tableNumber;

    @Column(name = "qr_code")
    private String qrCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public QrCodeTables tableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getQrCode() {
        return qrCode;
    }

    public QrCodeTables qrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QrCodeTables)) {
            return false;
        }
        return id != null && id.equals(((QrCodeTables) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QrCodeTables{" +
            "id=" + getId() +
            ", tableNumber='" + getTableNumber() + "'" +
            ", qrCode='" + getQrCode() + "'" +
            "}";
    }
}
