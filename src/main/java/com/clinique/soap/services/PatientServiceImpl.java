package com.clinique.soap.services;

import com.clinique.soap.config.DatabaseConfig;
import com.clinique.soap.model.Patient;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.clinique.soap.services.PatientService")
public class PatientServiceImpl implements PatientService {

    @WebMethod
    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT id, nom, prenom, date_naissance, email, telephone FROM patients";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setDateNaissance(rs.getDate("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setTelephone(rs.getString("telephone"));
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @WebMethod
    public Patient createPatient(
            @WebParam(name = "nom") String nom,
            @WebParam(name = "prenom") String prenom,
            @WebParam(name = "dateNaissance") String dateNaissance,
            @WebParam(name = "email") String email,
            @WebParam(name = "telephone") String telephone)
    {
        System.out.println("nom="+nom+", prenom="+prenom+", dateNaissance="+dateNaissance+", email="+email+", telephone="+telephone);

        if (dateNaissance == null || dateNaissance.trim().isEmpty()) {
            System.out.println("dateNaissance est null ou vide !");
            return null; // ou lancez une exception
        }

        // Convertir dateNaissance en java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.parse(dateNaissance));

        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO patients (nom, prenom, date_naissance, email, telephone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setDate(3, sqlDate);
            stmt.setString(4, email);
            stmt.setString(5, telephone);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    Patient p = new Patient();
                    p.setId(generatedId);
                    p.setNom(nom);
                    p.setPrenom(prenom);
                    p.setDateNaissance(sqlDate);
                    p.setEmail(email);
                    p.setTelephone(telephone);
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
