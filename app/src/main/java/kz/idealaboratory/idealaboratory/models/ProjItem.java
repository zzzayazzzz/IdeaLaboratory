package kz.idealaboratory.idealaboratory.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START post_class]
@IgnoreExtraProperties
public class ProjItem {

    public Item item;
    public long startTime;
    public long endTime;
    public boolean isStarted;
    public int endPrice;
    public String itemType;

    public ProjItem() {
    }

    public ProjItem(Item item, long startTime, long endTime, boolean isStarted, int endPrice, String itemType) {
        this.item = item;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isStarted = isStarted;
        this.endPrice = endPrice;
        this.itemType = itemType;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    //TODO calculatePrice (???) )))))
    public void calculatePrice() {
        this.endPrice = (int)(this.endTime - this.startTime) * this.item.getHourly();
    }
}
// [END post_class]
