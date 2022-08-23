package com.aayushigupta.algovise.tools;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;

public class CountDownTimerFactory {

    private static CountDownTimer timer;
    private static long stepCount;
    private static boolean isPaused = false;
    private static long pausedAtMillisecond;
    private static CountDownTimerListener listener;
    public static long delayInMillisecond = 1500;
    public static long timeLeftInMillisecond;

    public static void setCountDownTimerListener(CountDownTimerListener l) {
        listener = l;
    }

    public static void setStepCount(long count) {
        stepCount = count;
        timeLeftInMillisecond = stepCount * delayInMillisecond;
    }

    public static void start() {
        if(isPaused) {
            timeLeftInMillisecond = pausedAtMillisecond;
            isPaused = false;
        } else {
            timeLeftInMillisecond = stepCount * delayInMillisecond;
        }
        timer = new CountDownTimer(timeLeftInMillisecond, delayInMillisecond) {
            @Override
            public void onTick(long timeToFinishInMillisecond) {
                if (listener.isEnabled()) {
                    listener.onTick(timeToFinishInMillisecond);
                }
            }
            @Override
            public void onFinish() {
                if (listener.isEnabled()) {
                    listener.onFinish();
                }
            }
        };
        timer.start();
    }

    public static void pause() {
        isPaused = true;
        pausedAtMillisecond = timeLeftInMillisecond;
        if (timer != null) {
            timer.cancel();
        }
    }

    public static boolean isPaused() {
        return isPaused;
    }

    public static void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

}