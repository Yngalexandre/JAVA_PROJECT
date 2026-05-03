# JAVAZIK - Plateforme de Streaming Musical

## 📝 Présentation
JAVAZIK est une application de gestion de catalogue musical et de playlists développée en Java (ING3). Elle propose une expérience utilisateur moderne "Sunset" avec une interface graphique Swing complète, respectant strictement l'architecture **MVC**.

## 🚀 Comment lancer l'application
1.  Ouvrez le projet dans votre IDE (IntelliJ, Eclipse ou VS Code).
2.  Exécutez la classe **`app.MainGUI`** pour lancer l'interface utilisateur.
3.  Pour la version console (si requise), exécutez **`app.Main`**.

## 👥 Comptes de test (Identifiants)
| Catégorie | Login | Mot de passe | Description |
|---|---|---|---|
| **Administrateur** | `admin` | `admin` | Gestion totale, statistiques, modération. |
| **Abonné** | `alice` | `alice123` | Playlists, historique, écoute illimitée. |
| **Abonné** | `bob` | `bob123` | Autre compte abonné. |
| **Visiteur** | *(Aucun)* | *(Aucun)* | Consultation et écoute limitée. |

## ✨ Fonctionnalités Clés
### 👤 Utilisateurs
- **Inscription & Connexion** : Créez votre compte directement depuis l'interface.
- **Espace Abonné** : Gestion complète des playlists (Créer, Renommer, Supprimer, Ajouter/Retirer des titres).
- **Historique d'écoute** : Retrouvez vos dernières écoutes.

### 🎵 Catalogue & Lecture
- **Recherche temps réel** : Filtrez le catalogue par titre ou artiste instantanément.
- **Barre de lecture animée** : Visualisez la progression de votre musique avec une barre de progression fluide.
- **Mode Visiteur** : Limite automatique à 5 écoutes par session (conforme au cahier des charges).

### 🛡️ Administration
- **Gestion du catalogue** : Ajoutez, **modifiez** ou supprimez des morceaux de la base de données.
- **Statistiques détaillées** : Visualisez le nombre d'écoutes, les **Top 3 des morceaux les plus populaires** et la liste des abonnés inscrits.
- **Modération** : Possibilité de suspendre ou **supprimer définitivement** un compte abonné.

## 🏗️ Architecture Technique
- **Modèle (Model)** : Logique métier pure, sans dépendance UI.
- **Vue (View)** : Interface Swing avec thémisation "Sunset & Cozy" personnalisée.
- **Contrôleur (Controller)** : Orchestration via `GuiController` pour le lien entre données et affichage.
- **Persistance** : Sérialisation automatique dans le dossier `data/` à chaque fermeture.

---
*Projet réalisé dans le cadre du module POO - Java 2025-2026.*
