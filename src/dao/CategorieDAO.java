package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Categorie;

public class CategorieDAO {


    private final String TB_NAME = "categorie";
    private final String TB_ID_CAT = "id_cat";
    private final String TB_NOM = "nom";

    private Connection conn;

    public CategorieDAO(Connection con) {
        this.conn = con;
    }

    public boolean addCategorie(Categorie categorie) {
        String sql = "INSERT INTO " + TB_NAME + " (" + TB_NOM + ") VALUES (?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categorie.getNom());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean categorieExist(String nom) {
        String sql = "SELECT COUNT(*) AS count FROM " + TB_NAME + " WHERE " + TB_NOM + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
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

    public boolean categorieExist(int idCat) {
        String sql = "SELECT COUNT(*) AS count FROM " + TB_NAME + " WHERE " + TB_ID_CAT + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") >= 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategorie(Categorie categorie) {
        String sql = "UPDATE " + TB_NAME + " SET " + TB_NOM + " = ? WHERE " + TB_ID_CAT + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categorie.getNom());
            stmt.setInt(2, categorie.getIdCat());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCategorie(int idCat) {
        String sql = "DELETE FROM " + TB_NAME + " WHERE " + TB_ID_CAT + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCat);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    } 
    
    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT " + TB_ID_CAT + ", " + TB_NOM + " FROM " + TB_NAME;
        
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt(TB_ID_CAT);
                String nom = rs.getString(TB_NOM);
                Categorie categorie = new Categorie(id, nom);
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }
    
    
    
}
