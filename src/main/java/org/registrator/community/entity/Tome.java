package org.registrator.community.entity;


import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "tomes")
public class Tome implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "tome_id")
    @GeneratedValue
    private Integer tomeId;

    @Column(name = "identifier", unique = true, nullable = false)
    private String identifier;

    @ManyToOne
    @JoinColumn(name = "registrator_id", nullable = false)
    private User registrator;

    public Tome() {
        
    }
    
    public Tome(User registrator, String identifier) {
        this.identifier = identifier;
        this.registrator = registrator;
    }

    public Integer getTomeId() {
        return tomeId;
    }

    public void setTomeId(Integer tomeId) {
        this.tomeId = tomeId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public User getRegistrator() {
        return registrator;
    }

    public void setRegistrator(User registrator) {
        this.registrator = registrator;
    }
}
