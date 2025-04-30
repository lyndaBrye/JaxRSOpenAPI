# 🎵 API de Gestion des Concerts

Ce projet est une API RESTful développée en **Java avec Jakarta EE** pour la gestion de concerts, des utilisateurs, des tickets et des entités associées (artistes, organisateurs). Cette API permet de créer, consulter, réserver et supprimer des concerts tout en assurant une gestion complète des utilisateurs et de leurs billets.

---

## ✨ Fonctionnalités principales

- 🎤 **Gestion des concerts** : Création, mise à jour, suppression et consultation.
- 👤 **Gestion des utilisateurs** : Création, mise à jour, authentification, suppression.
- 🎟️ **Gestion des tickets** : Réservation de billets pour les concerts.
- 🎭 **Artistes & Organisateurs** : Association des concerts avec les artistes et les organisateurs.
- 🔐 **Authentification utilisateur** : Connexion basique via email/mot de passe.
- 📄 **Documentation Swagger/OpenAPI** intégrée.

---

## 🛠️ Technologies utilisées

| Composant        | Outil / Framework        |
|------------------|--------------------------|
| Langage          | Java                     |
| Framework API    | Jakarta EE (JAX-RS)      |
| ORM              | Hibernate / JPA          |
| Build Tool       | Maven                    |
| Documentation    | Swagger / OpenAPI        |
| Base de données  | HSQL                     |

---

## 📁 Structure du projet

```
src/main/java/fr/istic/taa/jaxrs/
├── domain/         → Entités JPA (Concert, User, Ticket, etc.)
├── dao/generic/    → DAO pour l’accès aux données
├── rest/           → Ressources REST (ConcertResource, UserResource,OrganisateurResource,...)
├── DTO/            → Objets de transfert de données
```

---

## 📦 DAO disponibles

- `ConcertDao`  
- `ArtisteDao`  
- `OrganisateurDao`  
- `TicketDao`  
- `UserDao`  

---

## 📨 DTO (Data Transfer Objects)

- `ConcertDTO` / `ConcertDTOResponse`
- `UserDTO`
- `TicketDTO`

---

## 🌐 Ressources REST exposées

### 🎤 Concerts
- `GET /concerts/all`  
- `GET /concerts/{id}`  
- `POST /concerts`  
- `PUT /concerts/{id}`  
- `DELETE /concerts/{id}`  
- `GET /concerts/{id}/capacite-restante`  
- `GET /concerts/artiste/{artisteId}`  
- `GET /concerts/lieu/{lieu}`  
- `GET /concerts/date/{date}`  
- `GET /concerts/organisateur/{organisateurId}`  

### 👤 Utilisateurs
- `GET /users/all`  
- `GET /users/{userId}`  
- `POST /users`  
- `PUT /users/{userId}`  
- `DELETE /users/{userId}`  
- `POST /users/login`  

### 🎟️ Tickets
- `POST /users/{userId}/tickets/{concertId}?quantity={quantity}`  
> Réserve une ou plusieurs places pour un utilisateur sur un concert donné.

---

## 🚀 Installation & Lancement

### 1. Cloner le projet
```bash
git clone https://github.com/lyndaBrye/JaxRSOpenAPI.git
cd JaxRSOpenAPI
```

### 2. Lancer le serveur HSQL
- **Windows** : Exécuter `run-hsql-server.bat`
- **Linux/Mac** : Exécuter `run-hsql-server.sh`

### 3. Démarrer le serveur REST
Exécuter le fichier `RestServer.java`.

---

## 📄 Exécution de Swagger

Une fois l'application démarrée sur votre serveur d'application, vous pouvez accéder à la documentation Swagger en ouvrant l'URL suivante dans votre navigateur :

```
http://localhost:8080/api/#/
```
```
