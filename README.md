# 🛒 Product Manager Kata

Ce projet est une API REST Java permettant de gérer un catalogue de produits, un panier d’achat, une wishlist, et un système d’authentification simple basé sur JWT.

Il a été conçu comme un exercice technique, avec une attention particulière portée à l’architecture, la qualité du code, et le respect des bonnes pratiques backend.

---

## 🚀 Stack technique

- Java 21 + Spring Boot 3.4
- Spring Security + JWT
- H2 Database (in-memory)
- JPA (Hibernate)
- MapStruct + Lombok
- OpenAPI (Swagger)
- Tests unitaires : JUnit + MockMvc

---

## 📦 Fonctionnalités

### 🔐 Authentification
- Création de compte via `/account`
- Login JWT via `/token`
- Seul `admin@admin.com` peut modifier les produits

### 🧾 Produits (`/products`)
- Ajouter, lire, modifier, supprimer un produit
- Filtrage par ID
- Champs : code, nom, description, stock, etc.

### 🛍️ Panier (`/cart`)
- Ajouter/supprimer un produit au panier utilisateur
- Voir son panier

### ❤️ Wishlist (`/wishlist`)
- Ajouter/supprimer un produit à sa liste d’envies
- Voir sa liste d’envies

---

## 📚 Documentation API

Swagger UI disponible sur :

http://localhost:8080/swagger-ui/index.html

Base H2 disponible sur :

http://localhost:8080/h2-console