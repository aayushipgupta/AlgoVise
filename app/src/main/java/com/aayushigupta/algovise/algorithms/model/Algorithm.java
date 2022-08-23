package com.aayushigupta.algovise.algorithms.model;

import android.app.Activity;
import android.graphics.Color;

import java.util.List;

public abstract class Algorithm {
    public static final String[] languages = new String[4];
    public static final int DATANODE_COLOR = Color.GREEN;
    public static final int SUCCESS_COLOR = Color.RED;
    public static final int FAILURE_COLOR = Color.BLUE;

    static {
        languages[0] = "Pseudocode";
        languages[1] = "Java";
        languages[2] = "Python";
        languages[3] = "C";
    }

    public abstract void reset();

    public abstract void executeNextStep();

    public abstract boolean isAlgoComplete();

    public abstract Object getDataStrucure();

    public abstract void setDataStrucure(Object dataStrucure);

    public abstract void setAlgoParams(List<String> params);

    public abstract String getName();

    public abstract String getDescription();

    public abstract int getMaxStepCount();

    public abstract String getCode(String language);

    public abstract String getFirstParamLabel();

    public abstract String getSecondParamLabel();

    public String getSecondButtonLabel() {
        return null;
    }

    public String getThirdParamLabel() {
        return null;
    }

    public String getThirdButtonLabel() {
        return null;
    }

    public String getOperations() {
        return "";
    }

    public boolean getShowArrow() {
        return false;
    }
}
