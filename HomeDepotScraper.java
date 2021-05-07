package item_search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Locale;


public class HomeDepotScraper extends DriverScraper{
    String nameClass;
    String priceClass;
    String parentClass;
    String linkClass;
    String linkKey;

    public HomeDepotScraper(){
        setInstanceVars();
    }

    public HomeDepotScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc){
        //System.out.println(doc);
        ArrayList<Product> products = new ArrayList<>();

        String[] names = getFromText(doc, nameClass);
        String[] prices = getFromText(doc, priceClass);
        String[] links = getLinks(doc);

        /*System.out.println(names.length == prices.length && names.length == links.length);
        System.out.println(names.length);
        System.out.println(prices.length);
        System.out.println(links.length);*/
        int count = 0;

        if(names.length == prices.length && names.length == links.length) {
            for (int i = 0; i < names.length; i++) {
                if(count == maxItemsPerWebsite){
                    break;
                }
                try {
                    products.add(new Product(names[i], toFloat(editPrice(prices[i])), links[i]));
                    count++;
                }catch(Exception ex){
                    //System.out.println(names[i]);
                    //System.out.println(prices[i]);
                    System.out.print("");
                }
            }
        }
        return products.toArray(new Product[0]);
    }
    private String[] getLinks(Document doc){
        Elements classes = doc.getElementsByClass(linkClass);
        //System.out.println(products.get(1).getElementsByAttributeStarting("product-list__price"));
        //System.out.println(classes.size());

        ArrayList<String> items = new ArrayList<>();
        for (Element e: classes) {
            if(e.attributes().hasKey(linkKey)) {
                String attr = e.attr(linkKey);
                items.add(websiteLink + attr);
            }
        }

        return items.toArray(new String[0]);
    }
    private String[] getFromText(Document doc, String textClass){
        Elements products = doc.getElementsByClass(textClass);

        ArrayList<String> items = new ArrayList<>();
        for (Element e: products) {
            items.add(e.text());
        }
        return items.toArray(new String[0]);
    }
    private String editPrice(String price){
        return price.toLowerCase(Locale.ROOT).replace("cents / each", "").replace("and ", ".");
    }
    private void setInstanceVars(){
        this.name = "homedepot";
        this.websiteLinkToSearch = "https://www.homedepot.ca/search?q=";
        this.websiteLink = "https://www.homedepot.ca";

        this.parentClass = "acl-product-card acl-product-card--is-grid acl-product-card--has-rating acl-product-card--has-badges-compare ng-star-inserted";
        this.nameClass = "acl-product-card__title-link ng-star-inserted";
        this.priceClass = "acl-product-card__price";
        this.linkClass = "acl-product-card__title-link ng-star-inserted";
        this.linkKey = "href";
    }
    public void waitInstructions() throws Exception {
        final int MILLISECOND_INTERVAL = 500;
        final int MAX_WAITING_TIME = 10;
        int count = 0;
        while (count < MAX_WAITING_TIME / (MILLISECOND_INTERVAL / 1000f)) {
            Thread.sleep(MILLISECOND_INTERVAL);
            count++;
            if (Jsoup.parse(driver.getPageSource()).getElementsByClass((this).nameClass).size() > 2) {
                return;
            }
        }
    }
}

