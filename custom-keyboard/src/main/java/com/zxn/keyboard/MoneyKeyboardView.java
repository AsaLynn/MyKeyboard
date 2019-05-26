package com.zxn.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * 自定义收钱的键盘.
 * Created by zxn on 2019/5/26.
 */
public class MoneyKeyboardView extends KeyboardView {

    public MoneyKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoneyKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setPreviewEnabled(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
//        List<Keyboard.Key> keys = getKeyboard().getKeys();
//        for(Keyboard.Key key: keys) {
//            if("aa".equals(key.label)){
//                //resetOKBtn(key, canvas);
//            }
//        }
        super.onDraw(canvas);
    }

}
