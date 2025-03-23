# SCONTRI

## Objectif de l'application

Cette application a pour but de faciliter la gestion des événements en permettant aux utilisateurs de se connecter, de créer des événements et de participer à ces derniers. Elle offre une interface conviviale pour interagir avec les différentes fonctionnalités liées aux événements.

## Fonctionnalités

- Connexion et création de compte utilisateur
- Création d'événements
- Adhésion à des événements
- Consultation des événements auxquels l'utilisateur participe

## Architecture du projet

L'architecture de l'application est organisée en plusieurs couches :

- **Modèles** : Contient les classes représentant les données (ex. : `User`, `Event`).
- **Vues** : Gère l'affichage et l'interaction avec l'utilisateur (ex. : `UserView`, `EventView`).
- **Contrôleurs** : Contient la logique métier et gère les interactions entre les modèles et les vues (ex. : `UserController`, `EventController`).
- **DAO (Data Access Object)** : Gère l'accès aux données et les interactions avec la base de données (ex. : `UserDAO`, `EventDAO`).
- **Utilitaires** : Contient des classes utilitaires pour des fonctionnalités spécifiques (ex. : `Popup`).

## Bibliothèques externes

Le projet utilise les bibliothèques suivantes :
- **JDBC** : Pour la gestion des connexions à la base de données. [Télécharger JDBC](https://dev.mysql.com/downloads/connector/j/) version 9.2.0
- **Bcrypt** : Pour le hachage des mots de passe afin d'assurer la sécurité des données utilisateur. [Télécharger Bcrypt](https://jar-download.com/artifact-search/bcrypt) version 0.4.1

## Exécution de l'application

Pour exécuter l'application, suivez les étapes suivantes :

1. Assurez-vous d'avoir Java installé sur votre machine.
2. Clonez le dépôt sur votre machine locale.
3. Ouvrez un terminal et naviguez jusqu'au répertoire du projet.
4. Compilez le projet avec la commande `javac src/app/App.java`.
5. Exécutez l'application avec la commande `java -cp src app.App`.

Assurez-vous que la base de données est configurée correctement avant de lancer l'application.


