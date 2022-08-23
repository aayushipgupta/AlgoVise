package com.aayushigupta.algovise.visualiser;

import android.widget.ScrollView;
import android.widget.TextView;

import com.aayushigupta.algovise.ui.home.VisualFragment;

public class AlgorithmLogSingleton {

    private static String logs;
    private static TextView view;
    private static ScrollView scroll;

    public static TextView getView() {
        return view;
    }

    public static void setView(TextView viewObj) {
        view = viewObj;
    }

    public static ScrollView getScroll() {
        return scroll;
    }

    public static void setScroll(ScrollView scrollObj) {
        scroll = scrollObj;
    }

    public static void addLogLine(String logLine) {
        if (view != null) {
            VisualFragment.visualFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logs += "\n" + logLine;
                    view.setText(logs);
                }
            });
        }
        if(scroll != null) {
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    public static void refreshLogs() {
        if (view != null && VisualFragment.visualFragment != null && VisualFragment.visualFragment.getActivity() != null) {
            VisualFragment.visualFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.setText(logs);
                }
            });
        }
    }

    public static void clearLogs() {
        if (view != null && VisualFragment.visualFragment != null && VisualFragment.visualFragment.getActivity() != null) {
            VisualFragment.visualFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logs = "";
                    view.setText(logs);
                }
            });
        }
    }

}
