package cn.lankton.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by taofangxin on 16/4/16.
 */
public class FlowLayout extends ViewGroup {

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
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int lineUsed = 0;
        int lineY = 0;
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
                lineUsed = 0;
                lineHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, lineUsed, heightMeasureSpec, lineY);
                childWidth = child.getMeasuredWidth();
                childHeight = child.getMeasuredHeight();
                spaceWidth = mlp.leftMargin + childWidth + mlp.rightMargin;
                spaceHeight = mlp.topMargin + childHeight + mlp.bottomMargin;
            }
            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight;
            }
            lineUsed += mlp.leftMargin + childWidth + mlp.rightMargin;
        }
        setMeasuredDimension(
                widthSize,
                heightMode == MeasureSpec.EXACTLY ? heightSize : lineY + lineHeight
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineY = 0;
        int lineWidth = r - l;
        int lineUsed = 0;
        int lineHeight = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            Log.v("lanktondebug", "" + childWidth + "," + childHeight);
            int spaceWidth = mlp.leftMargin + childWidth + mlp.rightMargin;
            int spaceHeight = mlp.topMargin + childHeight + mlp.bottomMargin;
            if (lineUsed + spaceWidth > lineWidth) {
                //approach the limit of width and move to next line
                lineY += lineHeight;
                lineUsed = 0;
                lineHeight = 0;
            }
            child.layout(lineUsed + mlp.leftMargin, lineY + mlp.topMargin, lineUsed + mlp.leftMargin + childWidth, lineY + mlp.topMargin + childHeight);
            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight;
            }
            lineUsed += mlp.leftMargin + childWidth + mlp.rightMargin;

        }
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
