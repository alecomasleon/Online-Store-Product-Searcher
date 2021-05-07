package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;

public class GoogleScraper extends HttpScraper{
    String endOfURL;

    String[] parentClasses;
    String[] nameClasses;
    String[] priceClasses;
    String[] linkClasses;
    String[] linkKeys;

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

                items.add(new Product(name, price, link));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
    }

    private void setInstanceVars(){
        this.endOfURL = "&rlz=1C5CHFA_enCA930CA935&sxsrf=ALeKk01odgGb-x1icu0ROTS1ds8gdsIWDw:1619825867241&source=" +
                "lnms&tbm=shop&sa=X&ved=2ahUKEwi2rajLkafwAhXCBc0KHe43BacQ_AUoAXoECAEQAw&biw=1792&bih=1009";

        this.name = "google";
        this.websiteLinkToSearch = "https://www.google.com/search?q=";
        this.websiteLink = "https://www.google.com";

        this.parentClasses = new String[]{"sh-dgr__content", "sh-dlr__content xal5Id"};
        this.nameClasses = new String[]{"A2sOrd", "OzIAJc"};
        this.priceClasses = new String[]{"a8Pemb", "a8Pemb"};
        this.linkClasses = new String[]{"Lq5OHe eaGTj translate-content", "VZTCjd translate-content"};
        this.linkKeys = new String[]{"href", "href"};
    }

    public String makeURL(String searchString){
        String newString = searchString.toLowerCase(Locale.ROOT).replace(' ', '+');
        String query = URLEncoder.encode(newString, StandardCharsets.UTF_8);
        return this.websiteLinkToSearch + query + this.endOfURL;
    }
}
