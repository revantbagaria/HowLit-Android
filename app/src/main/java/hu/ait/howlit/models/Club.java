package hu.ait.howlit.models;

public class Club {
    private String name;
    private String location;
    private String price;
    private String upvote_count;
    private String downvote_count;

    public Club(String name, String location, String price) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.upvote_count = "0";
        this.downvote_count = "0";
    }
    public Club(){

    }

    public Club(String name, String location, String price, String upvote_count, String downvote_count) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.upvote_count = upvote_count;
        this.downvote_count = downvote_count;
    }

    public String getUpvote_count() {
        return upvote_count;
    }

    public void setUpvote_count(String upvote_count) {
        this.upvote_count = upvote_count;
    }

    public String getDownvote_count() {
        return downvote_count;
    }

    public void setDownvote_count(String downvote_count) {
        this.downvote_count = downvote_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String toString(){
        return new String("name: " + name + "location: " + location + "price: " +  price);
    }

}

