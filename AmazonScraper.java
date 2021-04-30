package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class AmazonScraper extends DriverScraper{
    String nameClass;
    String priceClass;
    String parentClass;

    public AmazonScraper(){
        setInstanceVars();
    }
    public AmazonScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc){
        Elements products = doc.getElementsByClass(parentClass);
        String name;
        float price;
        String link;

        ArrayList<Product> items = new ArrayList<>();
        int count = 0;
        for (Element product: products) {
            if(count == maxItemsPerWebsite){
                break;
            }
            try {
                name = product.getElementsByClass(nameClass).first().text();

                Element fullPriceElement = product.getElementsByClass(priceClass).first();
                //System.out.println(name);

                String priceFull = fullPriceElement.text();
                price = toFloat(priceFull.substring(0, priceFull.length() / 2));

                link = websiteLink + product.getElementsByClass(linkClass).first().attr(linkKey);
                items.add(new Product(name, price, link));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
    }
    private void setInstanceVars(){
        this.name = "amazon";
        this.websiteLinkToSearch = "https://www.amazon.ca/s?k=";
        this.websiteLink = "https://www.amazon.ca/";

        this.parentClass = "a-section a-spacing-medium";
        this.nameClass = "a-size-base-plus a-color-base a-text-normal";
        this.priceClass = "a-price";
        this.linkClass = "a-link-normal a-text-normal";
        this.linkKey = "href";
    }
}
