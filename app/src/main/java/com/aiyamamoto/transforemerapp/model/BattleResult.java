package com.aiyamamoto.transforemerapp.model;

/**
 * Created by aiyamamoto on 2018-09-15.
 */

public class BattleResult {

    private boolean isNameWin;
    private boolean isCourageAndStrengthWin;
    private boolean isSkillWin;
    private boolean isOverallRatingWin;

    public BattleResult() {
        this.isNameWin = false;
        this.isCourageAndStrengthWin = false;
        this.isSkillWin = false;
        this.isOverallRatingWin = false;
    }

    public void setNameWin(boolean nameWin) {
        isNameWin = nameWin;
    }

    public void setCourageAndStrengthWin(boolean courageAndStrengthWin) {
        isCourageAndStrengthWin = courageAndStrengthWin;
    }

    public void setSkillWin(boolean skillWin) {
        isSkillWin = skillWin;
    }

    public void setOverallRatingWin(boolean overallRatingWin) {
        isOverallRatingWin = overallRatingWin;
    }

    public boolean isNameWin() {
        return isNameWin;
    }

    public boolean isCourageAndStrengthWin() {
        return isCourageAndStrengthWin;
    }

    public boolean isSkillWin() {
        return isSkillWin;
    }

    public boolean isOverallRatingWin() {
        return isOverallRatingWin;
    }
}
