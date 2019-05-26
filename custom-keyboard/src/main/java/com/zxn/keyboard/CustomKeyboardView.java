package com.zxn.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import java.util.List;

public class CustomKeyboardView extends KeyboardView {

    private Context context;

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setPreviewEnabled(false);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onDraw(Canvas canvas) {
//		List<Key> keys = getKeyboard().getKeys();
//		for(Key key: keys) {
//			if("aa".equals(key.label)){
//				//resetOKBtn(key, canvas);
//				resetDELBtn(key, canvas);
//			}
//		}

        onDrawKeyboard(canvas);

        super.onDraw(canvas);
    }

    /**
     * 绘制OK键的点9图
     *
     * @param key
     * @param canvas
     * @author Song
     */
    private void resetOKBtn(Key key, Canvas canvas) {
        //将OK键重新绘制
        NinePatchDrawable npd = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.ok_undefined);
        npd.setBounds(key.x, key.y + 1, key.x + key.width, key.y + key.height + 1);
        npd.draw(canvas);
    }

    private void resetDELBtn(Key key, Canvas canvas) {
        //将OK键重新绘制
        key.label = null;
        //key.icon = this.getResources().getDrawable(R.drawable.sym_keyboard_delete);
        NinePatchDrawable npd = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.iv_del);
        npd.setBounds(key.x, key.y + 1, key.x + key.width, key.y + key.height + 1);
        npd.draw(canvas);
    }

    public void showWithAnimation(Animation animation) {
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.VISIBLE);
            }
        });
        setAnimation(animation);
    }

    /**
     * 绘制按键.
     *
     * @param canvas
     */
    private void onDrawKeyboard(Canvas canvas) {
        Keyboard keyboard = getKeyboard();
        if (keyboard == null) return;
        List<Keyboard.Key> keys = keyboard.getKeys();
        if (keys != null && keys.size() > 0) {
            Paint paint = new Paint();
            paint.setTextAlign(Paint.Align.CENTER);
            Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
            paint.setTypeface(font);
            paint.setAntiAlias(true);
            for (Keyboard.Key key : keys) {
                if (key.codes[0] == -4) {
//                    Drawable dr = getContext().getResources().getDrawable(R.drawable.keyboard_blue);
//                    dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
//                    dr.draw(canvas);
                } else {
                    Drawable dr = getContext().getResources().getDrawable(R.drawable.bg_sp_keyboard_btn_normal);
                    dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    dr.draw(canvas);
                }
                if (key.label != null) {
                    if (key.codes[0] == -4 ||
                            key.codes[0] == -5) {
                        paint.setTextSize(17 * 2);
                    } else {
                        paint.setTextSize(20 * 2);
                    }
//                    if (key.codes[0] == -4) {
//                        paint.setColor(getContext().getResources().getColor(android.R.color.white));
//                    } else {
//                        paint.setColor(getContext().getResources().getColor(R.color.blue_03A9F4));
//                    }
                    Rect rect = new Rect(key.x, key.y, key.x + key.width, key.y + key.height);
                    Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                    int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                    // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(key.label.toString(), rect.centerX(), baseline, paint);
                }
            }


        }
    }

}

