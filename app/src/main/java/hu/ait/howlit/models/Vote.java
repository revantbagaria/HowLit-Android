package hu.ait.howlit.models;

/**
 * Created by millieyang on 5/22/17.
 */

public class Vote {
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

    private String upvote_count, downvote_count;

    public Vote(){

    }
    public Vote(String upvote_count, String downvote_count) {
        this.upvote_count = upvote_count;
        this.downvote_count = downvote_count;
    }
}
