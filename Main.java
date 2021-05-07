package item_search;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        go();
    }
    private static void go(){
        Scanner sc = new Scanner(System.in);
        FullScrapeHelper helper = new FullScrapeHelper(5);
        System.out.println("Type what you want to search:  ");
        String searchString = sc.nextLine();
        System.out.println("Type as many stores as desired from the list (with enters in between): ");
        System.out.println("amazon, costco, indigo, bestbuy, ebay, google, walmart, homedepot");
        System.out.println("Type 'done' when all desired stores are typed in");
        System.out.println("Type 'all' to search all stores");

        ArrayList<String> stores = new ArrayList<>();
        String typed;
        Product[] response;
        while(true){
            typed = sc.nextLine();
            if(typed.equals("done")){
                response = helper.searchCertain(searchString, stores.toArray(new String[0]));
                break;
            }else if(typed.equals("all")){
                response = helper.searchAll(searchString);
                break;
            }
            stores.add(typed);
        }

        System.out.println("Return Length: " + response.length);
        System.out.println("Return: ");
        for (Product p: response) {
            System.out.println(p.toString());
        }
    }
    private static void retiredGo(){
        Scanner sc = new Scanner(System.in);
        FullScrapeHelper helper = new FullScrapeHelper(5);
        System.out.println("Search in Amazon, Costco, Indigo, Best Buy, Ebay, Google Shopping and Walmart: ");
        String searchString = sc.nextLine();
        Product[] response = helper.searchAll(searchString);

        System.out.println("Return Length: " + response.length);
        System.out.println("Return: ");
        for (Product p: response) {
            System.out.println(p.toString());
        }
    }
}
