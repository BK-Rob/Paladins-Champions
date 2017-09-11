package com.stats.champions.paladins.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;

import co.uk.rushorm.core.AnnotationCache;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.annotations.RushCustomTableName;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Map;

import co.uk.rushorm.core.RushObject;

@RushCustomTableName(name = "Champions")
public class Champion extends RushObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("Ability1")
    @Expose
    private String ability1;
    @SerializedName("Ability2")
    @Expose
    private String ability2;
    @SerializedName("Ability3")
    @Expose
    private String ability3;
    @SerializedName("Ability4")
    @Expose
    private String ability4;
    @SerializedName("Ability5")
    @Expose
    private String ability5;
    @SerializedName("AbilityId1")
    @Expose
    private int abilityId1;
    @SerializedName("AbilityId2")
    @Expose
    private int abilityId2;
    @SerializedName("AbilityId3")
    @Expose
    private int abilityId3;
    @SerializedName("AbilityId4")
    @Expose
    private int abilityId4;
    @SerializedName("AbilityId5")
    @Expose
    private int abilityId5;
    @SerializedName("ChampionAbility1_URL")
    @Expose
    private String championAbility1URL;
    @SerializedName("ChampionAbility2_URL")
    @Expose
    private String championAbility2URL;
    @SerializedName("ChampionAbility3_URL")
    @Expose
    private String championAbility3URL;
    @SerializedName("ChampionAbility4_URL")
    @Expose
    private String championAbility4URL;
    @SerializedName("ChampionAbility5_URL")
    @Expose
    private String championAbility5URL;
    @SerializedName("ChampionCard_URL")
    @Expose
    private String championCardURL;
    @SerializedName("ChampionIcon_URL")
    @Expose
    private String championIconURL;
    @SerializedName("Cons")
    @Expose
    private String cons;
    @SerializedName("Health")
    @Expose
    private int health;
    @SerializedName("Lore")
    @Expose
    private String lore;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("OnFreeRotation")
    @Expose
    private String onFreeRotation;
    @SerializedName("Pantheon")
    @Expose
    private String pantheon;
    @SerializedName("Pros")
    @Expose
    private String pros;
    @SerializedName("Roles")
    @Expose
    private String roles;
    @SerializedName("Speed")
    @Expose
    private int speed;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("abilityDescription1")
    @Expose
    private String abilityDescription1;
    @SerializedName("abilityDescription2")
    @Expose
    private String abilityDescription2;
    @SerializedName("abilityDescription3")
    @Expose
    private String abilityDescription3;
    @SerializedName("abilityDescription4")
    @Expose
    private String abilityDescription4;
    @SerializedName("abilityDescription5")
    @Expose
    private String abilityDescription5;
    @SerializedName("latestChampion")
    @Expose
    private String latestChampion;
    @SerializedName("ret_msg")
    @Expose
    private Object retMsg;

    public Champion() {
    }

    public final static Parcelable.Creator<Champion> CREATOR = new Creator<Champion>() {
        @SuppressWarnings({
                "unchecked"
        })
        public Champion createFromParcel(Parcel in) {
            Champion instance = new Champion();
            instance.ability1 = ((String) in.readValue((String.class.getClassLoader())));
            instance.ability2 = ((String) in.readValue((String.class.getClassLoader())));
            instance.ability3 = ((String) in.readValue((String.class.getClassLoader())));
            instance.ability4 = ((String) in.readValue((String.class.getClassLoader())));
            instance.ability5 = ((String) in.readValue((String.class.getClassLoader())));
            instance.abilityId1 = ((int) in.readValue((int.class.getClassLoader())));
            instance.abilityId2 = ((int) in.readValue((int.class.getClassLoader())));
            instance.abilityId3 = ((int) in.readValue((int.class.getClassLoader())));
            instance.abilityId4 = ((int) in.readValue((int.class.getClassLoader())));
            instance.abilityId5 = ((int) in.readValue((int.class.getClassLoader())));
            instance.championAbility1URL = ((String) in.readValue((String.class.getClassLoader())));
            instance.championAbility2URL = ((String) in.readValue((String.class.getClassLoader())));
            instance.championAbility3URL = ((String) in.readValue((String.class.getClassLoader())));
            instance.championAbility4URL = ((String) in.readValue((String.class.getClassLoader())));
            instance.championAbility5URL = ((String) in.readValue((String.class.getClassLoader())));
            instance.championCardURL = ((String) in.readValue((String.class.getClassLoader())));
            instance.championIconURL = ((String) in.readValue((String.class.getClassLoader())));
            instance.cons = ((String) in.readValue((String.class.getClassLoader())));
            instance.health = ((int) in.readValue((int.class.getClassLoader())));
            instance.lore = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.onFreeRotation = ((String) in.readValue((String.class.getClassLoader())));
            instance.pantheon = ((String) in.readValue((String.class.getClassLoader())));
            instance.pros = ((String) in.readValue((String.class.getClassLoader())));
            instance.roles = ((String) in.readValue((String.class.getClassLoader())));
            instance.speed = ((int) in.readValue((int.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.abilityDescription1 = ((String) in.readValue((String.class.getClassLoader())));
            instance.abilityDescription2 = ((String) in.readValue((String.class.getClassLoader())));
            instance.abilityDescription3 = ((String) in.readValue((String.class.getClassLoader())));
            instance.abilityDescription4 = ((String) in.readValue((String.class.getClassLoader())));
            instance.abilityDescription5 = ((String) in.readValue((String.class.getClassLoader())));
            instance.latestChampion = ((String) in.readValue((String.class.getClassLoader())));
            instance.retMsg = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public Champion[] newArray(int size) {
            return (new Champion[size]);
        }

    };

    public String getAbility1() {
        return ability1;
    }

    public void setAbility1(String ability1) {
        this.ability1 = ability1;
    }

    public String getAbility2() {
        return ability2;
    }

    public void setAbility2(String ability2) {
        this.ability2 = ability2;
    }

    public String getAbility3() {
        return ability3;
    }

    public void setAbility3(String ability3) {
        this.ability3 = ability3;
    }

    public String getAbility4() {
        return ability4;
    }

    public void setAbility4(String ability4) {
        this.ability4 = ability4;
    }

    public String getAbility5() {
        return ability5;
    }

    public void setAbility5(String ability5) {
        this.ability5 = ability5;
    }

    public int getAbilityId1() {
        return abilityId1;
    }

    public void setAbilityId1(int abilityId1) {
        this.abilityId1 = abilityId1;
    }

    public int getAbilityId2() {
        return abilityId2;
    }

    public void setAbilityId2(int abilityId2) {
        this.abilityId2 = abilityId2;
    }

    public int getAbilityId3() {
        return abilityId3;
    }

    public void setAbilityId3(int abilityId3) {
        this.abilityId3 = abilityId3;
    }

    public int getAbilityId4() {
        return abilityId4;
    }

    public void setAbilityId4(int abilityId4) {
        this.abilityId4 = abilityId4;
    }

    public int getAbilityId5() {
        return abilityId5;
    }

    public void setAbilityId5(int abilityId5) {
        this.abilityId5 = abilityId5;
    }

    public String getChampionAbility1URL() {
        return championAbility1URL;
    }

    public void setChampionAbility1URL(String championAbility1URL) {
        this.championAbility1URL = championAbility1URL;
    }

    public String getChampionAbility2URL() {
        return championAbility2URL;
    }

    public void setChampionAbility2URL(String championAbility2URL) {
        this.championAbility2URL = championAbility2URL;
    }

    public String getChampionAbility3URL() {
        return championAbility3URL;
    }

    public void setChampionAbility3URL(String championAbility3URL) {
        this.championAbility3URL = championAbility3URL;
    }

    public String getChampionAbility4URL() {
        return championAbility4URL;
    }

    public void setChampionAbility4URL(String championAbility4URL) {
        this.championAbility4URL = championAbility4URL;
    }

    public String getChampionAbility5URL() {
        return championAbility5URL;
    }

    public void setChampionAbility5URL(String championAbility5URL) {
        this.championAbility5URL = championAbility5URL;
    }

    public String getChampionCardURL() {
        return championCardURL;
    }

    public void setChampionCardURL(String championCardURL) {
        this.championCardURL = championCardURL;
    }

    public String getChampionIconURL() {
        return championIconURL;
    }

    public void setChampionIconURL(String championIconURL) {
        this.championIconURL = championIconURL;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnFreeRotation() {
        return onFreeRotation;
    }

    public void setOnFreeRotation(String onFreeRotation) {
        this.onFreeRotation = onFreeRotation;
    }

    public String getPantheon() {
        return pantheon;
    }

    public void setPantheon(String pantheon) {
        this.pantheon = pantheon;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbilityDescription1() {
        return abilityDescription1;
    }

    public void setAbilityDescription1(String abilityDescription1) {
        this.abilityDescription1 = abilityDescription1;
    }

    public String getAbilityDescription2() {
        return abilityDescription2;
    }

    public void setAbilityDescription2(String abilityDescription2) {
        this.abilityDescription2 = abilityDescription2;
    }

    public String getAbilityDescription3() {
        return abilityDescription3;
    }

    public void setAbilityDescription3(String abilityDescription3) {
        this.abilityDescription3 = abilityDescription3;
    }

    public String getAbilityDescription4() {
        return abilityDescription4;
    }

    public void setAbilityDescription4(String abilityDescription4) {
        this.abilityDescription4 = abilityDescription4;
    }

    public String getAbilityDescription5() {
        return abilityDescription5;
    }

    public void setAbilityDescription5(String abilityDescription5) {
        this.abilityDescription5 = abilityDescription5;
    }

    public String getLatestChampion() {
        return latestChampion;
    }

    public void setLatestChampion(String latestChampion) {
        this.latestChampion = latestChampion;
    }

    public Object getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(Object retMsg) {
        this.retMsg = retMsg;
    }

    public int getmId() {
        return id;
    }

    public void setmId(int mId) {
        this.id = mId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ability1);
        dest.writeValue(ability2);
        dest.writeValue(ability3);
        dest.writeValue(ability4);
        dest.writeValue(ability5);
        dest.writeValue(abilityId1);
        dest.writeValue(abilityId2);
        dest.writeValue(abilityId3);
        dest.writeValue(abilityId4);
        dest.writeValue(abilityId5);
        dest.writeValue(championAbility1URL);
        dest.writeValue(championAbility2URL);
        dest.writeValue(championAbility3URL);
        dest.writeValue(championAbility4URL);
        dest.writeValue(championAbility5URL);
        dest.writeValue(championCardURL);
        dest.writeValue(championIconURL);
        dest.writeValue(cons);
        dest.writeValue(health);
        dest.writeValue(lore);
        dest.writeValue(name);
        dest.writeValue(onFreeRotation);
        dest.writeValue(pantheon);
        dest.writeValue(pros);
        dest.writeValue(roles);
        dest.writeValue(speed);
        dest.writeValue(title);
        dest.writeValue(type);
        dest.writeValue(abilityDescription1);
        dest.writeValue(abilityDescription2);
        dest.writeValue(abilityDescription3);
        dest.writeValue(abilityDescription4);
        dest.writeValue(abilityDescription5);
        dest.writeValue(latestChampion);
        dest.writeValue(retMsg);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

    public static ArrayList<Champion> loadDataById(String id) {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Champion.class).getTableName();
        return (ArrayList<Champion>) RushCore.getInstance().load(Champion.class, "SELECT * FROM " + table + " WHERE id = " + id + " ORDER BY name DESC");
    }

    public static ArrayList<Champion> loadAllData() {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Champion.class).getTableName();
        return (ArrayList<Champion>) RushCore.getInstance().load(Champion.class, "SELECT * FROM " + table);
    }

    public static long countData() {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Champion.class).getTableName();
        return RushCore.getInstance().count("SELECT COUNT(*) FROM " + table);
    }
}