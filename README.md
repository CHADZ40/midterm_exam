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
  B --> C[Login attempts 1 to 3]
  C --> D[/Input username and password/]
  D --> E{Credentials correct?}
  E -- Yes --> F[Login successful]
  E -- No --> G[Show incorrect and attempts left]
  G --> H{Attempts remaining?}
  H -- Yes --> D
  H -- No --> Z([End: Access denied])

  F --> I[Show menu]
  I --> J[/Input choice/]
  J --> K{Choice?}

  K -- 1 --> L[View inventory]
  L --> In[Show items]
  In --> I

  K -- 2 --> M[Add item]
  M --> M1[/Input name and quantity/]
  M1 --> M2[Generate unique ID]
  M2 --> M3[Save item]
  M3 --> I

  K -- 3 --> N[Update stock]
  N --> N1[/Input item ID/]
  N1 --> N2{ID exists?}
  N2 -- No --> N3[Show not found]
  N2 -- Yes --> N4[/Input quantity to add/]
  N4 --> N5[Increase quantity]
  N5 --> I
  N3 --> I

  K -- 4 --> O[Search item]
  O --> O1[/Input item ID/]
  O1 --> O2{ID exists?}
  O2 -- No --> O3[Show not found]
  O2 -- Yes --> O4[Display item details]
  O3 --> I
  O4 --> I

  K -- 5 --> P[Remove item]
  P --> P1[/Input item ID/]
  P1 --> P2{ID exists?}
  P2 -- No --> P3[Show not found]
  P2 -- Yes --> P4[Remove item]
  P3 --> I
  P4 --> I

  K -- 6 --> Q[Low stock alert]
  Q --> Q1[Check all items]
  Q1 --> Q2{Any item low?}
  Q2 -- Yes --> Q3[Display low stock items]
  Q2 -- No --> Q4[Show all good]
  Q3 --> I
  Q4 --> I

  K -- 0 --> R([End: Goodbye])
