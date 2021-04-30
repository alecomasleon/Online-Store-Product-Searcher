package item_search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        FullScrapeHelper helper = new FullScrapeHelper(5);
        System.out.println("Search in Amazon, Costco, Indigo, and Walmart: ");
        String searchString = sc.nextLine();
        Product[] response = helper.searchAll(searchString);

        System.out.println("Return Length: " + response.length);
        System.out.println("Return: ");
        for (Product p: response) {
            System.out.println(p.toString());
        }
    }
}
