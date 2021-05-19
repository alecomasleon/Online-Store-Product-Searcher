package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class IndigoScraper extends DriverScraper{
    private final static String parentClass = "product-list__product product-list__product-container";
    private final static String nameClass = "product-list__product-title";
    private final static String priceClass1 = "product-list__price--black product-list__listview-price";
    private final static String priceClass2 = "product-list__price--orange";
    private final static String linkClass = "js-productImageWrap product-list__product-link";
    private final static String linkKey = "href";
    private final static String imageClass = "product-list__product-image";
    private final static String imageKey = "src";

    public IndigoScraper(){
        setInstanceVars();
    }
    public IndigoScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }
    @Override
    public Product[] getProductsFromDoc(Document doc) {
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
                Elements price1 = product.getElementsByClass(priceClass1);
                if(price1.size() == 0){
                    price1 = product.getElementsByClass(priceClass2);
                }
                //System.out.println(fullPriceElement);
                String priceFull = takeOffOnline(price1.first().text());
                //System.out.println(priceFull);
                price = toFloat(priceFull);

                link = websiteLink + product.getElementsByClass(linkClass).first().attr(linkKey);

                image = product.getElementsByClass(imageClass).first().attr(imageKey);

                items.add(new Product(name, price, link, image));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
    }
    private String takeOffOnline(String price){
        return price.replace(" online", "");
    }
    private void setInstanceVars(){
        this.name = "indigo";
        this.websiteLinkToSearch = "https://www.chapters.indigo.ca/en-ca/home/search/?keywords=";
        this.websiteLink = "https://www.indigo.ca/";
    }
    public void waitInstructions() throws Exception{
        scrollDown();
    }
}
