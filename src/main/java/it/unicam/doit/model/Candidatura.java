package it.unicam.doit.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @JoinColumn(name = "CANDIDATO_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente candidato;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RUOLO_PROGETTO_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RuoloProgetto ruoloProgetto;


    @NotBlank
    @Column
    private String stato;

    /*
     * STATO CANDIDATURA
     * qualsiasi utente
     * candidatura richiesta-> RQSTD
     *
     * candidato
     * candidatura attivata-> ACTVD
     * candidatura ritirata -> CNCLD
     *
     * proponente o esperto
     * candidatura confermata-> CFRMD
     * candidatura scartata -> RJCTD
     * */

    public static final String RQSTD = "RQSTD";
    public static final String ACTVD = "ACTVD";
    public static final String CNCLD = "CNCLD";
    public static final String CFRMD = "CFRMD";
    public static final String RJCTD = "RJCTD";



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

    public String getPresentazione() {
        return presentazione;
    }

    public void setPresentazione(String presentazione) {
        this.presentazione = presentazione;
    }

    public Utente getCandidato() {
        return candidato;
    }

    public void setCandidato(Utente candidato) {
        this.candidato = candidato;
    }


    public RuoloProgetto getRuoloProgetto() {
        return ruoloProgetto;
    }

    public void setRuoloProgetto(RuoloProgetto ruoloProgetto) {
        this.ruoloProgetto = ruoloProgetto;
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

}
