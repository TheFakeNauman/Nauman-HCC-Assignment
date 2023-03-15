package com.example.inventory;

import java.util.*;

public class GizmoInventory {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Gizmo> inventory = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize the inventory
        inventory.add(new Gizmo("Waxbill", 618001, 2015, 3, 499.99));
        inventory.add(new Gizmo("Heron", 618002, 2016, 13, 299.99));
        inventory.add(new Gizmo("Crane", 618003, 2017, 195, 49.99));
        inventory.add(new Gizmo("Duck", 618004, 2018, 295, 99.99));
        inventory.add(new Gizmo("Wallaby", 618005, 2019, 973, 149.99));
        inventory.add(new Gizmo("Egret", 618006, 2020, 495, 199.99));

        boolean quit = false;
        while (!quit) {
            System.out.println("What do you want to do?");
            System.out.println("1. Add a new entry");
            System.out.println("2. Remove an entry");
            System.out.println("3. Sort the list by year");
            System.out.println("4. Sort the list by price");
            System.out.println("5. Sort the list by quantity");
            System.out.println("6. Calculate and print the total value of the inventory");
            System.out.println("7. Print inventory");
            System.out.println("8. Print inventory in table format");
            System.out.println("9. Quit");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addEntry(scanner);
                        break;
                    case 2:
                        removeEntry(scanner);
                        break;
                    case 3:
                        sortByYear();
                        break;
                    case 4:
                        sortByPrice();
                        break;
                    case 5:
                        sortByQuantity();
                        break;
                    case 6:
                        calculateTotalValue();
                        break;
                    case 7:
                        printInventory();
                        break;
                    case 8:
                        printInventoryTable();
                        break;
                    case 9:
                        quit = true;

                        break;
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void printInventory() {
        System.out.printf("%-12s %-12s %-6s %-8s %s\n", "Product Name", "Product Number", "Year", "Quantity", "Price ($)");
        for (Gizmo gizmo : inventory) {
            System.out.printf("%-12s %-12d %-6d %-8d $%.2f\n", gizmo.getProductName(), gizmo.getProductNumber(),
                    gizmo.getYear(), gizmo.getQuantity(), gizmo.getPrice());
        }
    }


    private static void calculateTotalValue() {
        double totalValue = inventory.stream()
                .mapToDouble(gizmo -> gizmo.getQuantity() * gizmo.getPrice())
                .sum();
        System.out.printf("Total value of inventory: $%.2f\n", totalValue);
    }


    private static void sortByQuantity() {
        inventory.sort(Comparator.comparing(Gizmo::getQuantity));
        System.out.println("Inventory sorted by quantity in ascending order:");
        printInventory();
    }


    private static void removeEntry(Scanner scanner) {
        System.out.print("Enter the product number of the Gizmo to remove: ");
        int productNumber = Integer.parseInt(scanner.nextLine());
        boolean removed = inventory.removeIf(gizmo -> gizmo.getProductNumber() == productNumber);
        if (removed) {
            System.out.println("Removed the Gizmo with product number " + productNumber);
        } else {
            System.out.println("No Gizmo found with product number " + productNumber);
        }
    }


    private static void sortByYear() {
        inventory.sort(Comparator.comparing(Gizmo::getYear));
        System.out.println("Inventory sorted by year in ascending order:");
        printInventory();
    }


    private static void sortByPrice() {
        inventory.sort(Comparator.comparing(Gizmo::getPrice));
        System.out.println("Inventory sorted by price in ascending order:");
        printInventory();
    }


    private static void addEntry(Scanner scanner) {
        System.out.println("Enter the details of the Gizmo to add:");
        System.out.print("Product name: ");
        String productName = scanner.nextLine();
        System.out.print("Product number: ");
        int productNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Price ($ USD): ");
        double price = Double.parseDouble(scanner.nextLine());
        Gizmo gizmo = new Gizmo(productName, productNumber, year, quantity, price);
        inventory.add(gizmo);
        System.out.println("Added the following Gizmo to inventory:");
        System.out.println(gizmo);
    }

    private static void printInventoryTable() {
        System.out.format("+------------+------------+------+----------+--------+%n");
        System.out.format("| Product    | Product    | Year | Quantity | Price  |%n");
        System.out.format("| Name       | Number     |      |          | ($ USD)|%n");
        System.out.format("+------------+------------+------+----------+--------+%n");
        for (Gizmo gizmo : inventory) {
            System.out.format("| %-10s | %10d | %4d | %8d | %6.2f |%n",
                    gizmo.getProductName(), gizmo.getProductNumber(),
                    gizmo.getYear(), gizmo.getQuantity(), gizmo.getPrice());
        }
        System.out.format("+------------+------------+------+----------+--------+%n");
    }

}


