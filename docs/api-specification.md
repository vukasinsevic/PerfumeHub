# API Specification

## 1. General Information
## 2. Authentication
## 3. Perfumes
## 4. Reviews
## 5. Wishlist
## 6. Collection
## 7. Cart
## 8. Orders
## 9. Product Notifications
## 10. Administration
## 11. Error Response Format

---

# 1. General Information

- Base URL: /api
- Request and response format: application/json
- Authentication: JWT Bearer token
- Date and time format: ISO 8601
- Monetary values use decimal numbers with two decimal places.

Authenticated endpoints require the following HTTP header:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 2. Authentication

## Registration

`POST /api/auth/register`

Creates a new user account.

**Access:** Public

#### Request body

```json
{
  "name": "Jane Doe",
  "email": "jane@gmail.com",
  "password": "StrongPassword123",
  "phoneNumber": "+123456789",
  "address": "Wall Street 21, New York"
}
```

#### Successful response

**Status:** `201 Created`

```json
{
  "id": 1,
  "name": "Jane Doe",
  "email": "jane@gmail.com",
  "role": "USER",
  "createdAt": "2026-07-28T18:35:00"
}
```

#### Possible errors

- 400 Bad Request – Validation failed
- 409 Conflict – Email already exists

## Login

`POST /api/auth/login`

Authenticates a user and returns a JWT access token.

**Access:** Public

#### Request body

```json
{
  "email": "jane@gmail.com",
  "password": "StrongPassword123"
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer"
}
```

#### Possible errors

- 401 Unauthorized - Invalid email or password

---

# 3. Perfumes

## Get Perfumes

`GET /api/perfumes`

Returns a paginated list of perfumes.

**Access:** Public

#### Query parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| page | Integer | No | Page number, default is `0` |
| size | Integer | No | Page size, default is `20` |
| search | String | No | Search perfume by name |
| brandId | Long | No | Filters by brand |
| genderId | Long | No | Filters by gender |
| concentrationId | Long | No | Filters by concentration |
| noteId | Long | No | Filters by note |
| accordId | Long | No | Filters by accord | 
| seasonId | Long | No | Filters by season |
| minPrice | Decimal | No | Minimum variant price |
| maxPrice | Decimal | No | Maximum variant price |
| sort | String | No | Sort field and direction (e.g. price,asc or releaseYear,desc) |

#### Example request

`GET /api/perfumes?brandId=3&seasonId=2&minPrice=50&sort=price,asc&page=0&size=20`

#### Successful response

**Status:** `200 OK`

```json
{
  "content": [
    {
      "id": 1,
      "name": "Sauvage",
      "releaseYear": 2015,
      "imageUrl": "https://example.com/images/sauvage.jpg",
      "brand": {
        "id": 1,
        "name": "Dior"
      },
      "gender": {
        "id": 1,
        "name": "MALE"
      },
      "concentration": {
        "id": 4,
        "name": "EAU_DE_TOILETTE"
      },
      "minimumPrice": 79.99,
      "averageRating": 4.2
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

## Get Perfume by ID

`GET /api/perfumes/{perfumeId}`

Returns detailed information about a perfume.

**Access:** Public

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`GET /api/perfumes/1`

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 1,
  "name": "Sauvage",
  "description": "A fresh and spicy fragrance.",
  "releaseYear": 2015,
  "imageUrl": "https://example.com/images/sauvage.jpg",
  "brand": {
    "id": 1,
    "name": "Dior"
  },
  "gender": {
    "id": 1,
    "name": "MALE"
  },
  "concentration": {
    "id": 4,
    "name": "EAU_DE_TOILETTE"
  },
  "notes": [
    {
      "id": 1,
      "name": "Bergamot"
    },
    {
      "id": 8,
      "name": "Pepper"
    }
  ],
  "accords": [
    {
      "id": 2,
      "name": "Fresh Spicy"
    }
  ],
  "seasons": [
    {
      "id": 1,
      "name": "Spring"
    },
    {
      "id": 2,
      "name": "Summer"
    }
  ],
  "variants": [
    {
      "id": 30,
      "volumeMl": 50,
      "price": 79.99,
      "stock": 12,
      "active": true
    },
    {
      "id": 31,
      "volumeMl": 100,
      "price": 119.99,
      "stock": 5,
      "active": true
    }
  ],
  "averageRating": 4.2,
  "reviewCount": 128
}
```

#### Possible errors

- 400 Bad Request - Invalid perfume ID
- 404 Not Found - Perfume does not exist

---

# 4. Reviews

## Get Reviews

`GET /api/perfumes/{perfumeId}/reviews`

Returns all reviews for a perfume ordered by creation date (newest first).

**Access:** Public

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`GET /api/perfumes/1/reviews`

#### Successful response

**Status:** `200 OK`

```json
[
  {
    "id": 1,
    "rating": 5,
    "text": "Great fragrance.",
    "createdAt": "2026-07-28T19:00:00",
    "updatedAt": "2026-07-28T19:00:00",
    "user": {
      "id": 15,
      "name": "Jane Doe"
    }
  },
  {
    "id": 2,
    "rating": 4,
    "text": "Good fragrance, but I wish it lasted longer.",
    "createdAt": "2026-07-29T11:15:00",
    "updatedAt": "2026-07-29T11:15:00",
    "user": {
      "id": 28,
      "name": "John Smith"
    }
  }
]
```

#### Possible errors

- 400 Bad Request - Invalid perfume ID
- 404 Not Found - Perfume does not exist

## Create Review

`POST /api/perfumes/{perfumeId}/reviews`

Creates a review for a perfume.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`POST /api/perfumes/1/reviews`

#### Request body

```json
{
  "rating": 5,
  "text": "Great fragrance."
}
```

