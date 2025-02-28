package com.clinique.soap.services;

import com.clinique.soap.config.DatabaseConfig;
import com.clinique.soap.model.Prescription;

import javax.jws.WebService;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.clinique.soap.services.PrescriptionService")
public class PrescriptionServiceImpl implements PrescriptionService {

    @Override
    public Prescription createPrescription(int dossierId, String contenu) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO prescriptions (dossier_id, contenu, date_prescription) VALUES (?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, dossierId);
            stmt.setString(2, contenu);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Prescription p = new Prescription();
                p.setId(rs.getInt(1));
                p.setDossierId(dossierId);
                p.setContenu(contenu);
                p.setDatePrescription(new Timestamp(System.currentTimeMillis()));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prescription getPrescriptionById(int id) {
        Prescription p = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT id, dossier_id, contenu, date_prescription FROM prescriptions WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                p = new Prescription();
                p.setId(rs.getInt("id"));
                p.setDossierId(rs.getInt("dossier_id"));
                p.setContenu(rs.getString("contenu"));
                p.setDatePrescription(rs.getTimestamp("date_prescription"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public List<Prescription> getPrescriptionsByDossier(int dossierId) {
        List<Prescription> liste = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT id, dossier_id, contenu, date_prescription FROM prescriptions WHERE dossier_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, dossierId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prescription p = new Prescription();
                p.setId(rs.getInt("id"));
                p.setDossierId(rs.getInt("dossier_id"));
                p.setContenu(rs.getString("contenu"));
                p.setDatePrescription(rs.getTimestamp("date_prescription"));
                liste.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public boolean deletePrescription(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "DELETE FROM prescriptions WHERE id = ?";
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
