package item_search;

import org.jetbrains.annotations.NotNull;

public class Product implements Comparable<Float>{
    private String name;
    private String link;
    private float price;

    public Product(String name, float price, String link){
        this.name = name;
        this.link = link;
        this.price = price;
    }
    public Product(){
        this("", -1, "");
    }
    public Product(String link){
        this("", -1, link);
    }

    @Override
    public int compareTo(@NotNull Float o) {
        return Float.compare(this.price, o);
    }

    public String toString(){
        return "Name: " + this.name +
                "  Price: " + this.price +
                "  Link: " + this.link;
    }


    public void setName(String name){ this.name = name;}
    public String getName(){ return name;}

    public void setLink(String link){ this.link = link;}
    public String getLink(){ return link;}

    public void setPrice(float price) {
        if (price >= 0) {
            this.price = price;
        }
    }
    public float getPrice(){ return price;}
}
