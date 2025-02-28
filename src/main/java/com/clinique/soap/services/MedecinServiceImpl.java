package com.clinique.soap.services;

import com.clinique.soap.config.DatabaseConfig;
import com.clinique.soap.model.Medecin;

import javax.jws.WebService;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.clinique.soap.services.MedecinService")
public class MedecinServiceImpl implements MedecinService {

    @Override
    public Medecin createMedecin(String nom, String specialite, String email, String telephone) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO medecins (nom, specialite, email, telephone) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nom);
            stmt.setString(2, specialite);
            stmt.setString(3, email);
            stmt.setString(4, telephone);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Medecin m = new Medecin();
                m.setId(rs.getInt(1));
                m.setNom(nom);
                m.setSpecialite(specialite);
                m.setEmail(email);
                m.setTelephone(telephone);
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medecin getMedecinById(int id) {
        Medecin m = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT id, nom, specialite, email, telephone FROM medecins WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = new Medecin();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setSpecialite(rs.getString("specialite"));
                m.setEmail(rs.getString("email"));
                m.setTelephone(rs.getString("telephone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public List<Medecin> getAllMedecins() {
        List<Medecin> liste = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT id, nom, specialite, email, telephone FROM medecins";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medecin m = new Medecin();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setSpecialite(rs.getString("specialite"));
                m.setEmail(rs.getString("email"));
                m.setTelephone(rs.getString("telephone"));
                liste.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public boolean updateMedecin(int id, String nom, String specialite, String email, String telephone) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "UPDATE medecins SET nom=?, specialite=?, email=?, telephone=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, specialite);
            stmt.setString(3, email);
            stmt.setString(4, telephone);
            stmt.setInt(5, id);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMedecin(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "DELETE FROM medecins WHERE id=?";
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
