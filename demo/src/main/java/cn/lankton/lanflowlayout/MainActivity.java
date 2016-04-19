package cn.lankton.lanflowlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import cn.lankton.flowlayout.FlowLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FlowLayout flowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout = (FlowLayout) findViewById(R.id.flowlayout);
        for (int i = 0; i < flowLayout.getChildCount(); i++) {
            View child = flowLayout.getChildAt(i);
            child.setOnClickListener(this);
        }
        findViewById(R.id.btn_add_random).setOnClickListener(this);
        findViewById(R.id.btn_all_visible).setOnClickListener(this);
        findViewById(R.id.btn_remove_all).setOnClickListener(this);
        findViewById(R.id.btn_sort).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_visible:
                for (int i = 0; i < flowLayout.getChildCount(); i++) {
                    View child = flowLayout.getChildAt(i);
                    child.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_add_random:
                int ranWidth = dip2px(this, 20 + (int) (80 * Math.random()));
                int ranHeight = dip2px(this, 20 + (int) (15 * Math.random()));
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ranWidth, ranHeight);
                lp.setMargins(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), dip2px(this, 10));
                View view = new View(this);
                view.setBackgroundColor(Color.parseColor("#777777"));
                flowLayout.addView(view, lp);
                view.setOnClickListener(this);
                break;
            case R.id.btn_remove_all:
                flowLayout.removeAllViews();
                break;
            case R.id.btn_sort:
                flowLayout.sort();
                break;
            default:
                v.setVisibility(View.GONE);
                break;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
