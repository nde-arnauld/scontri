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
	private final String TB_IDCAT = "id_Cat";
	private final String TB_NOM = "nom";
	
	private Connection conn; 
	
	public CategorieDAO(Connection con) {
		this.conn = con;
	}
	
	public boolean addCategorie(Categorie categorie) {
		 String sql = "INSERT INTO "+ TB_NAME +" ("+
	        		TB_NOM +
	        		") VALUES (?, ?)";
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
	
	 public List<Categorie> getAllCategories() {
	        List<Categorie> categories = new ArrayList<>();
	        String sql = "SELECT " + TB_IDCAT + ", " + TB_NOM + " FROM " + TB_NAME;
	        
	        try {
	        	PreparedStatement stmt = conn.prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery();
	            
	            while (rs.next()) {
	                int id = rs.getInt(TB_IDCAT);
	                String nom = rs.getString(TB_NOM);
	                Categorie categorie = new Categorie(id, nom);
	                categories.add(categorie);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return categories;
	    }
	 
	 public boolean deleteCategorie(int idCategorie) {
		    String sql = "DELETE FROM " + TB_NAME + " WHERE " + TB_IDCAT + " = ?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idCategorie);
		        int result = stmt.executeUpdate();
		        return result > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
	 }
	 
	 public boolean categorieExist(String nomCat) {
		    String sql = "SELECT 1 FROM " + TB_NAME + " WHERE " + TB_NOM + " = ?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, nomCat);
		        try (ResultSet rs = stmt.executeQuery()) {
		            return rs.next();  
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	 public boolean categorieIDExist(int idCat) {
		    String sql = "SELECT 1 FROM " + TB_NAME + " WHERE " + TB_IDCAT + " = ?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idCat);
		        try (ResultSet rs = stmt.executeQuery()) {
		            return rs.next();  
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	
	
	
	
	

}
