package it.unicam.doit.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String presentazione;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UTENTE_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente utente;

    @Column
    private Long IdRuoloProgetto;

    @Column
    private String stato;


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

    public Long getIdRuoloProgetto() {
        return IdRuoloProgetto;
    }

    public void setIdRuoloProgetto(Long idRuoloProgetto) {
        IdRuoloProgetto = idRuoloProgetto;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getPresentazione() {
        return presentazione;
    }

    public void setPresentazione(String presentazione) {
        this.presentazione = presentazione;
    }
}
