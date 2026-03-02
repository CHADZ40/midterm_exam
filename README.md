# midterm_exam

This is a shared coding document for the Midterm Exam in **Java Programming I**.

## Project Scope

This project covers the topic **Inventory Management System** with the following flow:

1. **Login System**
   - User must log in so only authorized people can access the system.
   - Uses a **for-loop** to limit login attempts to **3 times** to help prevent brute force or dictionary attacks.

2. **After successful login, user can use these functions:**
   - **View Inventory**: Show all items in stock with **unique ID** and **quantity**.
   - **Add Item**: Add new item name and quantity; system generates a **unique ID**.
   - **Update Stock**: Enter item **unique ID** to add more quantity to that item.
   - **Search Item**: Enter item **unique ID** to see item details.
   - **Remove Item**: Enter item **unique ID** to remove the item completely.
   - **Low Stock Alert**: System alerts when any item stock is low (<= threshold).

---

## Flowchart

```mermaid
flowchart TD
  A([Start]) --> B[Show title]
  B --> C[Login attempts = 1..3 (for loop)]
  C --> D[/Input username + password/]
  D --> E{Credentials correct?}
  E -- Yes --> F[Login successful]
  E -- No --> G[Show incorrect + attempts left]
  G --> H{Attempts remaining?}
  H -- Yes --> D
  H -- No --> Z([End: Access denied])

  F --> I[Seed sample items (optional)]
  I --> J[Show Menu]
  J --> K[/Input choice/]

  K --> L{Choice?}

  L -- "1 View Inventory" --> M[Display all items: ID, Name, Quantity]
  M --> P[Press Enter] --> J

  L -- "2 Add Item" --> N[/Input item name + quantity/]
  N --> N1[Generate unique 6-digit ID]
  N1 --> N2[Add item to inventory]
  N2 --> P --> J

  L -- "3 Update Stock" --> O[/Input item ID/]
  O --> O1{ID exists?}
  O1 -- No --> O2[Show 'Item not found']
  O2 --> P --> J
  O1 -- Yes --> O3[/Input quantity to ADD/]
  O3 --> O4[Increase item quantity]
  O4 --> P --> J

  L -- "4 Search Item" --> Q[/Input item ID/]
  Q --> Q1{ID exists?}
  Q1 -- No --> Q2[Show 'Item not found']
  Q2 --> P --> J
  Q1 -- Yes --> Q3[Display item details]
  Q3 --> P --> J

  L -- "5 Remove Item" --> R[/Input item ID/]
  R --> R1{ID exists?}
  R1 -- No --> R2[Show 'Item not found']
  R2 --> P --> J
  R1 -- Yes --> R3[Remove item from inventory]
  R3 --> P --> J

  L -- "6 Low Stock Alert" --> S[Check all items]
  S --> S1{Any item qty <= threshold?}
  S1 -- Yes --> S2[Display low stock items]
  S2 --> P --> J
  S1 -- No --> S3[Show 'No low stock items']
  S3 --> P --> J

  L -- "0 Exit" --> T([End: Goodbye])
