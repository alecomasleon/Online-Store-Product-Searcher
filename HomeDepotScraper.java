package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Locale;


public class HomeDepotScraper extends DriverScraper{
    private final static String nameClass = "acl-product-card__title-link ng-star-inserted";
    private final static String priceClass = "acl-product-card__price";
    private final static String linkClass = "acl-product-card__title-link ng-star-inserted";
    private final static String linkKey = "href";
    private final static String imageClass = "acl-image__image ng-star-inserted";
    private final static String imageKey = "src";

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
        String[] links = getFromKey(doc, linkClass, linkKey);
        String[] images = getFromKey(doc, imageClass, imageKey);

        /*System.out.println(names.length == prices.length && names.length == links.length && names.length == images.length);
        System.out.println(names.length);
        System.out.println(prices.length);
        System.out.println(links.length);
        System.out.println(images.length);*/
        int count = 0;

        if(names.length == prices.length && names.length == links.length && names.length == images.length) {
            for (int i = 0; i < names.length; i++) {
                if(count == maxItemsPerWebsite){
                    break;
                }
                try {
                    products.add(new Product(names[i], toFloat(editPrice(prices[i])), websiteLink + links[i], images[i]));
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
    private String[] getFromKey(Document doc, String class1, String key){
        Elements classes = doc.getElementsByClass(class1);
        //System.out.println(products.get(1).getElementsByAttributeStarting("product-list__price"));
        //System.out.println(classes.size());

        ArrayList<String> items = new ArrayList<>();
        for (Element e: classes) {
            if(e.attributes().hasKey(key)) {
                //System.out.println(!e.attr("title").equals(""));
                //System.out.println(e.attr(key));
                if(class1.equals(imageClass) && e.attr("title").equals("")){
                    continue;
                }
                String attr = e.attr(key);
                items.add(attr);
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
    }
    public void waitInstructions() throws Exception {
        waitUntilProductExistence();
        scrollDown();
    }
    public String getProductExistenceIdentifier(){
        return nameClass;
    }
}

