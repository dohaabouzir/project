package com.clinique.soap.services;

import com.clinique.soap.model.DossierMedical;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface DossierMedicalService {
    @WebMethod
    DossierMedical createDossierMedical(int patientId, String contenu);

    @WebMethod
    DossierMedical getDossierMedicalById(int id);

    @WebMethod
    List<DossierMedical> getAllDossiersByPatient(int patientId);

    @WebMethod
    boolean updateDossierMedical(int id, String nouveauContenu);

    @WebMethod
    boolean deleteDossierMedical(int id);
}

