# Évaluation Technique Backend – Spring Boot & Microservices

**Durée :** 5 heures

## 🏢 Contexte

Vous venez de rejoindre l'équipe de développement de **"UEA Shop"**. On vous a confié une application existante qui contient plusieurs dettes techniques et bugs. L'application est un service Spring Boot monolithique qui simule une architecture microservices via des domaines distincts (Catalogue, Commande, Paiement).

## 🎯 Vos Objectifs

### 1. 🛠️ Réparation de l'Infrastructure (Docker)

Actuellement, la commande `docker-compose up` échoue ou l'application n'arrive pas à se connecter à la base de données.

**Tâche :** Identifiez et corrigez les problèmes de configuration dans le fichier `docker-compose.yml` pour que l'environnement démarre correctement.

---

### 2. ⚡ Optimisation de la Base de Données (N+1)

L'endpoint `GET /categories` est extrêmement lent lorsque le volume de données augmente.

**Tâche :** Analysez les logs Hibernate, identifiez le problème de performance (N+1) et refactorisez le service pour utiliser une requête JPA optimisée (ex: `JOIN FETCH` ou `EntityGraph`).

---

### 3. 📦 Implémentation de Fonctionnalité (Logique Métier)

Le service de commande (`OrderService`) permet aux clients de commander des produits même s'ils ne sont plus en stock.

**Tâche :** Modifiez la méthode `placeOrder` pour qu'elle vérifie réellement le stock via `InventoryClient`.
- Si le stock est vide, le système doit rejeter la commande et lancer une exception.
- Assurez-vous que la transaction est gérée correctement.

---

### 4. 🧹 Refactoring (Code Legacy)

La classe `LegacyPaymentProcessor` est obsolète, non sécurisée et difficile à maintenir.

**Tâche :** Refactorisez cette classe pour utiliser les standards Spring Boot :
- Utilisez un `Repository` pour l'accès aux données.
- Utilisez l'injection de dépendances.
- Déplacez les identifiants codés en dur vers le fichier de configuration (`application.properties` ou `application.yml`).

---

## 📤 Livrable

Veuillez soumettre le code corrigé ainsi que les tests unitaires/intégration passant au vert.

**Note:** Ne modifiez pas les tests fournis. Utilisez les tests comme orientation pour vous assurer que votre code est correct.

```bash
# Pour lancer les tests
docker-compose run --rm tests
```

**Critères de succès :**
- L'application démarre avec `docker-compose up --build`.
- Tous les tests passent (BUILD SUCCESS).
- Le code est propre et respecte les bonnes pratiques Spring Boot.
