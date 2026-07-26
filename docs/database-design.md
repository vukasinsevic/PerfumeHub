# Database Design

## Overview

PerfumeHub is a perfume discovery and e-commerce application.  
The database supports perfume catalog management, user reviews, wishlists,
personal collections, shopping carts, orders, and product notifications.

## Entities

## 1. Perfume

### Attributes

- id
- name
- description
- releaseYear
- imageUrl

### Relationships

- belongs to one Brand
- belongs to one Gender
- belongs to one Concentration
- has one or more ProductVariants
- has one or more Notes
- has one or more Accords
- has one or more Seasons
- may have many Reviews
- may have many WishlistItems
- may have many CollectionItems

---

## 2. Brand

### Attributes

- id
- name

### Relationships

- may have many Perfumes

---

## 3. Gender

### Attributes

- id
- name

### Relationships

- may have many Perfumes

---

## 4. Concentration

### Attributes

- id
- name

### Relationships

- may have many Perfumes

---

## 5. Note

### Attributes

- id
- name

### Relationships

- may belong to many Perfumes

---

## 6. Accord

### Attributes

- id
- name

### Relationships

- may belong to many Perfumes

---

## 7. Season

### Attributes

- id
- name

### Relationships

- may belong to many Perfumes

---

## 8. Review

### Attributes

- id
- rating
- text
- createdAt
- updatedAt

### Relationships

- belongs to one Perfume
- belongs to one User

---

## 9. WishlistItem

### Attributes

- id
- addedAt

### Relationships

- belongs to one Perfume
- belongs to one User

--- 

## 10. CollectionItem

### Attributes

- id
- status
- addedAt

### Relationships

- belongs to one Perfume
- belongs to one User

--- 

## 11. User

### Attributes

- id
- name
- email
- passwordHash
- phoneNumber
- address
- createdAt
- role

### Relationships

- may have many ProductNotifications
- may have many Orders
- may have many Reviews
- may have many WishlistItems
- may have many CollectionItems
- may have one Cart

--- 

## 12. Cart

### Attributes

- id
- createdAt
- updatedAt

### Relationships

- has one or more CartItems
- belongs to one User

---

## 13. CartItem

### Attributes

- id
- quantity
- addedAt

### Relationships

- belongs to one Cart
- belongs to one ProductVariant

---

## 14. ProductVariant

### Attributes

- id
- volumeMl
- price
- stock
- active

### Relationships

- may have many CartItems
- may have many OrderItems
- may have many ProductNotifications
- belongs to one Perfume

---

## 15. Order

### Attributes

- id
- createdAt
- status
- totalPrice
- shippingAddress

### Relationships

- has one or more OrderItems
- belongs to one User

---

## 16. OrderItem

### Attributes

- id
- quantity
- pricePerItem

### Relationships

- belongs to one Order
- belongs to one ProductVariant

---

## 17. ProductNotification

### Attributes

- id
- type
- active
- createdAt
- lastKnownPrice

### Relationships

- belongs to one User
- belongs to one ProductVariant