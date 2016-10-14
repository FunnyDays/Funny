package com.carpediem.vv.funny.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carpediem.vv.funny.R;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CustomTextView extends TextView {

    private String titleText;
    private int titleTextcolor;
    private int titleTextcolorSize;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomTitleView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int a = typedArray.getIndex(i);
            switch (a) {
                case R.styleable.CustomTitleView_mytitleText:
                    titleText = typedArray.getString(a);
                    break;
                case R.styleable.CustomTitleView_mytitleTextColor:
                    titleTextcolor = typedArray.getColor(a, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_mytitleTextSize:
                    titleTextcolorSize = typedArray.getDimensionPixelSize(a, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

        Paint paint = new Paint();
    }

}
