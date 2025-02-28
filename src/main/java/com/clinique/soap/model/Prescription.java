package com.clinique.soap.model;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlRootElement(name = "Prescription")
@XmlAccessorType(XmlAccessType.FIELD)
public class Prescription {
    @XmlElement
    private int id;
    @XmlElement
    private int dossierId;
    @XmlElement
    private String contenu;
    @XmlElement
    private Date datePrescription; // Remplacé Timestamp par Date

    public Prescription() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getDossierId() {
        return dossierId;
    }
    public void setDossierId(int dossierId) {
        this.dossierId = dossierId;
    }

    public String getContenu() {
        return contenu;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatePrescription() {
        return datePrescription;
    }
    public void setDatePrescription(Date datePrescription) {
        this.datePrescription = datePrescription;
    }
}
