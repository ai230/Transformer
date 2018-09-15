package com.aiyamamoto.transforemerapp.model;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class Transformer {
    private String id;
    private String name;
    private String strength;
    private String intelligence;
    private String speed;
    private String endurance;
    private String rank;
    private String courage;
    private String firepower;
    private String skill;
    private String team; // "A" or "D"
    private String team_icon;

    private String overAllRating;

    public Transformer(String id, String name, String strength, String intelligence, String speed, String endurance, String rank, String courage, String firepower, String skill, String team, String team_icon) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.intelligence = intelligence;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;
        this.team = team;
        this.team_icon = team_icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStrength() {
        return strength;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public String getSpeed() {
        return speed;
    }

    public String getEndurance() {
        return endurance;
    }

    public String getRank() {
        return rank;
    }

    public String getCourage() {
        return courage;
    }

    public String getFirepower() {
        return firepower;
    }

    public String getSkill() {
        return skill;
    }

    public String getTeam() {
        return team;
    }

    public String getTeam_icon() {
        return team_icon;
    }

    public String getOverAllRating() {
        return overAllRating;
    }

    public void setOverAllRating(String overAllRating) {
        this.overAllRating = overAllRating;
    }
}

