package item_search;

public class IndigoScraper extends HttpScraper{
    public IndigoScraper(){
        this.name = "indigo";
        this.websiteLinkToSearch = "https://www.chapters.indigo.ca/en-ca/home/search/?keywords=";
        this.websiteLink = "https://www.indigo.ca/";
        this.classToExtract = "product-list__product-title";
        this.keyToExtract = "href";
    }
}
