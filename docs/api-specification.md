# API Specification

## 1. General Information
## 2. Authentication
## 3. Perfumes
## 4. Reviews
## 5. Wishlist
## 6. Cart
## 7. Collection
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

- 400 Bad Request - Validation failed
- 409 Conflict - Email or phone number already exists

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
    "createdAt": "2026-07-29T19:00:00",
    "updatedAt": "2026-07-29T19:00:00",
    "user": {
      "id": 15,
      "name": "Jane Doe"
    }
  },
  {
    "id": 2,
    "rating": 4,
    "text": "Good fragrance, but I wish it lasted longer.",
    "createdAt": "2026-07-28T11:15:00",
    "updatedAt": "2026-07-28T11:15:00",
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
    "addedAt": "2026-07-28T19:15:00",
    "perfume": {
      "id": 1,
      "name": "Sauvage",
      "brand": "Dior",
      "imageUrl": "https://example.com/images/sauvage.jpg"
    }
  },
  {
    "id": 2,
    "addedAt": "2026-07-30T09:45:00",
    "perfume": {
      "id": 5,
      "name": "Le Male Le Parfum",
      "brand": "Jean Paul Gaultier",
      "imageUrl": "https://example.com/images/le-male.jpg"
    }
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

Adds a product variant to the cart or increases its quantity if it is already present.

**Access:** Authenticated user

#### Request body

```json
{
 "productVariantId": 3,
 "quantity": 1
}
```

#### Successful response

**Status:** `200 OK` -  The quantity of an existing cart item was increased.
**Status:** `201 Created` - A new cart item was created.

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

---

# 7. Collection

## Get Collection

`GET /api/collection`

Returns the authenticated user's perfume collection.

**Access:** Authenticated user

#### Successful response

**Status:** `200 OK`

```json
[
  {
    "id": 1,
    "addedAt": "2026-07-28T18:30:00",
    "status": "OWNED",
    "perfume": {
      "id": 1,
      "name": "Sauvage",
      "brand": "Dior",
      "imageUrl": "https://example.com/images/sauvage.jpg"
    }
  },
  {
    "id": 2,
    "addedAt": "2026-07-30T12:10:00",
    "status": "LIKED",
    "perfume": {
      "id": 5,
      "name": "Le Male Le Parfum",
      "brand": "Jean Paul Gaultier",
      "imageUrl": "https://example.com/images/le-male.jpg"
    }
  }
]
```

#### Possible errors

- 401 Unauthorized - User is not logged in

## Add Perfume

`POST /api/collection/items`

Adds a perfume to the collection.

**Access:** Authenticated user

#### Request body

```json
{
 "perfumeId": 3,
 "status": "TESTED"
}
```

#### Successful response

**Status:** `201 Created`

```json
  {
    "id": 1,
    "addedAt": "2026-07-30T12:10:00",
    "status": "TESTED",
    "perfume": {
      "id": 3,
      "name": "MYSLF Le Parfum",
      "brand": "Yves Saint Laurent",
      "imageUrl": "https://example.com/images/myslf-le-parfum.jpg"
    }
  }
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 404 Not Found - Perfume does not exist
- 409 Conflict - Perfume is already in the collection

## Update Collection Item Status

`PATCH /api/collection/items/{perfumeId}`

Updates the status of a perfume in the authenticated user's collection.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`PATCH /api/collection/items/3`

#### Request body

```json
{
  "status": "FINISHED"
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 1,
  "addedAt": "2026-07-30T12:10:00",
  "status": "FINISHED",
  "perfume": {
    "id": 3,
    "name": "MYSLF Le Parfum",
    "brand": "Yves Saint Laurent",
    "imageUrl": "https://example.com/images/myslf-le-parfum.jpg"
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 404 Not Found - Perfume is not in the collection

## Remove Perfume from Collection

`DELETE /api/collection/items/{perfumeId}`

Removes a perfume from the authenticated user's collection.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`DELETE /api/collection/items/3`

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
- 404 Not Found - Perfume is not in the collection

---

# 8. Orders

## Get Orders

`GET /api/orders`

Returns the authenticated user's orders.

**Access:** Authenticated user

#### Successful response

**Status:** `200 OK`

```json
[
  {
    "id": 1,
    "createdAt": "2026-07-30T18:20:00",
    "status": "DELIVERED",
    "totalPrice": 209.98
  },
  {
    "id": 2,
    "createdAt": "2026-08-02T09:15:00",
    "status": "PROCESSING",
    "totalPrice": 89.99
  }
]
```

#### Possible errors

- 401 Unauthorized - User is not logged in

## Create Order

`POST /api/orders`

Creates a new order from the authenticated user's current cart using the user's saved shipping address.

**Access:** Authenticated user

#### Successful response

**Status:** `201 Created`

```json
{
  "id": 1,
  "createdAt": "2026-08-02T09:15:00",
  "status": "PENDING",
  "totalPrice": 89.99
}
```

#### Possible errors

- 400 Bad Request - Shopping cart is empty
- 401 Unauthorized - User is not logged in
- 409 Conflict - One or more product variants are no longer available

## Get Order details

`GET /api/orders/{orderId}`

Returns detailed information about an order.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| orderId | Long | ID of the order |

#### Example request

`GET /api/orders/1`

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 1,
  "createdAt": "2026-08-02T09:15:00",
  "status": "PENDING",
  "totalPrice": 199.98,
  "shippingAddress": "Wall Street 21, New York",
  "items": [
    {
      "id": 1,
      "productVariantId": 3,
      "perfume": {
        "id": 1,
        "name": "Sauvage",
        "brand": "Dior",
        "imageUrl": "https://example.com/images/sauvage.jpg"
      },
      "volumeMl": 50,
      "pricePerItem": 89.99,
      "quantity": 1,
      "subtotal": 89.99
    },
    {
      "id": 2,
      "productVariantId": 8,
      "perfume": {
        "id": 5,
        "name": "Le Male Le Parfum",
        "brand": "Jean Paul Gaultier",
        "imageUrl": "https://example.com/images/le-male.jpg"
      },
      "volumeMl": 125,
      "pricePerItem": 109.99,
      "quantity": 1,
      "subtotal": 109.99
    }
  ]
}
```

#### Possible errors

- 401 Unauthorized - User is not logged in
- 404 Not Found - Order does not exist

---

# 9. Product Notifications

## Get Notifications

`GET /api/product-notifications`

Returns all product notifications for the authenticated user.

**Access:** Authenticated user

#### Successful response

**Status:** `200 OK`

```json
[
  {
    "id": 1,
    "type": "PRICE_DROP",
    "active": true,
    "createdAt": "2026-08-01T14:30:00",
    "lastKnownPrice": 129.99,
    "productVariant": {
      "id": 3,
      "volumeMl": 100,
      "currentPrice": 119.99,
      "perfume": {
        "id": 1,
        "name": "Sauvage",
        "brand": "Dior",
        "imageUrl": "https://example.com/images/sauvage.jpg"
      }
    }
  },
  {
    "id": 2,
    "type": "RESTOCK",
    "active": true,
    "createdAt": "2026-08-02T10:15:00",
    "productVariant": {
      "id": 8,
      "volumeMl": 125,
      "currentPrice": 109.99,
      "perfume": {
        "id": 5,
        "name": "Le Male Le Parfum",
        "brand": "Jean Paul Gaultier",
        "imageUrl": "https://example.com/images/le-male.jpg"
      }
    }
  }
]
```

#### Possible errors

- 401 Unauthorized - User is not logged in

## Create Notification

`POST /api/product-notifications`

Creates a new notification for a product.

**Access:** Authenticated user

#### Request body

```json
{
  "productVariantId": 3,
  "type": "PRICE_DROP"
}
```

#### Successful response

**Status:** `201 Created`

```json
{
  "id": 1,
  "type": "PRICE_DROP",
  "active": true,
  "createdAt": "2026-08-01T14:30:00",
  "lastKnownPrice": 119.99,
  "productVariant": {
    "id": 3,
    "volumeMl": 100,
    "currentPrice": 119.99,
    "perfume": {
      "id": 1,
      "name": "Sauvage",
      "brand": "Dior",
      "imageUrl": "https://example.com/images/sauvage.jpg"
    }
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 404 Not Found - Product variant does not exist
- 409 Conflict - Notification already exists

## Update Notification

`PATCH /api/product-notifications/{notificationId}`

Activates or deactivates an existing product notification.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| notificationId | Long | ID of the product notification |

#### Example request

`PATCH /api/product-notifications/1`

#### Request body

```json
{
  "active": false
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 1,
  "type": "PRICE_DROP",
  "active": false,
  "createdAt": "2026-08-01T14:30:00",
  "lastKnownPrice": 129.99,
  "productVariant": {
    "id": 3,
    "volumeMl": 100,
    "currentPrice": 119.99,
    "perfume": {
      "id": 1,
      "name": "Sauvage",
      "brand": "Dior",
      "imageUrl": "https://example.com/images/sauvage.jpg"
    }
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 404 Not Found - Product notification does not exist

## Delete Notification

`DELETE /api/product-notifications/{notificationId}`

Deletes an existing product notification.

**Access:** Authenticated user

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| notificationId | Long | ID of the product notification |

#### Example request

`DELETE /api/product-notifications/1`

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
- 404 Not Found - Product notification does not exist

---

# 10. Administration

## Perfumes

### Create Perfume

`POST /api/admin/perfumes`

Creates a new perfume.

**Access:** Administrator

#### Request body

```json
{
  "name": "Stronger With You Intensely",
  "description": "A warm and cozy fragrance.",
  "releaseYear": 2019,
  "imageUrl": "https://example.com/images/swy-intensely.jpg",
  "brandId": 7,
  "genderId": 1,
  "concentrationId": 4,
  "noteIds": [5, 8],
  "accordIds": [5],
  "seasonIds": [3, 4]
}
```

#### Successful response

**Status:** `201 Created`

```json
{
  "id": 10,
  "name": "Stronger With You Intensely",
  "description": "A warm and cozy fragrance.",
  "releaseYear": 2019,
  "imageUrl": "https://example.com/images/swy-intensely.jpg",
  "brand": {
    "id": 7,
    "name": "Armani"
  },
  "gender": {
    "id": 1,
    "name": "MALE"
  },
  "concentration": {
    "id": 4,
    "name": "EAU_DE_PARFUM"
  },
  "notes": [
    {
      "id": 5,
      "name": "Amber"
    },
    {
      "id": 8,
      "name": "Pepper"
    }
  ],
  "accords": [
    {
      "id": 5,
      "name": "Warm"
    }
  ],
  "seasons": [
    {
      "id": 3,
      "name": "Fall"
    },
    {
      "id": 4,
      "name": "Winter"
    }
  ]
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator
- 404 Not Found - Referenced entity does not exist
- 409 Conflict - Perfume already exists

### Update Perfume

`PUT /api/admin/perfumes/{perfumeId}`

Updates an existing perfume.

**Access:** Administrator

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`PUT /api/admin/perfumes/10`

#### Request body

```json
{
  "name": "Stronger With You Intensely",
  "description": "A warm, sweet and cozy fragrance.",
  "releaseYear": 2019,
  "imageUrl": "https://example.com/images/swy-intensely.jpg",
  "brandId": 7,
  "genderId": 1,
  "concentrationId": 4,
  "noteIds": [5, 8],
  "accordIds": [5, 9],
  "seasonIds": [3, 4]
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 10,
  "name": "Stronger With You Intensely",
  "description": "A warm, sweet and cozy fragrance.",
  "releaseYear": 2019,
  "imageUrl": "https://example.com/images/swy-intensely.jpg",
  "brand": {
    "id": 7,
    "name": "Armani"
  },
  "gender": {
    "id": 1,
    "name": "MALE"
  },
  "concentration": {
    "id": 4,
    "name": "EAU_DE_PARFUM"
  },
  "notes": [
    {
      "id": 5,
      "name": "Amber"
    },
    {
      "id": 8,
      "name": "Pepper"
    }
  ],
  "accords": [
    {
      "id": 5,
      "name": "Warm"
    },
    {
      "id": 9,
      "name": "Sweet"
    }
  ],
  "seasons": [
    {
      "id": 3,
      "name": "Fall"
    },
    {
      "id": 4,
      "name": "Winter"
    }
  ]
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator
- 404 Not Found - Perfume does not exist
- 404 Not Found - Referenced entity does not exist
- 409 Conflict - Updated perfume would duplicate an existing perfume

### Delete Perfume

`DELETE /api/admin/perfumes/{perfumeId}`

Deletes an existing perfume.

**Access:** Administrator

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`DELETE /api/admin/perfumes/10`

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator
- 404 Not Found - Perfume does not exist
- 409 Conflict - Perfume cannot be deleted because it is referenced by existing data

## Product Variants

### Create Product Variant

`POST /api/admin/perfumes/{perfumeId}/variants`

Creates a new product variant for a perfume.

**Access:** Administrator

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| perfumeId | Long | ID of the perfume |

#### Example request

`POST /api/admin/perfumes/10/variants`

#### Request body

```json
{
  "volumeMl": 100,
  "price": 129.99,
  "stock": 10,
  "active": true
}
```

#### Successful response

**Status:** `201 Created`

```json
{
  "id": 51,
  "volumeMl": 100,
  "price": 129.99,
  "stock": 10,
  "active": true,
  "perfume": {
    "id": 10,
    "name": "Stronger With You Intensely"
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator
- 404 Not Found - Perfume does not exist
- 409 Conflict - Product variant with the same volume already exists for this perfume

### Update Product Variant

`PUT /api/admin/product-variants/{variantId}`

Updates an existing product variant.

**Access:** Administrator

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| variantId | Long | ID of the variant |

#### Example request

`PUT /api/admin/product-variants/51`

#### Request body

```json
{
  "volumeMl": 100,
  "price": 124.99,
  "stock": 15,
  "active": true
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 51,
  "volumeMl": 100,
  "price": 124.99,
  "stock": 15,
  "active": true,
  "perfume": {
    "id": 10,
    "name": "Stronger With You Intensely"
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator
- 404 Not Found - Product variant does not exist
- 409 Conflict - Updated variant would duplicate another variant for the same perfume

### Delete Product Variant

`DELETE /api/admin/product-variants/{variantId}`

Deletes an existing product variant.

**Access:** Administrator

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| variantId | Long | ID of the variant |

#### Example request

`DELETE /api/admin/product-variants/51`

#### Successful response

**Status:** `204 No Content`

#### Possible errors

- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator
- 404 Not Found - Product variant does not exist
- 409 Conflict - Product variant cannot be deleted because it is referenced by existing data

## Orders

### Get All Orders

`GET /api/admin/orders`

Returns all customer orders.

**Access:** Administrator

#### Successful response

**Status:** `200 OK`

```json
[
  {
    "id": 1,
    "createdAt": "2026-08-02T09:15:00",
    "status": "PENDING",
    "totalPrice": 199.98,
    "customer": {
      "id": 15,
      "name": "Jane Doe",
      "email": "jane@gmail.com"
    }
  },
  {
    "id": 2,
    "createdAt": "2026-08-03T11:30:00",
    "status": "SHIPPED",
    "totalPrice": 89.99,
    "customer": {
      "id": 28,
      "name": "John Smith",
      "email": "john@gmail.com"
    }
  }
]
```

#### Possible errors

- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator

### Update Order Status

`PATCH /api/admin/orders/{orderId}/status`

Updates the status of an existing order.

**Access:** Administrator

#### Path parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| orderId | Long | ID of the order |

#### Example request

`PATCH /api/admin/orders/1/status`

#### Request body

```json
{
  "status": "PROCESSING"
}
```

#### Successful response

**Status:** `200 OK`

```json
{
  "id": 1,
  "createdAt": "2026-08-02T09:15:00",
  "status": "PROCESSING",
  "totalPrice": 199.98,
  "shippingAddress": "Wall Street 21, New York",
  "customer": {
    "id": 15,
    "name": "Jane Doe",
    "email": "jane@gmail.com"
  }
}
```

#### Possible errors

- 400 Bad Request - Invalid request body
- 401 Unauthorized - User is not logged in
- 403 Forbidden - User is not an administrator
- 404 Not Found - Order does not exist
- 409 Conflict - Invalid order status transition

---

# 11. Error Response Format

All API errors use the following response format:

```json
{
  "timestamp": "2026-08-03T12:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Perfume does not exist",
  "path": "/api/perfumes/999"
}
```