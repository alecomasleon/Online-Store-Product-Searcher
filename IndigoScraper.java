package item_search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class IndigoScraper extends HttpScraper{
    String parentClass;
    String nameClass;
    String priceClass1;
    String priceClass2;
    String linkClass;
    String linkKey;

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

                items.add(new Product(name, price, link));
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

        this.parentClass = "product-list__product product-list__product-container";
        this.nameClass = "product-list__product-title";
        this.priceClass1 = "product-list__price--black product-list__listview-price";
        this.priceClass2 = "product-list__price--orange";
        this.linkClass = "js-productImageWrap product-list__product-link";
        this.linkKey = "href";
    }
}
