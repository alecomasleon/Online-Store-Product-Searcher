package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class AmazonScraper extends DriverScraper{
    private static final String[] parentClasses = new String[]{"a-section a-spacing-medium", "a-section a-spacing-medium a-text-center"};
    private static final String nameClass = "a-size-base-plus a-color-base a-text-normal";
    private static final String priceClass = "a-offscreen";
    private static final String linkClass = "a-link-normal a-text-normal";
    private static final String linkKey = "href";
    private static final String imageClass = "s-image";
    private static final String imageKey = "src";

    public AmazonScraper(){
        setInstanceVars();
    }
    public AmazonScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc){
        //System.out.println(doc);
        Elements products = doc.getElementsByClass(parentClasses[0]);
        //System.out.println(products.first());
        if(products.size() <= 3){
            Elements temp = doc.getElementsByClass(parentClasses[1]);
            if(temp.size() > products.size()){
                products = temp;
            }
        }
        String name;
        float price;
        String link;
        String image;

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
                //price = toFloat(priceFull.substring(0, priceFull.length() / 2));
                price = toFloat(priceFull);

                link = websiteLink + product.getElementsByClass(linkClass).first().attr(linkKey);

                image = product.getElementsByClass(imageClass).first().attr(imageKey);

                items.add(new Product(name, price, link, image));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
    }
    private void setInstanceVars(){
        this.name = "amazon";
        this.websiteLinkToSearch = "https://www.amazon.ca/s?k=";
        this.websiteLink = "https://www.amazon.ca/";
    }
}
