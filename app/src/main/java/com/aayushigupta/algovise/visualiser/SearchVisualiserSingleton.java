package com.aayushigupta.algovise.visualiser;

public class SearchVisualiserSingleton {

    private static SearchVisualiser view;

    public static SearchVisualiser getView() {
        return view;
    }

    public static void setView(SearchVisualiser viewObj) {
        view = viewObj;
    }
}
