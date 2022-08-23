package com.aayushigupta.algovise.tools;

public interface CountDownTimerListener {
    void onTick(long timeToFinishInMillisecond);

    void onFinish();

    boolean isEnabled();
}
