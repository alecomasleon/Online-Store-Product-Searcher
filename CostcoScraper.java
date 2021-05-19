package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class CostcoScraper extends HttpScraper{
    private static final String parentClass = "product-tile-set";
    private static final String nameClass = "description";
    private static final String priceClass = "price";
    private static final String linkClass = "product-tile-set";
    private static final String linkKey = "data-pdp-url";
    private static final String imageClass = "img-responsive";
    private static final String imageKey = "src";

    public CostcoScraper(){
        setInstanceVars();
    }

    public CostcoScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc){
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
        this.name = "costco";
        this.websiteLinkToSearch = "https://www.costco.ca/CatalogSearch?dept=All&keyword=";
        this.websiteLink = "";
    }
}
