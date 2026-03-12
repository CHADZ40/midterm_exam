import java.util.*;

public class IMS3 {

    // ======= LOGIN CONFIG =======
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    // ======= LOW STOCK CONFIG =======
    private static final int LOW_STOCK_THRESHOLD = 5;

    // ======= INVENTORY STORAGE (NO HASHMAP) =======
    private static final List<Item> inventory = new ArrayList<>();
    private static int idCounter = 1000;

    // ======= ITEM MODEL =======
    private static class Item {
        private final String id;
        private final String name;
        private int quantity;

        Item(String id, String name, int quantity) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
        }

        String getId() { return id; }
        String getName() { return name; }
        int getQuantity() { return quantity; }

        void addQuantity(int amount) {
            quantity += amount;
        }

        @Override
        public String toString() {
            return String.format("ID: %-6s | Name: %-15s | Qty: %-3d", id, name, quantity);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        seedDemoItems();

        if (!login(sc)) {
            System.out.println("Too many failed attempts. Exiting...");
            sc.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n===== INVENTORY MANAGEMENT SYSTEM =====");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Item");
            System.out.println("3. Update Stock (Add/Subtract)");
            System.out.println("4. Search Item");
            System.out.println("5. Remove Item");
            System.out.println("6. Low Stock Alert");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    viewInventory();
                    break;
                case 2:
                    addItem(sc);
                    break;
                case 3:
                    updateStock(sc);
                    break;
                case 4:
                    searchItem(sc);
                    break;
                case 5:
                    removeItem(sc);
                    break;
                case 6:
                    lowStockAlert();
                    break;
                case 7:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-7.");
            }
        }

        sc.close();
    }

    // ======= LOGIN =======
    private static boolean login(Scanner sc) {
        System.out.println("===== LOGIN =====");
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            System.out.print("Username: ");
            String user = sc.nextLine().trim();

            System.out.print("Password: ");
            String pass = sc.nextLine().trim();

            if (USERNAME.equals(user) && PASSWORD.equals(pass)) {
                System.out.println("Login successful!");
                return true;
            } else {
                int remaining = MAX_LOGIN_ATTEMPTS - attempt;
                System.out.println("Incorrect credentials.");
                if (remaining > 0) System.out.println("Attempts remaining: " + remaining);
            }
        }
        return false;
    }

    // ======= FEATURES =======
    private static void viewInventory() {
    System.out.println("\n----- VIEW INVENTORY -----");
    if (inventory.isEmpty()) {
        System.out.println("Inventory is empty.");
        return;
    }

    System.out.printf("%-10s | %-15s | %-5s%n", "ID", "Name", "Qty");
    System.out.println("-----------------------------------");

    for (Item item : inventory) {
        System.out.printf("%-10s | %-15s | %-5d%n",
                item.getId(), item.getName(), item.getQuantity());
    }
}


private static void addItem(Scanner sc) {
    System.out.println("\n----- ADD ITEM -----");

    System.out.print("Enter item name: ");
    String name = sc.nextLine().trim();

    // Check if name is empty
    if (name.isEmpty()) {
        System.out.println("Item name cannot be empty.");
        return;
    }

    // Check if item already exists in inventory
    for (Item item : inventory) {
        if (item.getName().equalsIgnoreCase(name)) {
            System.out.println("Item already exists in the inventory:");
            System.out.println(item);
            return;
        }
    }

    System.out.print("Enter quantity: ");
    int qty = readInt(sc);

    // Validate quantity
    if (qty < 0) {
        System.out.println("Quantity cannot be negative.");
        return;
    }

    // Generate new ID and add item
    String newId = generateItemId();
    Item newItem = new Item(newId, name, qty);
    inventory.add(newItem);

    System.out.println("Item added successfully!");
    System.out.println(newItem);
}

    private static void updateStock(Scanner sc) {
        System.out.println("\n----- UPDATE STOCK -----");
        System.out.print("Enter item ID: ");
        String id = sc.nextLine().trim();

        Item item = findItemById(id);
        if (item == null) {
            System.out.println("Item not found for ID: " + id);
            return;
        }

        System.out.println("Current: " + item);
        System.out.println("1. Add stock");
        System.out.println("2. Subtract stock");
        System.out.print("Choose: ");
        int option = readInt(sc);

        if (option == 1) {
            System.out.print("Enter quantity to add: ");
            int qty = readInt(sc);
            if (qty <= 0) {
                System.out.println("Enter a positive number.");
                return ;
            }
            item.addQuantity(qty);
            System.out.println("Updated: " + item);

        }else if (option == 2) {
            System.out.print("Enter quantity to subtract: ");
            int qty = readInt(sc);
            if (qty <= 0) {
                System.out.println("Enter a positive number.");
                return;
            }
            if (qty > item.getQuantity()) {
                System.out.println("Not enough stock! Available: " + item.getQuantity());
                return;
            }
            item.addQuantity(-qty);
            System.out.println("Updated: " + item);

        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void searchItem(Scanner sc) {
    System.out.println("\n----- SEARCH ITEM -----");
    System.out.print("Enter item ID or item name/keyword: ");
    String input = sc.nextLine().trim();

    if (input.isEmpty()) {
        System.out.println("Search input cannot be empty.");
        return;
    }

    boolean found = false;

    for (Item item : inventory) {
        if (item.getId().equalsIgnoreCase(input) ||
            item.getName().toLowerCase().contains(input.toLowerCase())) {

            if (!found) {
                System.out.println("Matching item(s):");
            }
            System.out.println(item);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No items found matching: " + input);
    }
}

    private static void removeItem(Scanner sc) {
    System.out.println("\n----- REMOVE ITEM -----");
    System.out.print("Enter item ID to remove: ");
    String id = sc.nextLine().trim();

    Item itemToRemove = findItemById(id);

    if (itemToRemove == null) {
        System.out.println("Item not found for ID: " + id);
        return;
    }

    System.out.println("Item found:");
    System.out.println(itemToRemove);

    System.out.print("Are you sure you want to remove this item? (Y/N): ");
    String confirm = sc.nextLine().trim();

    if (confirm.equalsIgnoreCase("Y")) {
        inventory.remove(itemToRemove);
        System.out.println("Item removed successfully!");
    } else if (confirm.equalsIgnoreCase("N")) {
        System.out.println("Remove operation cancelled.");
    } else {
        System.out.println("Invalid choice. Remove operation cancelled.");
    }
}

    private static void lowStockAlert() {
        System.out.println("\n----- LOW STOCK ALERT (<= " + LOW_STOCK_THRESHOLD + ") -----");
        boolean found = false;

        for (Item item : inventory) {
            if (item.getQuantity() <= LOW_STOCK_THRESHOLD) {
                System.out.println("LOW STOCK -> " + item);
                found = true;
            }
        }

        if (!found) System.out.println("No low stock items right now.");
    }

    // ======= HELPERS =======
    private static Item findItemById(String id) {
        for (Item item : inventory) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    private static String generateItemId() {
        idCounter++;
        return String.valueOf(idCounter);
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }

    private static void seedDemoItems() {
        inventory.add(new Item(generateItemId(), "Rice", 12));
        inventory.add(new Item(generateItemId(), "Sugar", 4));
        inventory.add(new Item(generateItemId(), "Soap", 2));
        inventory.add(new Item(generateItemId(), "Book", 10));
        inventory.add(new Item(generateItemId(), "Apple", 6));
        inventory.add(new Item(generateItemId(), "Phone", 7));
        inventory.add(new Item(generateItemId(), "Airpod", 10));
    }
}
