package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.time.LocalDate;

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
		String sql = "INSERT INTO " + TB_NAME + " (" +
				TB_NOM + ", " +
				TB_PRENOM + ", " +
				TB_TELEPHONE + ", " +
				TB_EMAIL + ", " +
				TB_ADRESSE + ", " +
				TB_MOT_DE_PASSE + ", " +
				TB_DATE_NAISSANCE + ", " +
				TB_DATE_INSCRIPTION + ", " +
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

	public boolean userExist(String email) {

		String sql = "SELECT COUNT(*) AS count FROM " + TB_NAME + " WHERE " + TB_EMAIL + " = ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, email);

			try {
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					return rs.getInt("count") >= 1;
				}
			} catch (SQLTimeoutException e) {
				System.err.println("Erreur : 'userExist()'\n");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Erreur : 'userExist()'\n");
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Cette méthode permet de récupérer un mot de passe à partir d'un email.
	 * 
	 * @param email
	 * @return Le mot de passe haché correspondant à cet email.
	 */
	public String userPassword(String email) {
		String sql = "SELECT " + TB_MOT_DE_PASSE + " FROM " + TB_NAME + " WHERE " + TB_EMAIL + " = ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);

			try {
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					return rs.getString(TB_MOT_DE_PASSE);
				}
			} catch (SQLTimeoutException e) {
				System.err.println("Erreur : 'userPassword()'\n");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Erreur : 'userPassword()' -> createStatement() \n");
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Cette méthode permet de récupérer un utilisateur en fonction de son
	 * <i>email</i>
	 * 
	 * @param email
	 * @return <b>User | null : </b>retourne un <b>User</b> s'il existe ou
	 *         <b>null</b> dans le cas contraire.
	 */
	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM " + TB_NAME + " WHERE " + TB_EMAIL + " = ?";

		int idUser = 0;
		String nom = "";
		String prenom = "";
		String telephone = "";
		String adresse = "";
		String motDePasse = "";
		LocalDate dateNaissance = null;
		LocalDate dateInscription = null;
		String roleSysteme = "";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);

			try {
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					idUser = rs.getInt(TB_ID_USER);
					nom = rs.getString(TB_NOM);
					prenom = rs.getString(TB_PRENOM);
					telephone = rs.getString(TB_TELEPHONE);
					adresse = rs.getString(TB_ADRESSE);
					motDePasse = rs.getString(TB_MOT_DE_PASSE);
					dateNaissance = rs.getDate(TB_DATE_NAISSANCE).toLocalDate();
					dateInscription = rs.getDate(TB_DATE_INSCRIPTION).toLocalDate();
					roleSysteme = rs.getString(TB_ROLE_SYSTEME);
				}

				return new User(idUser, nom, prenom, telephone, email, adresse, motDePasse, dateNaissance,
						dateInscription, roleSysteme);
			} catch (SQLTimeoutException e) {
				System.err.println("Erreur : 'userPassword()'\n");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Erreur : 'userPassword()' -> createStatement() \n");
			e.printStackTrace();
		}
		return null;
	}
}
