package com.aiyamamoto.transforemerapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aiyamamoto on 2018-09-15.
 */

public class Result implements Serializable{

    private int numberOfBattle;
    private String winningTeam;
    private String winningTransformer;
    private ArrayList<String> survivors;
    private boolean isOptimusAndPredakingFaced;

    public Result(){
        this.isOptimusAndPredakingFaced = false;
        this.numberOfBattle = 0;
        this.winningTeam = "";
        this.winningTransformer = "";
        this.survivors = new ArrayList<>();
    }

    public int getNumberObButtle() {
        return numberOfBattle;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public String getWinningTransformer() {
        return winningTransformer;
    }


    public ArrayList<String> getSurvivors() {
        return survivors;
    }

    public void setNumberOfBattle(int numberOfBattle) {
        this.numberOfBattle = numberOfBattle;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }

    public void setWinningTransformer(String winningTransformer) {
        this.winningTransformer = winningTransformer;
    }

    public void setSurvivors(ArrayList<String> survivors) {
        this.survivors = survivors;
    }

    public boolean isOptimusAndPredakingFaced() {
        return isOptimusAndPredakingFaced;
    }

    public void setOptimusAndPredakingFaced(boolean optimusAndPredakingFaced) {
        isOptimusAndPredakingFaced = optimusAndPredakingFaced;
    }
}
