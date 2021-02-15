package it.unicam.doit.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Valutazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank
    private String punteggio;

    @Column
    private String note;

    @Column
    private Long idValutatore;

    @Column
    private Long idCandidato;

    @Column
    private Long idRuoloProgetto;


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


    public String getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(String punteggio) {
        this.punteggio = punteggio;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getIdValutatore() {
        return idValutatore;
    }

    public void setIdValutatore(Long idValutatore) {
        this.idValutatore = idValutatore;
    }

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Long getIdRuoloProgetto() {
        return idRuoloProgetto;
    }

    public void setIdRuoloProgetto(Long idRuoloProgetto) {
        this.idRuoloProgetto = idRuoloProgetto;
    }
}
