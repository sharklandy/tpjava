# 🍵 Boutique de Thés - Spring Boot

Application web de gestion de boutique de thés avec Spring Boot, JPA/Hibernate et MySQL.

## 🚀 Quick Start

```bash
docker-compose up -d
```

Accès : http://localhost:8080

## 🛠️ Stack Technique

- **Backend** : Spring Boot 3.2.0 (Java 17)
- **Base de données** : MySQL 8.0
- **ORM** : Spring Data JPA / Hibernate
- **Template** : Thymeleaf
- **Container** : Docker & Docker Compose
- **Admin BD** : phpMyAdmin (port 8081)

## ✨ Fonctionnalités

- ✅ CRUD complet des produits
- ✅ Recherche et filtres avancés
- ✅ Pagination (10 par page)
- ✅ Tri dynamique (nom, prix, stock, date)
- ✅ Export CSV
- ✅ Validation des formulaires
- ✅ Statistiques en temps réel
- ✅ Design dark "Palais des Thés"

## 📸 Screenshots

![Page d'accueil](screenshot/Capture%20d'écran%202026-01-21%20144317.png)
![Formulaire](screenshot/Capture%20d'écran%202026-01-21%20144726.png)

## 🐳 Services Docker

| Service | Port | Description |
|---------|------|-------------|
| Application | 8080 | Spring Boot App |
| MySQL | 3306 | Base de données |
| phpMyAdmin | 8081 | Interface admin BD |

## 📦 Installation Manuelle

```bash
# Clone
git clone https://github.com/sharklandy/tpjava.git
cd tpjava

# Lancer avec Docker
docker-compose up -d

# Ou build Maven local
cd boutique-thes
mvn clean package
java -jar target/boutique-thes-1.0.0.jar
```

## 🗃️ Base de Données

**Connexion MySQL** :
- Host: `localhost:3306`
- Database: `boutique_thes`
- User: `admin`
- Password: `admin123`

## 📝 API Endpoints

- `GET /` - Liste des produits
- `GET /nouveau` - Formulaire ajout
- `POST /enregistrer` - Créer produit
- `GET /modifier/{id}` - Formulaire modification
- `POST /modifier/{id}` - Mettre à jour
- `GET /supprimer/{id}` - Supprimer
- `GET /exporter-csv` - Export CSV

## 👨‍💻 Auteur

**Landry LHOMME** - SUP DE VINCI

## 📄 Licence

Ce projet est un exercice académique.
