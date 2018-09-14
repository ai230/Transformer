package com.aiyamamoto.transforemerapp.model;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class Transformer {
    private int id;
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

    public Transformer(int id, String name, int speed, int endurance, int rank, int courage, int firepower, int skill, String team) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;
        this.team = team;
    }

    public int getId() {
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

    public void setTeam_icon(String team_icon) {
        this.team_icon = team_icon;
    }

    public String getTeam_icon() {
        return team_icon;
    }
}
