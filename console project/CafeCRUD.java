import java.util.*;
import com.mongodb.client.*;
import org.bson.Document;

class Order {
    int id;
    String itemName;
    int quantity;
    int price;

    Order(int id, String itemName, int quantity, int price) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
public class CafeCRUD {

    static HashMap<String, Integer> menu = new HashMap<>();
    static ArrayList<Order> orders = new ArrayList<>();
    static int orderId = 1;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Menu Items
        menu.put("Coffee", 50);
        menu.put("Tea", 20);
        menu.put("Sandwich", 80);
        menu.put("Juice", 60);

        int choice;

        do {
            System.out.println("\n====== CAFE ORDER & BILLING SYSTEM ======");
            System.out.println("1. Create Order");
            System.out.println("2. View Orders");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("5. Generate Bill");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createOrder(sc);
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    updateOrder(sc);
                    break;
                case 4:
                    deleteOrder(sc);
                    break;
                case 5:
                    generateBill();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    // CREATE
    static void createOrder(Scanner sc) {
        System.out.println("Available Menu Items:");
        for (String item : menu.keySet()) {
            System.out.println(item + " - Rs." + menu.get(item));
        }

        System.out.print("Enter item name: ");
        String item = sc.next();

        if (!menu.containsKey(item)) {
            System.out.println("Invalid item!");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        int price = menu.get(item) * qty;

        orders.add(new Order(orderId++, item, qty, price));
        System.out.println("Order added successfully!");
    }

    // READ
    static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders yet!");
            return;
        }

        System.out.println("\n----- ORDER LIST -----");
        for (Order o : orders) {
            System.out.println("ID: " + o.id + 
                " | Item: " + o.itemName + 
                " | Qty: " + o.quantity + 
                " | Price: Rs." + o.price);
        }
    }

    // UPDATE
    static void updateOrder(Scanner sc) {
        viewOrders();

        System.out.print("Enter Order ID to update: ");
        int id = sc.nextInt();

        for (Order o : orders) {
            if (o.id == id) {
                System.out.print("Enter new quantity: ");
                int newQty = sc.nextInt();

                o.quantity = newQty;
                o.price = newQty * menu.get(o.itemName);

                System.out.println("Order updated!");
                return;
            }
        }

        System.out.println("Order ID not found!");
    }

    // DELETE
    static void deleteOrder(Scanner sc) {
        viewOrders();

        System.out.print("Enter Order ID to delete: ");
        int id = sc.nextInt();

        Iterator<Order> it = orders.iterator();
        while (it.hasNext()) {
            Order o = it.next();
            if (o.id == id) {
                it.remove();
                System.out.println("Order deleted!");
                return;
            }
        }

        System.out.println("Order ID not found!");
    }

    // BILLING
    static void generateBill() {
        if (orders.isEmpty()) {
            System.out.println("No orders to bill!");
            return;
        }

        int total = 0;
        System.out.println("\n=========== BILL ===========");
        for (Order o : orders) {
            System.out.println(o.itemName + " x " + o.quantity + " = Rs." + o.price);
            total += o.price;
        }
        System.out.println("-----------------------------");
        System.out.println("TOTAL AMOUNT: Rs." + total);
        System.out.println("=============================");
    }
}
