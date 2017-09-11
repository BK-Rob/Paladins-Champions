package com.stats.champions.paladins.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

import co.uk.rushorm.core.AnnotationCache;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushCustomTableName;

@RushCustomTableName(name = "Item")
public class Item extends RushObject implements Parcelable {
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("DeviceName")
    @Expose
    private String deviceName;
    @SerializedName("IconId")
    @Expose
    private int iconId;
    @SerializedName("ItemId")
    @Expose
    private int id;
    @SerializedName("Price")
    @Expose
    private int price;
    @SerializedName("ShortDesc")
    @Expose
    private String shortDesc;
    @SerializedName("champion_id")
    @Expose
    private int championId;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("ret_msg")
    @Expose
    private Object retMsg;
    public final static Parcelable.Creator<Item> CREATOR = new Creator<Item>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Item createFromParcel(Parcel in) {
            Item instance = new Item();
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceName = ((String) in.readValue((String.class.getClassLoader())));
            instance.iconId = ((int) in.readValue((int.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.price = ((int) in.readValue((int.class.getClassLoader())));
            instance.shortDesc = ((String) in.readValue((String.class.getClassLoader())));
            instance.championId = ((int) in.readValue((int.class.getClassLoader())));
            instance.itemType = ((String) in.readValue((String.class.getClassLoader())));
            instance.retMsg = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public Item[] newArray(int size) {
            return (new Item[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Item() {
    }

    /**
     * @param price
     * @param deviceName
     * @param description
     * @param retMsg
     * @param itemType
     * @param iconId
     * @param shortDesc
     * @param championId
     * @param itemId
     */
    public Item(String description, String deviceName, int iconId, int itemId, int price, String shortDesc, int championId, String itemType, Object retMsg) {
        super();
        this.description = description;
        this.deviceName = deviceName;
        this.iconId = iconId;
        this.id = itemId;
        this.price = price;
        this.shortDesc = shortDesc;
        this.championId = championId;
        this.itemType = itemType;
        this.retMsg = retMsg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getmId() {
        return id;
    }

    public void semtId(int itemId) {
        this.id = itemId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Object getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(Object retMsg) {
        this.retMsg = retMsg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(description);
        dest.writeValue(deviceName);
        dest.writeValue(iconId);
        dest.writeValue(id);
        dest.writeValue(price);
        dest.writeValue(shortDesc);
        dest.writeValue(championId);
        dest.writeValue(itemType);
        dest.writeValue(retMsg);
    }

    public int describeContents() {
        return 0;
    }

    public static ArrayList<Item> loadDataById(String id) {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Item.class).getTableName();
        return (ArrayList<Item>) RushCore.getInstance().load(Item.class, "SELECT * FROM " + table + " WHERE id = " + id + " ORDER BY name DESC");
    }

    public static ArrayList<Item> loadAllData() {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Item.class).getTableName();
        return (ArrayList<Item>) RushCore.getInstance().load(Item.class, "SELECT * FROM " + table);
    }

    public static long countData() {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Item.class).getTableName();
        return RushCore.getInstance().count("SELECT COUNT(*) FROM " + table);
    }
}