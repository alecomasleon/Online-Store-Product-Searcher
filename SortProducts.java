package item_search;

import java.util.Comparator;

public class SortProducts implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return ((int) o1.getPrice() - (int) o2.getPrice());
    }
}
