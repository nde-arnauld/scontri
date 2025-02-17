package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.User;

public class UserDAO {
	private final String TB_NAME = "utilisateur";
	private final String TB_ID_USER = "id_user";
	private final String TB_NOM = "nom";
	private final String TB_PRENOM = "prenom";
	private final String TB_TELEPHONE = "telephone";
	private final String TB_EMAIL = "email";
	private final String TB_ADRESSE = "adresse";
	private final String TB_MOT_DE_PASSE = "mot_de_passe";
	private final String TB_DATE_NAISSANCE = "date_naissance";
	private final String TB_DATE_INSCRIPTION = "date_inscription";
	private final String TB_ROLE_SYSTEME = "role_systeme";
	
	private Connection conn;
	
	public UserDAO(Connection con) {
		this.conn = con;
	}
	
	public boolean addUser(User user) {
        String sql = "INSERT INTO "+ TB_NAME +" ("+
        		TB_NOM +", "+
        		TB_PRENOM +", "+
        		TB_TELEPHONE +", "+
        		TB_EMAIL +", "+
        		TB_ADRESSE +", "+ 
        		TB_MOT_DE_PASSE +", "+
        		TB_DATE_NAISSANCE +", "+
        		TB_DATE_INSCRIPTION +", "+
        		TB_ROLE_SYSTEME +
        		") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getTelephone());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getAdresse());
            stmt.setString(6, user.getMotDePasse());
            stmt.setDate(7, Date.valueOf(user.getDateNaissance()));
            stmt.setDate(8, Date.valueOf(user.getDateInscription()));
            stmt.setString(9, user.getRoleSysteme());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
