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
public class Valutazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Boolean esito;

    @Column
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VALUTATORE_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente valutatore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CANDIDATURA_ID", referencedColumnName="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Candidatura candidatura;

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


    public Boolean getEsito() {
        return esito;
    }

    public void setEsito(Boolean esito) {
        this.esito = esito;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Utente getValutatore() {
        return valutatore;
    }

    public void setValutatore(Utente valutatore) {
        this.valutatore = valutatore;
    }

    public Candidatura getCandidatura() {
        return candidatura;
    }

    public void setCandidatura(Candidatura candidatura) {
        this.candidatura = candidatura;
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
