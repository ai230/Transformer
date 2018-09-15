package com.aiyamamoto.transforemerapp.model;

import java.io.Serializable;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class Transformer implements Serializable{
    private String id;
    private String name;
    private int strength;
    private int intelligence;
    private int speed;
    private int endurance;
    private int rank;
    private int courage;
    private int firepower;
    private int skill;
    private String team; // "A" or "D"
    private String team_icon;

    private int overallRating;

//    private int result; // noresult:0 win:1 lose:2 tie:3
    private String result; // noresult:0 win:1 lose:2 tie:3

    public Transformer(String id, String name, int strength, int intelligence, int speed, int endurance, int rank, int courage, int firepower, int skill, String team, String team_icon) {
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

    public String getTeam() {
        return team;
    }

    public String getTeam_icon() {
        return team_icon;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    //    public void setResult(int result) {
//        this.result = result;
//    }
//
//    public int getResult() {
//        return result;
//    }
}

