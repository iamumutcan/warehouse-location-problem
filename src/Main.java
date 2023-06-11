import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File dosya = new File("src/wl_16_1");
            Scanner scanner = new Scanner(dosya);

            int numWarehouses = scanner.nextInt();

            int numCustomers = scanner.nextInt();

            int[] warehouseCapacities = new int[numWarehouses];
            double[] warehouseSetupCosts = new double[numWarehouses];
            int[] customerDemands = new int[numCustomers];
            double[][] customerCosts = new double[numCustomers][numWarehouses];

           // System.out.println(numWarehouses+" "+numCustomers);
            for (int i = 0; i < numWarehouses; i++) {
                warehouseCapacities[i] = scanner.nextInt();
                warehouseSetupCosts[i] =  Double.parseDouble(scanner.next());
              //  System.out.println(warehouseCapacities[i]+" "+warehouseSetupCosts[i]);
            }



            for (int i = 0; i < numCustomers; i++) {
                customerDemands[i] = scanner.nextInt();
              // System.out.println(customerDemands[i]);
                for (int j=0;j<numWarehouses;j++){
                    customerCosts[i][j] = Double.parseDouble(scanner.next());
                 //   System.out.print(customerCosts[i][j] + " ");
                }
              //  System.out.println(" ");
            }

            double toplam=customerCosts[1][1]+warehouseSetupCosts[1];
          //  System.out.println("dm="+customerCosts[1][1]+" dkm= "+warehouseSetupCosts[1] +" toplam= " + toplam);
            // Depo yerleşim problemini çözen algoritmayı buraya yazabilirsiniz
            // Algoritma sonucunda en uygun yerleşim ve maliyeti bulunabilir

            scanner.close();
            int[][] warehouseAssignments = assignWarehouses(numWarehouses, numCustomers, warehouseCapacities, warehouseSetupCosts, customerDemands, customerCosts);
            double totalCost = calculateTotalCost(warehouseAssignments, customerCosts, warehouseSetupCosts);


            System.out.println("Total cost: " + totalCost);
            for (int i = 0; i < numCustomers; i++) {
                System.out.print( (warehouseAssignments[i][0] ) + " ");
            }
           /* for (int i = 0; i < numCustomers; i++) {
                System.out.println("Customer " + (i + 1) + " is assigned to Warehouse " + (warehouseAssignments[i][0] ));
                System.out.println("Transportation cost for Customer " + (i + 1) + ": " + customerCosts[i][warehouseAssignments[i][0]]);
                System.out.println();
            }*/


        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + e.getMessage());
        }
    }
    public static int[][] assignWarehouses(int numWarehouses, int numCustomers, int[] warehouseCapacities, double[] warehouseSetupCosts, int[] customerDemands, double[][] customerCosts) {
        int[][] warehouseAssignments = new int[numCustomers][1];
        int[] warehouseCapacitiesCopy = Arrays.copyOf(warehouseCapacities, numWarehouses);

        for (int i = 0; i < numCustomers; i++) {
            int assignedWarehouse = -1;
            double minCost = Double.MAX_VALUE;

            for (int j = 0; j < numWarehouses; j++) {
                if (warehouseCapacitiesCopy[j] >= customerDemands[i]) {
                    if (customerCosts[i][j] + warehouseSetupCosts[j] < minCost) {
                        assignedWarehouse = j;
                        minCost = customerCosts[i][j] + warehouseSetupCosts[j];
                    }
                }
            }

            if (assignedWarehouse != -1) {
                warehouseCapacitiesCopy[assignedWarehouse] -= customerDemands[i];
            }

            warehouseAssignments[i][0] = assignedWarehouse;
        }

        return warehouseAssignments;
    }

    public static double calculateTotalCost(int[][] warehouseAssignments, double[][] customerCosts, double[] warehouseSetupCosts) {
        double totalCost = 0.0;
        int numCustomers = warehouseAssignments.length;

        for (int i = 0; i < numCustomers; i++) {
            int assignedWarehouse = warehouseAssignments[i][0];
            totalCost += customerCosts[i][assignedWarehouse];
        }

        for (int i = 0; i < warehouseSetupCosts.length; i++) {
            if (warehouseAssignments[i][0] != -1) {
                totalCost += warehouseSetupCosts[i];
            }
        }

        return totalCost;
    }
}
