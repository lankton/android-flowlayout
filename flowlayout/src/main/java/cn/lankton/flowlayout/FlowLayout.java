package cn.lankton.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taofangxin on 16/4/16.
 */
public class FlowLayout extends ViewGroup {

    private int usefulWidth;
    List<View> childList = new ArrayList();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mPaddingLeft = getPaddingLeft();
        int mPaddingRight = getPaddingRight();
        int mPaddingTop = getPaddingTop();
        int mPaddingBottom = getPaddingBottom();

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int lineUsed = mPaddingLeft + mPaddingRight;
        int lineY = mPaddingTop;
        int lineX = mPaddingLeft;
        int lineHeight = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChildWithMargins(child, widthMeasureSpec, lineUsed, heightMeasureSpec, lineY);
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int spaceWidth = mlp.leftMargin + childWidth + mlp.rightMargin;
            int spaceHeight = mlp.topMargin + childHeight + mlp.bottomMargin;
            if (lineUsed + spaceWidth > widthSize) {
                //approach the limit of width and move to next line
                lineY += lineHeight;
                lineUsed = mPaddingLeft + mPaddingRight;
                lineHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, lineUsed, heightMeasureSpec, lineY);
                childHeight = child.getMeasuredHeight();
                spaceHeight = mlp.topMargin + childHeight + mlp.bottomMargin;
            }
            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight;
            }
            lineUsed += spaceWidth;
        }
        setMeasuredDimension(
                widthSize,
                heightMode == MeasureSpec.EXACTLY ? heightSize : lineY + lineHeight + mPaddingBottom
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mPaddingLeft = getPaddingLeft();
        int mPaddingRight = getPaddingRight();
        int mPaddingTop = getPaddingTop();
        int mPaddingBottom = getPaddingBottom();

        int lineX = mPaddingLeft;
        int lineY = mPaddingTop;
        int lineWidth = r - l;
        usefulWidth = lineWidth - mPaddingLeft - mPaddingRight;
        int lineUsed = mPaddingLeft + mPaddingRight;
        int lineHeight = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int spaceWidth = mlp.leftMargin + childWidth + mlp.rightMargin;
            int spaceHeight = mlp.topMargin + childHeight + mlp.bottomMargin;
            if (lineUsed + spaceWidth > lineWidth) {
                //approach the limit of width and move to next line
                lineY += lineHeight;
                lineUsed = mPaddingLeft + mPaddingRight;
                lineX = mPaddingLeft;
                lineHeight = 0;
            }
            child.layout(lineX + mlp.leftMargin, lineY + mlp.topMargin, lineX + mlp.leftMargin + childWidth, lineY + mlp.topMargin + childHeight);
            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight;
            }
            lineUsed += spaceWidth;
            lineX += spaceWidth;

        }
    }

    public void relayoutToFill() {
        int childCount = this.getChildCount();
        if (0 == childCount) {
            //no need to sort if flowlayout has no child view
            return;
        }
        View[] childs = new View[childCount];
        int[] spaces = new int[childCount];
        for (int i = 0; i < childCount; i++) {
            childs[i] = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) childs[i].getLayoutParams();
            int childWidth = childs[i].getMeasuredWidth();
            spaces[i] = mlp.leftMargin + childWidth + mlp.rightMargin;
        }
        sortToFill(childs, spaces);
        this.removeAllViews();
        for (View v : childList) {
            this.addView(v);
        }
        childList.clear();
//        int[][] table = new int[childCount + 1][usefulWidth + 1];
//        for (int i = 0; i < childCount +1; i++) {
//            for (int j = 0; j < usefulWidth; j++) {
//                table[i][j] = 0;
//            }
//        }
//
//        boolean[] flag = new boolean[childCount];
//        for (int i = 0; i < childCount; i++) {
//            flag[i] = false;
//        }
//        for (int i = 1; i <= childCount; i++) {
//            for (int j = spaces[i-1]; j <= usefulWidth; j++) {
//                table[i][j] = (table[i-1][j] > table[i-1][j-spaces[i-1]] + spaces[i-1]) ? table[i-1][j] : table[i-1][j-spaces[i-1]] + spaces[i-1];
//            }
//        }
//        int v = usefulWidth;
//        for (int i = childCount ; i > 0 && v >= spaces[i-1]; i--) {
//                if (table[i][v] == table[i-1][v-spaces[i-1]] + spaces[i-1]) {
//                    flag[i-1] =  true;
//                    v = v - spaces[i - 1];
//                }
//        }
//        for (int i = 0; i < flag.length; i++) {
//            if (flag[i] == true) {
//                if (childList.contains(childs[i]))
//                Log.v("lanktondebug", "" + i + ":" + spaces[i]);
//            }
//        }
//        int[] f = new int[usefulWidth + 1];
//        for (int i = 0; i < usefulWidth + 1; i++) {
//            table[i] = 0;
//        }
//        for (int i = 0; i < childCount; i++) {
//            for (int j = usefulWidth; j >= spaces[i]; j--) {
//                table[j] = (table[j] > table[j-spaces[i]] + spaces[i]) ? table[j] : table[j-spaces[i]] + spaces[i];
//            }
//        }
//        int v = usefulWidth;
//        for (int i = childCount ; i > 0 && v >= spaces[i-1]; i--) {
//                if (table[v] == table[v-spaces[i-1]] + spaces[i-1]) {
//                    flag[i-1] =  true;
//                    v = v - spaces[i - 1];
//                }
//        }
//        for (int i = 0; i < flag.length; i++) {
//            if (flag[i] == true) {
//                Log.v("lanktondebug", "" + i + ":" + spaces[i]);
//            }
//        }
    }

    private void sortToFill(View[] childs, int[] spaces) {
        int childCount = childs.length;
        int[][] table = new int[childCount + 1][usefulWidth + 1];
        for (int i = 0; i < childCount +1; i++) {
            for (int j = 0; j < usefulWidth; j++) {
                table[i][j] = 0;
            }
        }
        boolean[] flag = new boolean[childCount];
        for (int i = 0; i < childCount; i++) {
            flag[i] = false;
        }
        for (int i = 1; i <= childCount; i++) {
            for (int j = spaces[i-1]; j <= usefulWidth; j++) {
                table[i][j] = (table[i-1][j] > table[i-1][j-spaces[i-1]] + spaces[i-1]) ? table[i-1][j] : table[i-1][j-spaces[i-1]] + spaces[i-1];
            }
        }
        int v = usefulWidth;
        for (int i = childCount ; i > 0 && v >= spaces[i-1]; i--) {
            if (table[i][v] == table[i-1][v-spaces[i-1]] + spaces[i-1]) {
                flag[i-1] =  true;
                v = v - spaces[i - 1];
            }
        }
        int rest = childCount;
        View[] restArray;
        int[] restSpaces;
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == true) {
                Log.v("lanktondebug", "" + i + ":" + spaces[i]);
                childList.add(childs[i]);
                rest--;
            }
        }
        Log.v("lanktondebug", "" + table[childCount][usefulWidth] + "/" + usefulWidth);
        if (0 == rest) {
            return;
        }
        restArray = new View[rest];
        restSpaces = new int[rest];
        int index = 0;
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == false) {
                restArray[index] = childs[i];
                restSpaces[index] = spaces[i];
                index++;
            }
        }
        table = null;
        childs = null;
        flag = null;
        sortToFill(restArray, restSpaces);
    }
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
