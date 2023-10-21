import java.util.Scanner;
import java.util.Random;

class Menu {
   String name;
   double price;
   String category;

   /**
    * @param name
    * @param price
    * @param category
    */
   public Menu(String name, double price, String category) {
      this.name = name;
      this.price = price;
      this.category = category;
   }
}

public class Main {
   /**
    * @param args
    */
   public static void main(String[] args) {
      Menu[] menuList = {
            new Menu("Nasi Padang", 25000, "makanan"),
            new Menu("Ayam Goreng", 20000, "makanan"),
            new Menu("Es Teh Manis", 5000, "minuman"),
            new Menu("Sate Ayam", 30000, "makanan"),
            new Menu("Es Jeruk", 6000, "minuman"),
            new Menu("Mie Goreng", 22000, "makanan"),
            new Menu("Nasi Goreng", 23000, "makanan"),
            new Menu("Kopi Hitam", 8000, "minuman")
      };

      Scanner scanner = new Scanner(System.in);

      int[] order = new int[menuList.length];
      int selectedIndex = -1;
      double totalBill = 0;
      StringBuilder beverageDiscountReceipt = new StringBuilder();

      while (true) {
         System.out.println("===============================================================");
         System.out.println("Daftar Menu:");
         System.out.println("---------------------------------------------------------------");
         System.out.println("No.   Nama Menu         Harga    Pesanan   Subtotal");
         System.out.println("---------------------------------------------------------------");
         for (int i = 0; i < menuList.length; i++) {
            String menuText = String.format("%-4s %-15s Rp %-8.2f x%-9d Rp %-8.2f",
                  i + 1, menuList[i].name, menuList[i].price, order[i], (menuList[i].price * order[i]));
            System.out.println(menuText);
         }
         System.out.println("---------------------------------------------------------------");

         if (selectedIndex == -1) {
            System.out.println("Pilih menu berdasarkan angka di atas, jika tidak jadi memesan klik tombol 'Q'");
         }

         String input = scanner.nextLine();

         if (input.equals("q")) {
            System.out.println("Yah sayang sekali, kami tunggu pemesanan lain :)");
            break;
         }

         try {
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= menuList.length) {
               selectedIndex = choice - 1;
               System.out.print("Masukkan jumlah pesanan: ");
               int quantity = scanner.nextInt();
               order[selectedIndex] += quantity;
               scanner.nextLine();
               System.out.print("Apakah ingin memesan menu lagi? (Ya atau tidak, 1 atau 0): ");
               String anotherOrder = scanner.nextLine();
               if (anotherOrder.equalsIgnoreCase("tidak") || anotherOrder.equals("0")) {
                  scanner.close();
                  break;
               }
            } else {
               System.out.println("Pilihan tidak valid.");
            }
         } catch (NumberFormatException e) {
            System.out.println("Pilihan tidak valid.");
         }
      }

      for (int i = 0; i < menuList.length; i++) {
         totalBill += menuList[i].price * order[i];
      }

      double discount = 0;
      double tax = 0.1 * totalBill;
      double serviceCharge = 20000;

      if (totalBill > 100000) {
         discount = 0.1 * totalBill;
      }

      boolean hasMinumanGratis = false;
      for (int i = 0; i < menuList.length; i++) {
         if (menuList[i].category.equals("minuman") && order[i] > 0) {
            hasMinumanGratis = true;
            break;
         }
      }

      if (totalBill > 50000 && hasMinumanGratis) {
         int randomMinumanIndex = -1;
         while (randomMinumanIndex == -1) {
            int index = new Random().nextInt(menuList.length);
            if (menuList[index].category.equals("minuman") && order[index] > 0) {
               randomMinumanIndex = index;
            }
         }

         order[randomMinumanIndex] -= 1;
         beverageDiscountReceipt.append("Selamat! Anda mendapat ").append(menuList[randomMinumanIndex].name)
               .append(" 1 Pcs secara gratis... ");
      }

      totalBill = totalBill - discount + tax + serviceCharge;
      printReceipt(menuList, order, totalBill, discount, tax, serviceCharge, beverageDiscountReceipt.toString());
   }

   /**
    * @param menuList
    * @param order
    * @param totalBill
    * @param discount
    * @param tax
    * @param serviceCharge
    * @param beverageDiscountReceipt
    */
   public static void printReceipt(Menu[] menuList, int[] order, double totalBill, double discount, double tax,
         double serviceCharge, String beverageDiscountReceipt) {
      System.out.println("\nStruk Pesanan:");

      String namaWarung = "WARUNG YUSUF KULIAH";
      String alamatWarung = "Jl. Malioboro Simpang 4 No. 30";

      int centerOffset = (int) (40 - namaWarung.length() / 2);
      System.out.println(String.format("%" + centerOffset + "s%s", "", namaWarung));

      centerOffset = (int) (40 - alamatWarung.length() / 2);
      System.out.println(String.format("%" + centerOffset + "s%s", "", alamatWarung));

      System.out.println("==============================================================================");
      System.out.println("No.   Nama Menu         Harga/Item     Jumlah     Subtotal");
      System.out.println("==============================================================================");
      for (int i = 0; i < menuList.length; i++) {
         if (order[i] > 0) {
            System.out.println(String.format("%-4s  %-16s  Rp %-10.2f  x%-8d  Rp %-10.2f",
                  i + 1, menuList[i].name, menuList[i].price, order[i], (menuList[i].price * order[i])));
         }
      }
      System.out.println("-----------------------------------------------------------------------------");
      if (!beverageDiscountReceipt.isEmpty()) {
         System.out.println(beverageDiscountReceipt);
      }
      if (discount > 0) {
         System.out.println(String.format("Diskon (10%%):              -Rp %.2f", discount));
      }
      System.out.println(String.format("Pajak (10%%):                Rp %.2f", tax));
      System.out.println(String.format("Biaya Pelayanan:            Rp %.2f", serviceCharge));
      System.out.println("-----------------------------------------------------------------------------");
      System.out.println(String.format("Total Biaya Pesanan:        Rp %.2f", totalBill));
      System.out.println("==============================================================================");
   }

}
