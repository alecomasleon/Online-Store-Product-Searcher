package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class EBayScraper extends HttpScraper{
    private static final String parentClass = "s-item         ";
    private static final String nameClass = "s-item__title";
    private static final String priceClass = "s-item__price";
    private static final String linkClass = "s-item__link";
    private static final String linkKey = "href";
    private static final String imageClass = "s-item__image-img";
    private static final String imageKey = "src";

    public EBayScraper(){
        setInstanceVars();
    }

    public EBayScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc){
        //System.out.println(doc);
        Elements products = doc.getElementsByClass(parentClass);
        String name;
        float price;
        String link;
        String image;

        ArrayList<Product> items = new ArrayList<>();
        int count = 0;
        //System.out.println(products.size());
        for (Element product: products) {
            if(count == maxItemsPerWebsite){
                break;
            }
            try {
                name = product.getElementsByClass(nameClass).first().text();

                //System.out.println(product.text());
                Element fullPriceElement = product.getElementsByClass(priceClass).first();
                //System.out.println(fullPriceElement);
                String priceFull = fullPriceElement.text().replace("C", "");
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
        this.name = "ebay";
        this.websiteLinkToSearch = "https://www.ebay.ca/sch/i.html?_from=R40&_trksid=p2380057.m570.l1311&_nkw=";
        this.websiteLink = "";
    }
}
