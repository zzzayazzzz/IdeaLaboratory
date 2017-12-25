package kz.idealaboratory.idealaboratory.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Project {
    private int sum;
    public String client;
    public String agency;
    public String contact;
    public String format;
    public String duration;
    public String date;
    public String title;
    public String timing;
    public ArrayList<String> usernames;
    private int isClosed;

    public Project(){}

    public Project(String projectTitle, ArrayList<String> projectUsernames){

        title = projectTitle;
        isClosed = 0;
        date = DateFormat.getDateInstance().format(new Date());
        usernames = projectUsernames;

    }

    public int IsClosed(){
        return isClosed;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Title", title);
        return result;
    }
}
