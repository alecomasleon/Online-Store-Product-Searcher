package item_search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        FullScrapeHelper helper = new FullScrapeHelper();
        System.out.println("Search in Costco, Indigo, and Walmart: ");
        String searchString = sc.nextLine();
        String[] response = helper.searchAll(searchString);

        System.out.println("Return Length: " + response.length);
        System.out.println("Return: ");
        for (String url: response) {
            System.out.println(url);
        }
    }
}
