package com.aiyamamoto.transforemerapp.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformerResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("team")
    @Expose
    private String team;

    @SerializedName("strength")
    @Expose
    private int strength;
    @SerializedName("intelligence")
    @Expose
    private int intelligence;
    @SerializedName("speed")
    @Expose
    private int speed;
    @SerializedName("endurance")
    @Expose
    private int endurance;
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("courage")
    @Expose
    private int courage;
    @SerializedName("firepower")
    @Expose
    private int firepower;
    @SerializedName("skill")
    @Expose
    private int skill;

    @SerializedName("team_icon")
    @Expose
    private String team_icon;

    public TransformerResponse(String id, String name, String team, int strength, int intelligence, int speed, int endurance, int rank, int courage, int firepower, int skill, String team_icon) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.strength = strength;
        this.intelligence = intelligence;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;
        this.team_icon = team_icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getRank() {
        return rank;
    }

    public int getCourage() {
        return courage;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getSkill() {
        return skill;
    }

}
