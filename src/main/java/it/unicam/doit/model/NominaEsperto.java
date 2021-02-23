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
public class NominaEsperto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ESPERTO_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente esperto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RUOLO_PROGETTO_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RuoloProgetto ruoloProgetto;


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

    public Utente getEsperto() {
        return esperto;
    }

    public void setEsperto(Utente esperto) {
        this.esperto = esperto;
    }

    public RuoloProgetto getRuoloProgetto() {
        return ruoloProgetto;
    }

    public void setRuoloProgetto(RuoloProgetto ruoloProgetto) {
        this.ruoloProgetto = ruoloProgetto;
    }

}
