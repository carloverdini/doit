package it.unicam.doit.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Progetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank
    private String titolo;

    @Column
    private String descrizione;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataPubblicazione;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataScadenza;

    @Column
    private String stato;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UTENTE_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente proponenteProgetto;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }
    public void setDataPubblicazione(Date dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }
    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }


    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Utente getProponenteProgetto() {
        return proponenteProgetto;
    }

    public void setProponenteProgetto(Utente proponenteProgetto) {
        this.proponenteProgetto = proponenteProgetto;
    }
}
