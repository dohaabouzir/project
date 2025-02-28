package com.clinique.soap.model;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlRootElement(name = "DossierMedical")
@XmlAccessorType(XmlAccessType.FIELD)
public class DossierMedical {
    @XmlElement
    private int id;
    @XmlElement(name = "patientId")
    private int patientId;
    @XmlElement
    private String contenu;
    @XmlElement
    private Date dateCreation; // Remplacé Timestamp par Date

    public DossierMedical() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getContenu() {
        return contenu;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
