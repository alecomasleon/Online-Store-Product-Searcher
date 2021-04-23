package item_search;

import java.util.Locale;

public abstract class Scraper {
    protected String name;
    protected String websiteLinkToSearch;

    public String getName(){return name;}
    public abstract String[] scrape(String searchString) throws Exception;

    public String makeURL(String searchString){
        String newString = searchString.toLowerCase(Locale.ROOT).replace(' ', '+');
        return this.websiteLinkToSearch + newString;
    }
}