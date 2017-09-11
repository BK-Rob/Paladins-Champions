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

@RushCustomTableName(name = "Players")
public class Player extends RushObject implements Parcelable
{
    @SerializedName("Created_Datetime")
    @Expose
    private String createdDatetime;
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Last_Login_Datetime")
    @Expose
    private String lastLoginDatetime;
    @SerializedName("Leaves")
    @Expose
    private int leaves;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("Losses")
    @Expose
    private int losses;
    @SerializedName("MasteryLevel")
    @Expose
    private int masteryLevel;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Personal_Status_Message")
    @Expose
    private String personalStatusMessage;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("TeamId")
    @Expose
    private int teamId;
    @SerializedName("Team_Name")
    @Expose
    private String teamName;
    @SerializedName("Total_Achievements")
    @Expose
    private int totalAchievements;
    @SerializedName("Total_Worshippers")
    @Expose
    private int totalWorshippers;
    @SerializedName("Wins")
    @Expose
    private int wins;
    @SerializedName("ret_msg")
    @Expose
    private Object retMsg;
    public final static Parcelable.Creator<Player> CREATOR = new Creator<Player>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Player createFromParcel(Parcel in) {
            Player instance = new Player();
            instance.createdDatetime = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.lastLoginDatetime = ((String) in.readValue((String.class.getClassLoader())));
            instance.leaves = ((int) in.readValue((int.class.getClassLoader())));
            instance.level = ((int) in.readValue((int.class.getClassLoader())));
            instance.losses = ((int) in.readValue((int.class.getClassLoader())));
            instance.masteryLevel = ((int) in.readValue((int.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.personalStatusMessage = ((String) in.readValue((String.class.getClassLoader())));
            instance.region = ((String) in.readValue((String.class.getClassLoader())));
            instance.teamId = ((int) in.readValue((int.class.getClassLoader())));
            instance.teamName = ((String) in.readValue((String.class.getClassLoader())));
            instance.totalAchievements = ((int) in.readValue((int.class.getClassLoader())));
            instance.totalWorshippers = ((int) in.readValue((int.class.getClassLoader())));
            instance.wins = ((int) in.readValue((int.class.getClassLoader())));
            instance.retMsg = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public Player[] newArray(int size) {
            return (new Player[size]);
        }

    }
            ;

    /**
     * No args constructor for use in serialization
     *
     */
    public Player() {
    }

    /**
     *
     * @param region
     * @param teamName
     * @param createdDatetime
     * @param leaves
     * @param teamId
     * @param id
     * @param level
     * @param retMsg
     * @param name
     * @param totalAchievements
     * @param losses
     * @param personalStatusMessage
     * @param totalWorshippers
     * @param lastLoginDatetime
     * @param wins
     * @param masteryLevel
     */
    public Player(String createdDatetime, int id, String lastLoginDatetime, int leaves, int level, int losses, int masteryLevel, String name, String personalStatusMessage, String region, int teamId, String teamName, int totalAchievements, int totalWorshippers, int wins, Object retMsg) {
        super();
        this.createdDatetime = createdDatetime;
        this.id = id;
        this.lastLoginDatetime = lastLoginDatetime;
        this.leaves = leaves;
        this.level = level;
        this.losses = losses;
        this.masteryLevel = masteryLevel;
        this.name = name;
        this.personalStatusMessage = personalStatusMessage;
        this.region = region;
        this.teamId = teamId;
        this.teamName = teamName;
        this.totalAchievements = totalAchievements;
        this.totalWorshippers = totalWorshippers;
        this.wins = wins;
        this.retMsg = retMsg;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public int getmId() {
        return id;
    }

    public void setmId(int id) {
        this.id = id;
    }

    public String getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(String lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getMasteryLevel() {
        return masteryLevel;
    }

    public void setMasteryLevel(int masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalStatusMessage() {
        return personalStatusMessage;
    }

    public void setPersonalStatusMessage(String personalStatusMessage) {
        this.personalStatusMessage = personalStatusMessage;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTotalAchievements() {
        return totalAchievements;
    }

    public void setTotalAchievements(int totalAchievements) {
        this.totalAchievements = totalAchievements;
    }

    public int getTotalWorshippers() {
        return totalWorshippers;
    }

    public void setTotalWorshippers(int totalWorshippers) {
        this.totalWorshippers = totalWorshippers;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Object getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(Object retMsg) {
        this.retMsg = retMsg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(createdDatetime);
        dest.writeValue(id);
        dest.writeValue(lastLoginDatetime);
        dest.writeValue(leaves);
        dest.writeValue(level);
        dest.writeValue(losses);
        dest.writeValue(masteryLevel);
        dest.writeValue(name);
        dest.writeValue(personalStatusMessage);
        dest.writeValue(region);
        dest.writeValue(teamId);
        dest.writeValue(teamName);
        dest.writeValue(totalAchievements);
        dest.writeValue(totalWorshippers);
        dest.writeValue(wins);
        dest.writeValue(retMsg);
    }

    public int describeContents() {
        return 0;
    }

    public static ArrayList<Player> loadDataById(int id) {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Player.class).getTableName();
        return (ArrayList<Player>) RushCore.getInstance().load(Player.class, "SELECT * FROM " + table + " WHERE id = " + id);
    }

    public static ArrayList<Player> loadDataByName(String name) {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Player.class).getTableName();
        return (ArrayList<Player>) RushCore.getInstance().load(Player.class, "SELECT * FROM " + table + " WHERE name = \"" + name + "\"");
    }

    public static ArrayList<Player> loadAllData() {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Player.class).getTableName();
        return (ArrayList<Player>) RushCore.getInstance().load(Player.class, "SELECT * FROM " + table + " ORDER BY name ASC");
    }

    public static void delete(Player player) {
        RushCore.getInstance().delete(player);
    }

    public static long countData() {
        Map<Class<? extends Rush>, AnnotationCache> annotationCache = RushCore.getInstance().getAnnotationCache();
        String table = annotationCache.get(Player.class).getTableName();
        return RushCore.getInstance().count("SELECT COUNT(*) FROM " + table);
    }
}
