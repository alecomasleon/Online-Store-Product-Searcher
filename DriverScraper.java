package item_search;

import org.jsoup.Jsoup;
import org.openqa.selenium.JavascriptExecutor;
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
        try {
            System.setProperty("webdriver.chrome.driver", "Item_Search_Jars/chromedriver");

            createChromeDriver();

            String url = makeURL(searchString);
            //System.out.println(url);

            driver.navigate().to(url);
            //driver.get(url);

            waitInstructions();

            String html = driver.getPageSource();
            //System.out.println(html);
            return extractElements(html);
        }catch(Exception ex) {
            ex.printStackTrace();
        }finally{
            driver.close();
            driver.quit();
            driver = null;
        }
        return new Product[0];
    }

    public void createChromeDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        options.addArguments("window-size=1922x1083");
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("disable-gpu");
        options.addArguments("disable-extensions");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");

        driver = new ChromeDriver(options);
    }
    public void waitInstructions() throws Exception{}
    public void scrollDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for(int i = 0; i < 10; i++) {
            js.executeScript("window.scrollBy(0, 400)");
            //Thread.sleep(1000);
        }
    }
    public String getProductExistenceIdentifier(){
        return "";
    }
    public void waitUntilProductExistence() throws Exception{
        final int MILLISECOND_INTERVAL = 500;
        final int MAX_WAITING_TIME = 15;
        int count = 0;
        while(count < MAX_WAITING_TIME/(MILLISECOND_INTERVAL/1000f)){
            Thread.sleep(MILLISECOND_INTERVAL);
            count++;
            if(Jsoup.parse(driver.getPageSource()).getElementsByClass(getProductExistenceIdentifier()).size() > 2){
                break;
            }
        }
    }
}
