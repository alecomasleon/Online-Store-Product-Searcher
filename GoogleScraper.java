package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;

public class GoogleScraper extends DriverScraper{
    private static final String endOfURL = "&rlz=1C5CHFA_enCA930CA935&sxsrf=ALeKk01odgGb-x1icu0ROTS1ds8gdsIWDw:1619825867241&source=" +
            "lnms&tbm=shop&sa=X&ved=2ahUKEwi2rajLkafwAhXCBc0KHe43BacQ_AUoAXoECAEQAw&biw=1792&bih=1009";

    private final static String[] parentClasses = new String[]{"sh-dgr__content", "sh-dlr__content xal5Id"};
    private final static String[] nameClasses = new String[]{"A2sOrd", "OzIAJc"};
    private final static String[] priceClasses = new String[]{"a8Pemb", "a8Pemb"};
    private final static String[] linkClasses = new String[]{"Lq5OHe eaGTj translate-content", "VZTCjd translate-content"};
    private final static String[] linkKeys = new String[]{"href", "href"};
    private final static String[] imageClasses = new String[]{"ArOc1c", "TL92Hc"};
    private final static String[] imageKeys = new String[]{"src", "src"};

    public GoogleScraper(){
        setInstanceVars();
    }
    public GoogleScraper(int maxItems){
        super(maxItems);
        setInstanceVars();
    }

    @Override
    public Product[] getProductsFromDoc(Document doc) {
        int version = 0;
        Elements products = new Elements();
        for (int i = 0; i < parentClasses.length; i++) {
            products = doc.getElementsByClass(parentClasses[i]);
            if(products.size() != 0){
                version = i;
                break;
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
                name = product.getElementsByClass(nameClasses[version]).first().text();

                //System.out.println(product.text());
                Element fullPriceElement = product.getElementsByClass(priceClasses[version]).first();
                //System.out.println(fullPriceElement);
                String priceFull = fullPriceElement.text();
                price = toFloat(priceFull);

                link = websiteLink + product.getElementsByClass(linkClasses[version]).first().attr(linkKeys[version]);

                image =  product.getElementsByClass(imageClasses[version]).first().getAllElements().attr(imageKeys[version]);
                //System.out.println(name);
                //System.out.println(product.getElementsByClass(imageClasses[version]).first().getAllElements());
                //System.out.println(product);

                items.add(new Product(name, price, link, image));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
    }

    private void setInstanceVars(){
        this.name = "google";
        this.websiteLinkToSearch = "https://www.google.com/search?q=";
        this.websiteLink = "https://www.google.com";
    }

    public String makeURL(String searchString){
        String newString = searchString.toLowerCase(Locale.ROOT).replace(' ', '+');
        String query = URLEncoder.encode(newString, StandardCharsets.UTF_8);
        return this.websiteLinkToSearch + query + endOfURL;
    }
    public void waitInstructions() throws Exception{
        scrollDown();
    }
}
