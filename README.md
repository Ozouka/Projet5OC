# Yoga App – README Global

Bienvenue sur le projet **Yoga App**, une application de réservation de sessions pour le studio Savasana, développée par l'agence NumDev.

## 🚀 Lancer l'application

### Prérequis

- Node.js (version recommandée : 16+)
- npm
- Java 17+
- Maven
- MySQL

### 1. Cloner le projet

```bash
git clone https://github.com/OpenClassrooms-Student-Center/P5-Full-Stack-testing
cd yoga
```

### 2. Installer les dépendances

#### Front-end

```bash
cd front
npm install
```

#### Back-end

Configurer la base de données MySQL avec le script `ressources/sql/script.sql` si besoin.

```bash
cd ../back
# Vérifier le fichier application.properties pour la configuration BDD
```

### 3. Lancer les applications

#### Lancer le back-end

```bash
cd back
mvn spring-boot:run
```

#### Lancer le front-end

Dans un autre terminal :

```bash
cd front
npm run start
```

L'application sera accessible sur [http://localhost:4200](http://localhost:4200).

## 🧪 Lancer les tests & rapports de couverture

### Back-end

Pour lancer les tests et générer le rapport de couverture Jacoco :

```bash
cd back
mvn clean test
```

Le rapport Jacoco sera généré dans `back/target/site/jacoco/index.html`.

### Front-end

Pour lancer les tests unitaires :

```bash
cd front
npm run test
```

Pour lancer les tests end-to-end :

```bash
npm run e2e
```

Pour générer le rapport de couverture (après les tests e2e) :

```bash
npm run e2e:coverage
```

Le rapport de couverture sera disponible dans `front/coverage/lcov-report/index.html`.

## 👤 Comptes de test

Un compte admin par défaut est disponible :

- **login** : yoga@studio.com
- **mot de passe** : test!1234

## 📑 Ressources utiles

- Collection Postman : `ressources/postman/yoga.postman_collection.json`
- Script SQL : `ressources/sql/script.sql`
