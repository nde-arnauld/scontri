-- Création de la table Utilisateur
CREATE TABLE Utilisateur (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    telephone VARCHAR(15),
    email VARCHAR(100) NOT NULL UNIQUE,
    adresse VARCHAR(255),
    mot_de_passe VARCHAR(255) NOT NULL,
    date_naissance DATE,
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
    role_systeme ENUM('admin', 'user') DEFAULT 'user'
);

-- Création de la table Lieu
CREATE TABLE Lieu (
    id_lieu INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    ville VARCHAR(100),
    code_postal VARCHAR(10)
);

-- Création de la table Categorie
CREATE TABLE Categorie (
    id_cat INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE
);

-- Création de la table Evenement
CREATE TABLE Evenement (
    id_event INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    capacite INT,
    prix DECIMAL(10, 2),
    date_debut DATETIME NOT NULL,
    date_fin DATETIME NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('actif', 'annule','supprime', 'termine') DEFAULT 'actif',
    id_lieu INT,
    id_cat INT,
    FOREIGN KEY (id_lieu) REFERENCES Lieu(id_lieu),
    FOREIGN KEY (id_cat) REFERENCES Categorie(id_cat)
);

-- Création de la table Org_evenement (relation entre Utilisateur et Evenement pour les organisateurs)
CREATE TABLE Org_evenement (
    id_user INT,
    id_event INT,
    PRIMARY KEY (id_user, id_event),
    FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
    FOREIGN KEY (id_event) REFERENCES Evenement(id_event)
);

-- Création de la table Part_evenement (relation entre Utilisateur et Evenement pour les participants)
CREATE TABLE Part_evenement (
    id_user INT,
    id_event INT,
    status enum('en_attente','rejetee', 'annulee', 'validee') NOT NULL DEFAULT 'en_attente',
    presence enum('oui','non') NOT NULL DEFAULT 'non',
    date_part datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (id_user, id_event),
    FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
    FOREIGN KEY (id_event) REFERENCES Evenement(id_event)
);

-- Création de la table Message
CREATE TABLE Message (
    id_message INT AUTO_INCREMENT PRIMARY KEY,
    txt_msg TEXT NOT NULL,
    date_envoi DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_user INT,
    id_event INT,
    FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
    FOREIGN KEY (id_event) REFERENCES Evenement(id_event)
);

-- Création de la table Commentaire
CREATE TABLE Commentaire (
    id_comment INT AUTO_INCREMENT PRIMARY KEY,
    txt_comment TEXT NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_user INT,
    id_event INT,
    FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
    FOREIGN KEY (id_event) REFERENCES Evenement(id_event)
);

-- Création de la table Notification
CREATE TABLE Notification (
    id_notif INT AUTO_INCREMENT PRIMARY KEY,
    txt_notif TEXT NOT NULL,
    date_envoi_notif DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_event INT,
    FOREIGN KEY (id_event) REFERENCES Evenement(id_event)
);

-- Création de la table Utilisateur_notif (relation entre Utilisateur et Notification)
CREATE TABLE Utilisateur_notif (
    id_user INT,
    id_notif INT,
    PRIMARY KEY (id_user, id_notif),
    FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user),
    FOREIGN KEY (id_notif) REFERENCES Notification(id_notif)
);
