package models;

import java.time.LocalDate;

public class User {
	private int idUser;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;
    private String motDePasse;
    private LocalDate dateNaissance;
    private LocalDate dateInscription;
    private String roleSysteme;
    
    public User(int idUser, String nom, String prenom, String telephone, String email, String adresse, 
            String motDePasse, LocalDate dateNaissance, LocalDate dateInscription, String roleSysteme) {
    this.idUser = idUser;
    this.nom = nom;
    this.prenom = prenom;
    this.telephone = telephone;
    this.email = email;
    this.adresse = adresse;
    this.motDePasse = motDePasse;
    this.dateNaissance = dateNaissance;
    this.dateInscription = dateInscription;
    this.roleSysteme = roleSysteme;
    }
    
    public User(String nom, String prenom, String telephone, String email, String adresse, 
            String motDePasse, LocalDate dateNaissance, LocalDate dateInscription, String roleSysteme) {
    	this(0, nom, prenom, telephone, email, adresse, motDePasse, dateNaissance, dateInscription, roleSysteme);
    }
    
    public User() {
    	idUser = 0;
    }
    
    public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public LocalDate getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDate dateInscription) {
		this.dateInscription = dateInscription;
	}

	public String getRoleSysteme() {
		return roleSysteme;
	}

	public void setRoleSysteme(String roleSysteme) {
		this.roleSysteme = roleSysteme;
	}

	@Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", dateInscription=" + dateInscription +
                ", roleSystem='" + roleSysteme + '\'' +
                '}';
    }
}
