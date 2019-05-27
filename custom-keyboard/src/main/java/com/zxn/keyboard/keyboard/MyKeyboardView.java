package com.zxn.keyboard.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.List;

//核心类，承担绘制工作
public class MyKeyboardView extends KeyboardView {

    private Drawable mKeybgDrawable;
    private Drawable mKeyDoneBgDrawable;
    private Rect rect;
    private Paint paint;
    private String TAG = "MyKeyboardView";

    public MyKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResources(context);
    }

    public MyKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initResources(context);
    }

    private void initResources(Context context) {
        Resources res = context.getResources();
        rect = new Rect();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        paint.setColor(res.getColor(android.R.color.black));
    }

    public void setKeybgDrawable(Drawable mKeybgDrawable) {
        this.mKeybgDrawable = mKeybgDrawable;
        invalidate();
    }

    public void setDoneBgDrawable(Drawable drawable) {//keyDoneBgDrawable
        this.mKeyDoneBgDrawable = drawable;
        invalidate();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
        if (paint == null) {
            throw new NullPointerException("Paint is not null");
        }
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {
            canvas.save();
            int offsety = 0;
            if (key.y == 0) {
                offsety = 1;
            }
            int initdrawy = key.y + offsety;

            if (key.codes.length > 0 && key.codes[0] == Keyboard.KEYCODE_DONE) {
                if (mKeyDoneBgDrawable != null) {
                    rect.left = key.x;
                    rect.top = initdrawy;
                    rect.right = key.x + key.width;
                    rect.bottom = key.y + key.height;
                    canvas.clipRect(rect);
                    int[] state = key.getCurrentDrawableState();
                    //设置按压效果
                    mKeyDoneBgDrawable.setState(state);
                    //设置边距
                    mKeyDoneBgDrawable.setBounds(rect);
                    mKeyDoneBgDrawable.draw(canvas);
                }
            } else {
                if (mKeybgDrawable != null) {
                    rect.left = key.x;
                    rect.top = initdrawy;
                    rect.right = key.x + key.width;
                    rect.bottom = key.y + key.height;
                    canvas.clipRect(rect);
                    int[] state = key.getCurrentDrawableState();
                    //设置按压效果
                    mKeybgDrawable.setState(state);
                    //设置边距
                    mKeybgDrawable.setBounds(rect);
                    mKeybgDrawable.draw(canvas);
                }
            }

            if (key.label != null) {
                canvas.drawText(
                        key.label.toString(),
                        key.x + (key.width / 2),
                        initdrawy + (key.height + paint.getTextSize() - paint.descent()) / 2,
                        paint);
            } else if (key.icon != null) {
                int intriWidth = key.icon.getIntrinsicWidth();
                int intriHeight = key.icon.getIntrinsicHeight();

                final int drawableX = key.x + (key.width - intriWidth) / 2;
                final int drawableY = initdrawy + (key.height - intriHeight) / 2;

                key.icon.setBounds(
                        drawableX, drawableY, drawableX + intriWidth,
                        drawableY + intriHeight);

                key.icon.draw(canvas);

                for (int i = 0; i < key.codes.length; i++) {
                    Log.i(TAG, "onDraw: codes" + i + ":" + key.codes[i]);
                }
            }

            canvas.restore();
        }
    }

}
