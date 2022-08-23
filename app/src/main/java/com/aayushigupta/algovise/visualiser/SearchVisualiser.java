package com.aayushigupta.algovise.visualiser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.algorithms.model.TreeNode;

public class SearchVisualiser extends View {

    public static int screenHeight, screenWidth;
    private static final int TEXT_SIZE = 50;
    Paint paint;
    Paint bluePaint; //blue paint
    Paint redPaint; //red paint
    Paint textPaint;
    Paint arrowPaint;
    private Object dataStructure = null;
    enum DataType {
        ARRAY,
        TREE
    }
    DataType dataType = DataType.ARRAY;
    boolean showArrow = false;
    private int[] arr = {3, 1, 8, 4, 2, 9};
    private int[] color_array = {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN};
    private TreeNode treeNode;

    public SearchVisualiser(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public SearchVisualiser(Context context) {
        super(context);
        init();
    }

    public Object getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(Object dataStructure) {
        this.dataStructure = dataStructure;
        if(dataStructure instanceof DataNode[]) {
            DataNode[] dataNodes = (DataNode[]) dataStructure;
            int[] array = new int[dataNodes.length];
            int[] colors = new int[dataNodes.length];
            for (int i = 0; i < dataNodes.length; i++) {
                array[i] = dataNodes[i].getValue();
                colors[i] = dataNodes[i].getColor();
            }
            setArr(array);
            setColor_array(colors);
        }
        if(dataStructure instanceof TreeNode) {
            dataType = DataType.TREE;
            treeNode = (TreeNode) dataStructure;
        }
    }

    private int[] getArr() {
        return arr;
    }

    private void setArr(int[] arr) {
        this.arr = arr;
    }

    private int[] getColor_array() {
        return color_array;
    }

    private void setColor_array(int[] color_array) {
        this.color_array = color_array;
    }

    public boolean getShowArrow() {
        return showArrow;
    }

    public void setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#009688"));
        paint.setStyle(Paint.Style.FILL);
        //paint.setStrokeWidth(lineStrokeWidth);

        bluePaint = new Paint(paint);
        bluePaint.setColor(Color.BLUE);

        redPaint = new Paint(paint);
        redPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TEXT_SIZE);

        arrowPaint = new Paint();
        arrowPaint.setColor(Color.BLACK);
        arrowPaint.setStrokeWidth(3);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(dataType == DataType.ARRAY) {
            int padding = 20;
            int height = 120;
            int width = 120;
            int newHeight = (300 - 20) / 2 - (height / 2);
            int newWidth = 0;
            int array_length = arr.length * width;
            if(array_length < (screenWidth - 20)) {
                newWidth = ((screenWidth - 20) - (array_length - 1)) / 2 - (width / 2);
            }
            int textOffset = -TEXT_SIZE/4;
            int textShift = 0;
            Rect rect;
            int lastCenterX = -1;
            int lastCenterY = -1;
            for (int i = 0; i < arr.length; i++) {
                int value = arr[i];
                int color = color_array[i];
                rect = new Rect(padding + newWidth, padding + newHeight, newWidth + width - padding, newHeight + height - padding);
                switch (color) {
                    case Color.GREEN:
                        canvas.drawRect(rect, paint);
                        break;
                    case Color.RED:
                        canvas.drawRect(rect, redPaint);
                        break;
                    case Color.BLUE:
                        canvas.drawRect(rect, bluePaint);
                        break;
                }
                if(showArrow) {
                    if(lastCenterX > 0 && lastCenterY > 0) {
                        canvas.drawLine(lastCenterX + width / 4, lastCenterY, rect.centerX() - width / 4, rect.centerY(), arrowPaint);
                        if(lastCenterY == rect.centerY()) {
                            int midX = (lastCenterX + rect.centerX()) / 2;
                            int midY = (lastCenterY + rect.centerY()) / 2;
                            canvas.drawLine(midX, midY, midX - 10, midY - 10, arrowPaint);
                            canvas.drawLine(midX, midY, midX - 10, midY + 10, arrowPaint);
                        }
                    }
                    lastCenterX = rect.centerX();
                    lastCenterY = rect.centerY();
                }
                canvas.drawText(String.valueOf(value), rect.centerX() + textShift - width / 4, rect.centerY() + textOffset + height / 4, textPaint);
                newWidth += width;
                if(newWidth + width > (screenWidth - 20)) {
                    newWidth = 0;
                    newHeight += height;
                }
            }
        }
        if(dataType == DataType.TREE) {
            int padding = 20;
            int height = 120;
            int width = 120;
            int x = (screenWidth - 20) / 2 - width / 2;
            int y = 0;
            drawTreeNode(canvas, treeNode, x + padding, y + padding, x + width - padding, y + height - padding, (screenWidth - 20) / 4);
        }
        super.onDraw(canvas);
    }

    public void drawTreeNode(Canvas canvas, TreeNode treeNode, int x1, int y1, int x2, int y2, int nodeDistance) {
//        int textOffset = -TEXT_SIZE/4;
//        int textShift = 0;
        int height = 120;
        int width = 120;
        Rect rect = new Rect(x1, y1, x2, y2);
        float centerX = (float) (rect.centerX());
        float centerY = (float) (rect.centerY());
        if(treeNode.getLeftChild() != null) {
            canvas.drawLine(centerX, centerY, centerX - nodeDistance, centerY + height, arrowPaint);
        }
        if(treeNode.getRightChild() != null) {
            canvas.drawLine(centerX, centerY, centerX + nodeDistance, centerY + height, arrowPaint);
        }
        int color = treeNode.getColor();
        switch (color) {
            case Color.GREEN:
                canvas.drawRect(rect, paint);
                break;
            case Color.RED:
                canvas.drawRect(rect, redPaint);
                break;
            case Color.BLUE:
                canvas.drawRect(rect, bluePaint);
                break;
        }
        canvas.drawText(String.valueOf(treeNode.getValue()), rect.centerX() - width / 4, rect.centerY() + height / 4, textPaint);
        if(treeNode.getLeftChild() != null) {
            drawTreeNode(canvas, treeNode.getLeftChild(), x1 - nodeDistance, y1 + height, x2 - nodeDistance, y2 + height, Math.max(width, nodeDistance / 2));
        }
        if(treeNode.getRightChild() != null) {
            drawTreeNode(canvas, treeNode.getRightChild(), x1 + nodeDistance, y1 + height, x2 + nodeDistance, y2 + height, Math.max(width, nodeDistance / 2));
        }
    }

}
