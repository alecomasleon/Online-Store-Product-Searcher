package item_search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class BestBuyScraper extends DriverScraper{
    String parentClass;
    String nameClass;
    String priceClass;
    String linkClass;
    String linkKey;

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

                items.add(new Product(name, price, link));
                count++;
            }catch(Exception ex){System.out.print("");}
        }

        return items.toArray(new Product[0]);
    }

    private void setInstanceVars(){
        this.name = "bestbuy";
        this.websiteLinkToSearch = "https://www.bestbuy.ca/en-ca/search?search=";
        this.websiteLink = "https://www.bestbuy.ca";

        this.parentClass = "col-xs-12_198le col-sm-4_13E9O col-lg-3_ECF8k x-productListItem productLine_2N9kG";
        //this.parentClass = "listItem_10CIq materialOverride_vWsDY";
        this.nameClass = "productItemName_3IZ3c";
        this.priceClass = "price_FHDfG medium_za6t1 salePrice_kTFZ3";
        //this.priceClass = "productPricingContainer_3gTS3";
        //this.linkClass = "link_18NOy";
        this.linkClass = "link_3hcyN";
        this.linkKey = "href";
    }
    public void waitInstructions() throws Exception{
        final int MILLISECOND_INTERVAL = 500;
        final int MAX_WAITING_TIME = 15;
        int count = 0;
        while(count < MAX_WAITING_TIME/(MILLISECOND_INTERVAL/1000f)){
            Thread.sleep(MILLISECOND_INTERVAL);
            count++;
            if(Jsoup.parse(driver.getPageSource()).getElementsByClass(((BestBuyScraper) this).parentClass).size() > 2){
                return;
            }
        }
        /*WebDriverWait wait = new WebDriverWait(driver, 30);
        By locator = By.className(this.parentClass);
        Thread.sleep(20000);
        System.out.println("h");
        //System.out.println(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        //System.out.println(Jsoup.parse(driver.getPageSource()).getElementsByClass(((BestBuyScraper) this).parentClass));

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
*/
    }
}
