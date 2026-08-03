# Backend Architecture

## Overview

PerfumeHub follows a layered backend architecture.

The application is divided into the following layers:

- Controller layer
- Service layer
- Repository layer
- Domain entity layer
- DTO layer
- Mapper layer
- Security layer
- Exception handling layer

The typical request flow is:

Controller -> Service -> Repository -> Database

Responses are returned through DTO objects rather than exposing entities directly.
Each layer has a single responsibility and communicates only with adjacent layers.

---

## Controllers

### Public and User Controllers

- AuthenticationController
- PerfumeController
- ReviewController
- WishlistController
- CollectionController
- CartController
- OrderController
- ProductNotificationController

### Administration Controllers

- AdminPerfumeController
- AdminProductVariantController
- AdminOrderController

---

## Services

- AuthenticationService
- PerfumeService
- ReviewService
- WishlistService
- CollectionService
- CartService
- OrderService
- ProductNotificationService
- ProductVariantService
- AdminOrderService

### Service Responsibilities

- Controllers must not contain business logic.
- Services validate business rules and coordinate repository operations.
- Transactional operations are handled in the service layer.

### Transactional Operations

The following operations should be transactional:

- Creating an order
- Updating stock during order creation
- Clearing the cart after order creation
- Creating or updating a perfume and its relationships (notes, accords and seasons)

---

## Repositories

- PerfumeRepository
- BrandRepository
- GenderRepository
- ConcentrationRepository
- NoteRepository
- AccordRepository
- SeasonRepository
- ReviewRepository
- WishlistItemRepository
- CollectionItemRepository
- AppUserRepository
- CartRepository
- CartItemRepository
- ProductVariantRepository
- CustomerOrderRepository
- OrderItemRepository
- ProductNotificationRepository

---

## Entities

- Perfume
- Brand
- Gender
- Concentration
- Note
- Accord
- Season
- Review
- WishlistItem
- CollectionItem
- AppUser
- Cart
- CartItem
- ProductVariant
- CustomerOrder
- OrderItem
- ProductNotification

---

## Enums

- UserRole
- CollectionStatus
- OrderStatus
- NotificationType

---

## DTOs

### Request DTOs

- RegisterRequest
- LoginRequest
- CreateReviewRequest
- UpdateReviewRequest
- AddWishlistItemRequest
- AddCollectionItemRequest
- UpdateCollectionStatusRequest
- AddCartItemRequest
- UpdateCartItemQuantityRequest
- CreateProductNotificationRequest
- UpdateProductNotificationRequest
- CreatePerfumeRequest
- UpdatePerfumeRequest
- CreateProductVariantRequest
- UpdateProductVariantRequest
- UpdateOrderStatusRequest

### Response DTOs

- AuthenticationResponse
- UserResponse
- PerfumeSummaryResponse
- PerfumeDetailsResponse
- ReviewResponse
- WishlistItemResponse
- CollectionItemResponse
- CartResponse
- CartItemResponse
- OrderSummaryResponse
- OrderDetailsResponse
- OrderItemResponse
- ProductNotificationResponse
- ProductVariantResponse
- ErrorResponse

---

## Mappers

- PerfumeMapper
- ReviewMapper
- WishlistMapper
- CollectionMapper
- CartMapper
- OrderMapper
- ProductNotificationMapper
- ProductVariantMapper
- UserMapper

---

## Security

- SecurityConfig
- JwtService
- JwtAuthenticationFilter
- CustomUserDetailsService
- CustomUserDetails
- AuthenticationEntryPoint
- AccessDeniedHandler
- PasswordEncoder

### Security Responsibilities

- Authenticate users using email and password.
- Generate and validate JWT access tokens.
- Load users from the database.
- Protect authenticated endpoints.
- Restrict administration endpoints to users with the ADMIN role.

---

## Exception Handling

### Custom Exceptions

- ResourceNotFoundException
- DuplicateResourceException
- InvalidRequestException
- InsufficientStockException
- ForbiddenOperationException
- InvalidOrderStatusTransitionException

### Global Handler

- GlobalExceptionHandler

---

## Filtering and Pagination

### Components

- PerfumeSpecification
- PerfumeSearchCriteria

### Responsibilities

- `PerfumeSearchCriteria` stores all filtering parameters received from the request.
- `PerfumeSpecification` dynamically builds database queries based on the provided search criteria.
- Pagination and sorting are handled using Spring Data `Pageable`.

Example filtering criteria include:

- search
- brandId
- genderId
- concentrationId
- noteId
- accordId
- seasonId
- minPrice
- maxPrice

---

## Validation

Request DTOs are validated using Jakarta Bean Validation.

Examples of validation annotations:

- `@NotNull`
- `@NotBlank`
- `@Email`
- `@Size`
- `@Min`
- `@Max`
- `@Positive`
- `@PositiveOrZero`

---

## Suggested Package Structure

```text
com.perfumehub
├── config
├── controller
│   ├── admin
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
├── service
├── specification
└── validation
```
