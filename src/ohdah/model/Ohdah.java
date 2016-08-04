/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohdah.model;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Omar
 */
public class Ohdah {
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty amount;
    private final StringProperty reason;
    private final ObjectProperty<LocalDate> ohdahDate;

    /**
     *
     */
    public Ohdah() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.amount = new SimpleDoubleProperty();
        this.reason = new SimpleStringProperty();
        this.ohdahDate = new SimpleObjectProperty<>();
    }

    /**
     *
     * @param id
     * @param name
     * @param amount
     * @param reason
     * @param ohdahDate
     */
    public Ohdah(int id, String name, double amount, String reason, LocalDate ohdahDate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleDoubleProperty(amount);
        this.reason = new SimpleStringProperty(reason);
        this.ohdahDate = new SimpleObjectProperty<>(ohdahDate);
    }

    /**
     * @return the id
     */
    public IntegerProperty idProperty() {
        return id;
    }
    
    /**
     * 
     * @return id
     */
    public int getId(){
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * @return the name property
     */
    public StringProperty nameProperty() {
        return name;
    }
    
    /**
     * 
     * @return name
     */
    public String getName(){
        return name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return the amount property
     */
    public DoubleProperty amountProperty() {
        return amount;
    }
    
    /**
     * 
     * @return amount
     */
    public double getAmount(){
        return amount.get();
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    /**
     * @return the reason property
     */
    public StringProperty reasonProperty() {
        return reason;
    }
    
    /**
     *
     * @return
     */
    public String getReason(){
        return reason.get();
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason.set(reason);
    }
    
     /**
     * @return the ohdahDate property
     */
    public ObjectProperty<LocalDate> DateProperty() {
        return ohdahDate;
    }
    
    /**
     * 
     * @return ohdah date
     */
    public LocalDate getDate(){
        return (ohdahDate.getValue() != null)? ohdahDate.get() : LocalDate.now();
    }

    /**
     * @param ohdahDate the ohdahDate to set
     */
    public void setDate(LocalDate ohdahDate) {
        this.ohdahDate.set(ohdahDate);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Ohdah) ? (this.getId() == ((Ohdah)obj).getId()) : false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getId());
        return hash;
    }  
}