#### Successful response

**Status:** `201 Created`

```json
{
  "id": 1,
  "rating": 5,
  "text": "Great fragrance.",
  "createdAt": "2026-07-28T19:00:00",
  "updatedAt": "2026-07-28T19:00:00",
  "user": {
    "id": 1,
    "name": "Jane Doe"
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 404 Not Found - Perfume does not exist
- 409 Conflict - User has already reviewed this perfume

## Update Review

`PUT /api/reviews/{reviewId}`

Updates an existing review.

**Access:** Review author

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| reviewId | Long | ID of the review |

#### Example request

`PUT /api/reviews/1`

#### Request body

```json
{
  "rating": 4,
  "text": "Great fragrance, but it could last longer."
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 1,
  "rating": 4,
  "text": "Great fragrance, but it could last longer.",
  "createdAt": "2026-07-28T19:00:00",
  "updatedAt": "2026-07-30T21:30:00",
  "user": {
    "id": 1,
    "name": "Jane Doe"
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not the author of the review
- 404 Not Found - Review does not exist

## Delete Review

`DELETE /api/reviews/{reviewId}`

Deletes an existing review.

**Access:** Review author

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| reviewId | Long | ID of the review |

#### Example request

`DELETE /api/reviews/1`

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not the author of the review
- 404 Not Found - Review does not exist

---

# 5. Wishlist

## Get Wishlist

`GET /api/wishlist`

Returns the authenticated user's wishlist.

**Access:** Authenticated user

#### Successful response

**Status:** `200 OK`

```json
[
  {
    "id": 1,
    "name": "Sauvage",
    "brand": "Dior",
    "imageUrl": "https://example.com/images/sauvage.jpg"
  },
  {
    "id": 5,
    "name": "Le Male Le Parfum",
    "brand": "Jean Paul Gaultier",
    "imageUrl": "https://example.com/images/le-male.jpg"
  }
]
```

#### Possible errors

- 401 Unauthorized - User is not logged in

## Add Perfume to Wishlist

`POST /api/wishlist/items`

Adds a perfume to the wishlist.

**Access:** Authenticated user

#### Request body

```json
{
  "perfumeId": 1
}
```

#### Successful response

**Status:** `201 Created`

```json
{
  "id": 1,
  "addedAt": "2026-07-28T19:15:00",
  "perfume": {
    "id": 1,
    "name": "Sauvage",
    "brand": "Dior",
    "imageUrl": "https://example.com/images/sauvage.jpg"
  }
}
```
#### Possible errors

- 401 Unauthorized - User is not logged in
- 404 Not Found - Perfume does not exist
- 409 Conflict - Perfume is already in the wishlist

## Remove Perfume

`DELETE /api/wishlist/items/{perfumeId}`

Removes a perfume from the wishlist.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`DELETE /api/wishlist/items/1`

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
- 404 Not Found - Perfume is not in the wishlist

---

# 6. Cart

## Get Cart

`GET /api/cart`

Returns the authenticated user's shopping cart.

**Access:** Authenticated user

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 1,
  "items": [
    {
      "id": 10,
      "productVariantId": 3,
      "perfume": {
        "id": 1,
        "name": "Sauvage",
        "brand": "Dior",
        "imageUrl": "https://example.com/images/sauvage.jpg"
      },
      "volumeMl": 50,
      "price": 89.99,
      "quantity": 1,
      "subtotal": 89.99
    },
    {
      "id": 11,
      "productVariantId": 8,
      "perfume": {
        "id": 5,
        "name": "Le Male Le Parfum",
        "brand": "Jean Paul Gaultier",
        "imageUrl": "https://example.com/images/le-male.jpg"
      },
      "volumeMl": 125,
      "price": 109.99,
      "quantity": 2,
      "subtotal": 219.98
    }
  ],
  "totalPrice": 309.97
}
```

#### Possible errors

- 401 Unauthorized - User is not logged in

## Add Item to Cart

`POST /api/cart/items`

Adds a product variant to the cart.

**Access:** Authenticated user

#### Request body

```json
{
 "productVariantId": 3,
 "quantity": 1
}
```

#### Successful response

**Status:** `201 Created`

```json
{
 "id": 10,
 "productVariantId": 3,
 "perfume": {
    "id": 1,
    "name": "Sauvage",
    "brand": "Dior",
    "imageUrl": "https://example.com/images/sauvage.jpg"
    },
 "volumeMl": 50,
 "price": 89.99,
 "quantity": 1,
 "subtotal": 89.99
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 404 Not Found - Product variant does not exist
- 409 Conflict - Product variant is not available

## Update Cart Item Quantity

`PATCH /api/cart/items/{cartItemId}`

Updates the quantity of an existing cart item.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| cartItemId | Long | ID of the cart item |

#### Example request

`PATCH /api/cart/items/10`

#### Request body

```json
{
  "quantity": 3
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 10,
  "productVariantId": 3,
  "perfume": {
    "id": 1,
    "name": "Sauvage",
    "brand": "Dior",
    "imageUrl": "https://example.com/images/sauvage.jpg"
  },
  "volumeMl": 50,
  "price": 89.99,
  "quantity": 3,
  "subtotal": 269.97
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 403 Forbidden - Cart item does not belong to the authenticated user
- 404 Not Found - Cart item does not exist
- 409 Conflict - Requested quantity exceeds available stock

## Remove Item

`DELETE /api/cart/items/{cartItemId}`

Removes an item from the cart.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| cartItemId | Long | ID of the cart item |

#### Example request

`DELETE /api/cart/items/10`

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
- 404 Not Found - Cart item does not exist

## Remove all Items

`DELETE /api/cart`

Removes all items from the authenticated user's shopping cart.

**Access:** Authenticated user

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
