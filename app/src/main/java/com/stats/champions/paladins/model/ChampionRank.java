package com.stats.champions.paladins.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChampionRank {

    @SerializedName("Assists")
    @Expose
    private int assists;
    @SerializedName("Deaths")
    @Expose
    private int deaths;
    @SerializedName("Kills")
    @Expose
    private int kills;
    @SerializedName("Losses")
    @Expose
    private int losses;
    @SerializedName("MinionKills")
    @Expose
    private int minionKills;
    @SerializedName("Rank")
    @Expose
    private int rank;
    @SerializedName("Wins")
    @Expose
    private int wins;
    @SerializedName("Worshippers")
    @Expose
    private int worshippers;
    @SerializedName("champion")
    @Expose
    private String champion;
    @SerializedName("champion_id")
    @Expose
    private String championId;
    @SerializedName("player_id")
    @Expose
    private String playerId;
    @SerializedName("ret_msg")
    @Expose
    private String retMsg;

    private String championIconUrl;

    public ChampionRank() {
    }

    public String getChampionIconUrl() {
        return championIconUrl;
    }

    public void setChampionIconUrl(String championIconUrl) {
        this.championIconUrl = championIconUrl;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getMinionKills() {
        return minionKills;
    }

    public void setMinionKills(int minionKills) {
        this.minionKills = minionKills;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWorshippers() {
        return worshippers;
    }

    public void setWorshippers(int worshippers) {
        this.worshippers = worshippers;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

}
