package com.clinique.soap.services;

import com.clinique.soap.model.Medecin;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface MedecinService {
    @WebMethod
    Medecin createMedecin(String nom, String specialite, String email, String telephone);

    @WebMethod
    Medecin getMedecinById(int id);

    @WebMethod
    List<Medecin> getAllMedecins();

    @WebMethod
    boolean updateMedecin(int id, String nom, String specialite, String email, String telephone);

    @WebMethod
    boolean deleteMedecin(int id);
}
