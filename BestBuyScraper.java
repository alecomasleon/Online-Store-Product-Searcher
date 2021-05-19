package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class BestBuyScraper extends DriverScraper{
    private static final String parentClass = "col-xs-12_198le col-sm-4_13E9O col-lg-3_ECF8k x-productListItem productLine_2N9kG";
    private static final String nameClass = "productItemName_3IZ3c";
    private static final String priceClass = "price_FHDfG medium_za6t1 salePrice_kTFZ3";
    private static final String linkClass = "link_3hcyN";
    private static final String linkKey = "href";
    private static final String imageClass = "productItemImage_1en8J";
    private static final String imageKey = "src";
    private static final String notImageClass = "." + "productImageContainer_1V2HD";

    public BestBuyScraper(){
        setInstanceVars();
    }
    public BestBuyScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc) {
        //System.out.println(doc.html());
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

                //System.out.println(product);
                Element fullPriceElement = product.getElementsByClass(priceClass).first();
                //System.out.println(fullPriceElement);
                String priceFull = fullPriceElement.text();
                price = toFloat(priceFull);
                //price = toFloat(priceFull.substring(0, priceFull.length() / 2));

                //System.out.println("a" + product.getElementsByClass(linkClass));
                //System.out.println("b" + product.getElementsByClass(linkClass).first().attr(linkKey));
                link = websiteLink + product.getElementsByClass(linkClass).first().attr(linkKey);

                image = product.getElementsByClass(imageClass).not(notImageClass).first().attr(imageKey);

                items.add(new Product(name, price, link, image));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
    }

    private void setInstanceVars(){
        this.name = "bestbuy";
        this.websiteLinkToSearch = "https://www.bestbuy.ca/en-ca/search?search=";
        this.websiteLink = "https://www.bestbuy.ca";
    }
    public void waitInstructions() throws Exception{
        waitUntilProductExistence();
        scrollDown();
    }
    public String getProductExistenceIdentifier(){
        return parentClass;
    }
}
