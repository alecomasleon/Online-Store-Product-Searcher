package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WalmartScraper extends DriverScraper {
    private final static String parentClass = "css-x7wixz epettpn0";
    private final static String nameClass = "css-1p4va6y eudvd6x0";
    private final static String priceClass = "css-1vsc4ug e175iya64";
    private final static String linkClass = "css-1q9cqzy epettpn1";
    private final static String linkKey = "href";
    private final static String imageClass = "css-gxbcya e175iya62";
    private final static String imageKey = "src";

    public WalmartScraper() {
        setInstanceVars();
    }
    public WalmartScraper(int maxItems) {
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc) {
        //System.out.println(doc);
        Elements products = doc.getElementsByClass(parentClass);
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

                //System.out.println(product.text());
                Element fullPriceElement = product.getElementsByClass(priceClass).first();
                //System.out.println(fullPriceElement);
                String priceFull = fullPriceElement.text();
                price = toFloat(priceFull);

                link = websiteLink + product.getElementsByClass(linkClass).first().attr(linkKey);

                image = product.getElementsByClass(imageClass).first().attr(imageKey);

                items.add(new Product(name, price, link, image));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
        //return ExtractElementsStatic.getAllProductsNormal(doc, nameClass, priceClass, websiteLink, linkClass, linkKey);
    }
    private void setInstanceVars(){
        this.name = "walmart";
        this.websiteLinkToSearch = "https://www.walmart.ca/search?q=";
        this.websiteLink = "https://www.walmart.ca/";
    }
}
