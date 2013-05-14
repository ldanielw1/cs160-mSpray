package com.example.mspray;

/**
 * @author Lars Vogel, copyright 2010, 2011, 2012, http://www.vogella.com/articles/AndroidSQLite/article.html
 * @author Daniel Wu, September 2012, a few revisions based on Lars Vogel's model
 * A wrapper for a String along with an id for SQL database uploading.
 */
public class Comment {
    private long id;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return comment;
    }
}
