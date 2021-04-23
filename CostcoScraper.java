package item_search;

public class CostcoScraper extends HttpScraper{
    public CostcoScraper(){
        this.name = "costco";
        this.websiteLinkToSearch = "https://www.costco.ca/CatalogSearch?dept=All&keyword=";
        this.websiteLink = "";
        this.classToExtract = "product-tile-set";
        this.keyToExtract = "data-pdp-url";
    }
}
