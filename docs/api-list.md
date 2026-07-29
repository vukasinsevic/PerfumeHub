# List of API Endpoints

This document provides overview of all REST endpoints available in the PerfumeHub API.

Detailed request and response specifications are documented in `api-specification.md`.

## 1. AUTHENTICATION

- `POST /api/auth/register` - Register a new user.
- `POST /api/auth/login` - Authenticate a user.

---

## 2. PERFUMES

- `GET /api/perfumes` - Returns a paginated list of perfumes.
- `GET /api/perfumes/{perfumeId}` - Returns detailed information about a perfume.

---

## 3. REVIEWS

- `GET /api/perfumes/{perfumeId}/reviews` - Returns all reviews for a perfume.
- `POST /api/perfumes/{perfumeId}/reviews` - Creates a new review for a perfume.
- `PUT /api/reviews/{reviewId}` - Updates an existing review.
- `DELETE /api/reviews/{reviewId}` - Deletes a review.

---

## 4. WISHLIST

- `GET /api/wishlist` - Returns the authenticated user's wishlist.
- `POST /api/wishlist/items` - Adds a perfume to the wishlist.
- `DELETE /api/wishlist/items/{perfumeId}` - Removes a perfume from the wishlist.

---

## 5. COLLECTION

- `GET /api/collection` - Returns the authenticated user's perfume collection.
- `POST /api/collection/items` - Adds a perfume to the collection.
- `PATCH /api/collection/items/{perfumeId}` - Updates the collection item status.
- `DELETE /api/collection/items/{perfumeId}` - Removes a perfume from the collection.

---

## 6. CART

- `GET /api/cart` - Returns the authenticated user's shopping cart.
- `POST /api/cart/items` - Adds a product variant to the cart.
- `PATCH /api/cart/items/{cartItemId}` - Updates the quantity of a cart item.
- `DELETE /api/cart/items/{cartItemId}` - Removes an item from the cart.
- `DELETE /api/cart` - Clears the shopping cart.

---

## 7. ORDERS

- `POST /api/orders` - Creates a new order from the current cart.
- `GET /api/orders` - Returns the authenticated user's orders.
- `GET /api/orders/{orderId}` - Returns detailed information about an order.

---

## 8. PRODUCT NOTIFICATIONS

- `GET /api/product-notifications` - Returns the authenticated user's product notifications.
- `POST /api/product-notifications` - Creates a new notification for a product.
- `PATCH /api/product-notifications/{notificationId}` - Updates a product notification.
- `DELETE /api/product-notifications/{notificationId}` - Deletes a product notification.

---

## 9. ADMINISTRATION

### Perfumes

- `POST /api/admin/perfumes` - Creates a new perfume.
- `PUT /api/admin/perfumes/{perfumeId}` - Updates an existing perfume.
- `DELETE /api/admin/perfumes/{perfumeId}` - Deletes a perfume.

### Product Variants

- `POST /api/admin/perfumes/{perfumeId}/variants` - Creates a new product variant.
- `PUT /api/admin/product-variants/{variantId}` - Updates a product variant.
- `DELETE /api/admin/product-variants/{variantId}` - Deletes a product variant.

### Orders

- `GET /api/admin/orders` - Returns all customer orders.
- `PATCH /api/admin/orders/{orderId}/status` - Updates the status of an order.

## Summary

- Authentication endpoints: 2
- Perfume endpoints: 2
- Review endpoints: 4
- Wishlist endpoints: 3
- Collection endpoints: 4
- Cart endpoints: 5
- Order endpoints: 3
- Product Notification endpoints: 4
- Administration endpoints: 8

**Total REST endpoints:** 35