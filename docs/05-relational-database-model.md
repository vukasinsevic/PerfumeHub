# Relational Database Model

## PERFUME

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(250) | NOT NULL |
| description | TEXT | |
| release_year | INTEGER | |
| image_url | TEXT | |
| brand_id | BIGINT | FK - BRAND(id), NOT NULL |
| gender_id | BIGINT | FK - GENDER(id), NOT NULL |
| concentration_id | BIGINT | FK - CONCENTRATION(id), NOT NULL |

---

## BRAND

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(100) | UNIQUE, NOT NULL |

---

## GENDER

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(10) | UNIQUE, NOT NULL |

---

## CONCENTRATION

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(20) | UNIQUE, NOT NULL |

---

## NOTE

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(50) | UNIQUE, NOT NULL |

---

## PERFUME_NOTE

| Column | Type | Constraints |
|---------|------|-------------|
| perfume_id | BIGINT | FK - PERFUME(id), NOT NULL |
| note_id | BIGINT | FK - NOTE(id), NOT NULL |

### Additional Constraints

- PK (perfume_id, note_id)

---

## ACCORD

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(50) | UNIQUE, NOT NULL |

---

## PERFUME_ACCORD

| Column | Type | Constraints |
|---------|------|-------------|
| perfume_id | BIGINT | FK - PERFUME(id), NOT NULL |
| accord_id | BIGINT | FK - ACCORD(id), NOT NULL |

### Additional Constraints

- PK (perfume_id, accord_id)

---

## SEASON

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(50) | UNIQUE, NOT NULL |

---

## PERFUME_SEASON

| Column | Type | Constraints |
|---------|------|-------------|
| perfume_id | BIGINT | FK - PERFUME(id), NOT NULL |
| season_id | BIGINT | FK - SEASON(id), NOT NULL |

### Additional Constraints

- PK (perfume_id, season_id)

---

## APP_USER

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | UNIQUE, NOT NULL |
| password_hash | VARCHAR(255) | NOT NULL |
| phone_number | VARCHAR(20) | UNIQUE, NOT NULL |
| address | VARCHAR(255) | NOT NULL |
| created_at | TIMESTAMP | NOT NULL |
| role | VARCHAR(50) | NOT NULL |

--- 

## REVIEW

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| rating | INTEGER | NOT NULL, CHECK (rating BETWEEN 1 AND 5) |
| text | TEXT | |
| created_at | TIMESTAMP | NOT NULL |
| updated_at | TIMESTAMP | NOT NULL |
| perfume_id | BIGINT | FK - PERFUME(id), NOT NULL |
| app_user_id | BIGINT | FK - APP_USER(id), NOT NULL |

### Additional Constraints

- UNIQUE (app_user_id, perfume_id)

---

## WISHLIST_ITEM

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| added_at | TIMESTAMP | NOT NULL |
| perfume_id | BIGINT | FK - PERFUME(id), NOT NULL |
| app_user_id | BIGINT | FK - APP_USER(id), NOT NULL |

### Additional Constraints

- UNIQUE (app_user_id, perfume_id)

---

## COLLECTION_ITEM

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| status | VARCHAR(20) | NOT NULL |
| added_at | TIMESTAMP | NOT NULL |
| perfume_id | BIGINT | FK - PERFUME(id), NOT NULL |
| app_user_id | BIGINT | FK - APP_USER(id), NOT NULL |

### Additional Constraints

- UNIQUE (app_user_id, perfume_id)

---

## PRODUCT_VARIANT

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| volume_ml | INTEGER | NOT NULL, CHECK (volume_ml > 0) |
| price | DECIMAL(10,2) | NOT NULL, CHECK (price > 0) |
| stock | INTEGER | NOT NULL, CHECK (stock >= 0) |
| active | BOOLEAN | NOT NULL |
| perfume_id | BIGINT | FK - PERFUME(id), NOT NULL |

### Additional Constraints

- UNIQUE (perfume_id, volume_ml)

---

## CART

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| created_at | TIMESTAMP | NOT NULL |
| updated_at | TIMESTAMP | NOT NULL |
| app_user_id | BIGINT | FK - APP_USER(id), UNIQUE, NOT NULL |

---

## CART_ITEM

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| quantity | INTEGER | NOT NULL, CHECK (quantity > 0) |
| added_at | TIMESTAMP | NOT NULL |
| cart_id | BIGINT | FK - CART(id), NOT NULL |
| product_variant_id | BIGINT | FK - PRODUCT_VARIANT(id), NOT NULL |

### Additional Constraints

- UNIQUE (cart_id, product_variant_id)

---

## CUSTOMER_ORDER

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| created_at | TIMESTAMP | NOT NULL |
| status | VARCHAR(20) | NOT NULL |
| total_price | DECIMAL(10,2) | NOT NULL, CHECK (total_price > 0) |
| shipping_address | VARCHAR(255) | NOT NULL |
| app_user_id | BIGINT | FK - APP_USER(id), NOT NULL |

---

## ORDER_ITEM

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| quantity | INTEGER | NOT NULL, CHECK (quantity > 0) |
| price_per_item | DECIMAL(10,2) | NOT NULL, CHECK (price_per_item > 0) |
| customer_order_id | BIGINT | FK - CUSTOMER_ORDER(id), NOT NULL |
| product_variant_id | BIGINT | FK - PRODUCT_VARIANT(id), NOT NULL |

### Additional Constraints

- UNIQUE (customer_order_id, product_variant_id)

---

## PRODUCT_NOTIFICATION

| Column | Type | Constraints |
|---------|------|-------------|
| id | BIGSERIAL | PK |
| type | VARCHAR(20) | NOT NULL |
| active | BOOLEAN | NOT NULL |
| created_at | TIMESTAMP | NOT NULL |
| last_known_price | DECIMAL(10,2) | CHECK (last_known_price > 0) |
| app_user_id | BIGINT | FK - APP_USER(id), NOT NULL |
| product_variant_id | BIGINT | FK - PRODUCT_VARIANT(id), NOT NULL |

### Additional Constraints

- UNIQUE (app_user_id, product_variant_id, type)