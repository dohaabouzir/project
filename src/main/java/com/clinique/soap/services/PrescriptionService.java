package com.clinique.soap.services;

import com.clinique.soap.model.Prescription;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface PrescriptionService {
    @WebMethod
    Prescription createPrescription(int dossierId, String contenu);

    @WebMethod
    Prescription getPrescriptionById(int id);

    @WebMethod
    List<Prescription> getPrescriptionsByDossier(int dossierId);

    @WebMethod
    boolean deletePrescription(int id);
}
