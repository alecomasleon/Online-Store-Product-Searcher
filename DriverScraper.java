package item_search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class DriverScraper extends Scraper{
    WebDriver driver;

    public DriverScraper(){
        super();
        this.driver = null;
    }
    public DriverScraper(int maxItemsPerWebsite){
        super(maxItemsPerWebsite);
        this.driver = null;
    }

    @Override
    public Product[] scrape(String searchString) throws Exception {
        System.setProperty("webdriver.chrome.driver", "Item_Search_Jars/chromedriver");

        createChromeDriver();

        String url = makeURL(searchString);
        //System.out.println(url);

        driver.navigate().to(url);
        //driver.get(url);

        String html = driver.getPageSource();
        //System.out.println(html);
        driver.close();
        driver = null;
        return extractElements(html);
    }

    public void createChromeDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

        driver = new ChromeDriver(options);
    }
}
