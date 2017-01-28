package wnns.remind2speech.activities.fragments.adapters;

import android.support.v7.widget.RecyclerView;

public class RecyclerViewItem {

    String id;
    String title;
    String description;
    String date;

    public RecyclerViewItem(String _id, String _title, String _description, String _date){

        id = _id;
        title = _title;
        description = _description;
        date = _date;
    }

    public String getID(){

        return id;
    }

    public String getTitle(){

        return title;
    }

    public String getDescription(){

        return description;
    }

    public String getDate(){

        return date;
    }
}
