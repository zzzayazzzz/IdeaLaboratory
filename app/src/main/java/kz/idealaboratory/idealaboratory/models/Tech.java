package kz.idealaboratory.idealaboratory.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Tech {

    public String Name;
    public int Hourly;
    public int Shift;
    public int Type;
    public int count = 0;
    public Boolean isHourly;

    public Tech() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Tech(String name, int hourly, int shift, int Type) {
        this.Name = name;
        this.Hourly = hourly;
        this.Shift = shift;
        this.Type = Type;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", Name);
        result.put("Hourly", Hourly);
        result.put("Shift", Shift);
        result.put("Type", Type);
        return result;
    }
    // [END post_to_map]

}
// [END post_class]
