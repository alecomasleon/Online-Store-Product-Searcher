package item_search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class AmazonScraper extends Scraper{
    public AmazonScraper(){
        this.name = "amazon";
        this.websiteLinkToSearch = "https://www.amazon.ca/s?k=";
    }
    @Override
    public String[] scrape(String searchString) throws Exception {
        //WebDriver driver = new ChromeDriver();
        //String url = makeURL(searchString);

        //System.out.println(url);
        //driver.navigate().to(url);

        //List<WebElement> products = driver.findElements(By.className())

        return new String[0];
    }
}
