package item_search;

public class WalmartScraper extends HttpScraper{
    public WalmartScraper(){
        this.name = "walmart";
        this.websiteLinkToSearch = "https://www.walmart.ca/search?q=";
        this.websiteLink = "https://www.walmart.ca/";
        this.classToExtract = "css-1q9cqzy epettpn1";
        this.keyToExtract = "href";
    }
}
