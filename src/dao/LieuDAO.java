package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Lieu;

public class LieuDAO {
    private final String TB_NAME = "lieu";
    private final String TB_ID_LIEU = "id_lieu";
    private final String TB_NOM = "nom";
    private final String TB_ADRESSE = "adresse";
    private final String TB_VILLE = "ville";
    private final String TB_CODE_POSTAL = "code_postal";

    private Connection conn;

    public LieuDAO(Connection con) {
        this.conn = con;
    }

    public boolean addLieu(Lieu lieu) {
        String sql = "INSERT INTO " + TB_NAME + " (" + TB_NOM + ", " + TB_ADRESSE + ", " + TB_VILLE + ", "
                + TB_CODE_POSTAL + ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lieu.getNom());
            stmt.setString(2, lieu.getAdresse());
            stmt.setString(3, lieu.getVille());
            stmt.setString(4, lieu.getCodePostal());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLieu(Lieu lieu) {
        String sql = "UPDATE " + TB_NAME + " SET " + TB_NOM + " = ?, " + TB_ADRESSE + " = ?, " + TB_VILLE + " = ?, "
                + TB_CODE_POSTAL + " = ? WHERE " + TB_ID_LIEU + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lieu.getNom());
            stmt.setString(2, lieu.getAdresse());
            stmt.setString(3, lieu.getVille());
            stmt.setString(4, lieu.getCodePostal());
            stmt.setInt(5, lieu.getIdLieu());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Lieu getLieuById(int idLieu) {
        String sql = "SELECT * FROM " + TB_NAME + " WHERE " + TB_ID_LIEU + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Lieu(
                        rs.getInt(TB_ID_LIEU),
                        rs.getString(TB_NOM),
                        rs.getString(TB_ADRESSE),
                        rs.getString(TB_VILLE),
                        rs.getString(TB_CODE_POSTAL));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Lieu> getLieuxByName(String nom) {
        List<Lieu> lieux = new ArrayList<>();
        String sql = "SELECT * FROM lieu WHERE nom LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nom + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Lieu lieu = new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("ville"),
                        rs.getString("code_postal")
                    );
                    lieux.add(lieu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lieux;
    }

    public boolean deleteLieu(int idLieu) {
        String sql = "DELETE FROM " + TB_NAME + " WHERE " + TB_ID_LIEU + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLieu);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllVilles() {
        List<String> villes = new ArrayList<>();
        String sql = "SELECT DISTINCT " + TB_VILLE + " FROM " + TB_NAME;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                villes.add(rs.getString(TB_VILLE));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villes;
    }

    public List<String> getAllCodePostaux() {
        List<String> codePostaux = new ArrayList<>();
        String sql = "SELECT DISTINCT " + TB_CODE_POSTAL + " FROM " + TB_NAME;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                codePostaux.add(rs.getString(TB_CODE_POSTAL));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codePostaux;
    }

    public boolean lieuExiste(String nom) {
        String sql = "SELECT COUNT(*) AS count FROM " + TB_NAME + " WHERE " + TB_NOM + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") >= 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean lieuExiste(int idLieu) {
        String sql = "SELECT COUNT(*) AS count FROM " + TB_NAME + " WHERE " + TB_ID_LIEU + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") >= 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Lieu> getAllLieux() {
        List<Lieu> lieux = new ArrayList<>();
        String sql = "SELECT * FROM " + TB_NAME;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Lieu lieu = new Lieu(
                    rs.getInt(TB_ID_LIEU),
                    rs.getString(TB_NOM),
                    rs.getString(TB_ADRESSE),
                    rs.getString(TB_VILLE),
                    rs.getString(TB_CODE_POSTAL)
                );
                lieux.add(lieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lieux;
    }
}
