# Business Rules

## Users

- Every user must have a unique email address.
- Every user must have a role.
- Passwords must be stored as hashes.
- A user can have only one active cart.

---

## Perfumes

- Every perfume must belong to exactly one Brand.
- Every perfume must belong to exactly one Gender.
- Every perfume must belong to exactly one Concentration.
- Every perfume must have at least one ProductVariant.
- Every perfume must have at least one Note.
- Every perfume must have at least one Accord.
- Every perfume must have at least one Season.

---

## Product Variants

- A product variant must belong to exactly one perfume.
- Volume must be greater than zero.
- Price must be greater than zero.
- Stock cannot be negative.
- A perfume cannot have multiple variants with the same volume.

---

## Reviews

- A user can write only one review per perfume.
- Every review must belong to one user.
- Every review must belong to one perfume.
- Rating must be between 1 and 5.
- Review text is optional.

---

## Wishlist

- A wishlist item cannot exist without a user and a perfume.
- A perfume can appear only once in a user's wishlist.

---

## Collection

- A collection item cannot exist without a user and a perfume.
- A perfume can appear only once in a user's collection.
- Every collection item must have a valid status.

---

## Shopping Cart

- Every cart belongs to one user.
- A user can have only one active cart.
- Every cart item must belong to one cart.
- Every cart item must belong to one product variant.
- Quantity must be greater than zero.
- The same product variant cannot appear multiple times in the same cart.
- Adding an existing product variant increases its quantity.
- Quantity cannot exceed available stock.

---

## Orders

- Every order belongs to one user.
- Every order must contain at least one order item.
- Every order item belongs to one order.
- Every order item belongs to one product variant.
- Shipping address cannot be empty.
- Total price is calculated from all order items.
- Price per item stores the product price at the time of purchase.

---

## Product Notifications

- Every notification belongs to one user.
- Every notification belongs to one product variant.
- A user cannot have duplicate active notifications for the same product variant and notification type.
- Last known price is stored only for price drop notifications.