package com.clinique.soap.services;

import com.clinique.soap.model.Patient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface PatientService {
    @WebMethod
    List<Patient> getAllPatients();

    @WebMethod
    Patient createPatient(
            @WebParam(name="nom") String nom,
            @WebParam(name="prenom") String prenom,
            @WebParam(name="dateNaissance") String dateNaissance,
            @WebParam(name="email") String email,
            @WebParam(name="telephone") String telephone
    );
}

