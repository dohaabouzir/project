package com.clinique.soap.services;

import com.clinique.soap.config.DatabaseConfig;
import com.clinique.soap.model.DossierMedical;

import javax.jws.WebService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.clinique.soap.services.DossierMedicalService")
public class DossierMedicalServiceImpl implements DossierMedicalService {

    @Override
    public DossierMedical createDossierMedical(int patientId, String contenu) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO dossiers_medicaux (patient_id, contenu, date_creation) VALUES (?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, patientId);
            stmt.setString(2, contenu);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                DossierMedical dm = new DossierMedical();
                dm.setId(rs.getInt(1));
                dm.setPatientId(patientId);
                dm.setContenu(contenu);
                dm.setDateCreation(new Timestamp(System.currentTimeMillis()));
                return dm;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DossierMedical getDossierMedicalById(int id) {
        DossierMedical dm = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT id, patient_id, contenu, date_creation FROM dossiers_medicaux WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dm = new DossierMedical();
                dm.setId(rs.getInt("id"));
                dm.setPatientId(rs.getInt("patient_id"));
                dm.setContenu(rs.getString("contenu"));
                dm.setDateCreation(rs.getTimestamp("date_creation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dm;
    }

    @Override
    public List<DossierMedical> getAllDossiersByPatient(int patientId) {
        List<DossierMedical> liste = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT id, patient_id, contenu, date_creation FROM dossiers_medicaux WHERE patient_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DossierMedical dm = new DossierMedical();
                dm.setId(rs.getInt("id"));
                dm.setPatientId(rs.getInt("patient_id"));
                dm.setContenu(rs.getString("contenu"));
                dm.setDateCreation(rs.getTimestamp("date_creation"));
                liste.add(dm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public boolean updateDossierMedical(int id, String nouveauContenu) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "UPDATE dossiers_medicaux SET contenu = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nouveauContenu);
            stmt.setInt(2, id);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDossierMedical(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "DELETE FROM dossiers_medicaux WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            return deleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

