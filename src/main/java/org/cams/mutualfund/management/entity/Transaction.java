package org.cams.mutualfund.management.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    private String type;

    @ManyToOne
    @JoinColumn(name = "fund_id")
    private MutualFund fund;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date doneOn;

    private long units;

    private long value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public MutualFund getFund() {
        return fund;
    }

    public void setFund(MutualFund fund) {
        this.fund = fund;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDoneOn() {
        return doneOn;
    }

    public void setDoneOn(Date doneOn) {
        this.doneOn = doneOn;
    }

    
    public long getUnits() {
        return units;
    }

    public void setUnits(long units) {
        this.units = units;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }   
}