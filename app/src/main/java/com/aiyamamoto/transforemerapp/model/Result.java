package com.aiyamamoto.transforemerapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aiyamamoto on 2018-09-15.
 */

public class Result implements Serializable{

    private int numberObButtle;
    private String winningTeam;
    private String winningTransformer;
    private String losongTeam;
    private ArrayList<String> survivors;
    private String message;

    public Result(int numberObButtle, String winningTeam, String winningTransformer, String losongTeam, ArrayList<String> survivors) {
        this.numberObButtle = numberObButtle;
        this.winningTeam = winningTeam;
        this.winningTransformer = winningTransformer;
        this.losongTeam = losongTeam;
        this.survivors = survivors;
    }

    public int getNumberObButtle() {
        return numberObButtle;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public String getWinningTransformer() {
        return winningTransformer;
    }

    public String getLosongTeam() {
        return losongTeam;
    }

    public ArrayList<String> getSurvivors() {
        return survivors;
    }

    public String getMessage() {
        return message;
    }
}
