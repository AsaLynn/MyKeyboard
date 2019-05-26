package com.zxn.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.NinePatchDrawable;
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
		List<Key> keys = getKeyboard().getKeys();
		for(Key key: keys) {
			if("aa".equals(key.label)){
				//resetOKBtn(key, canvas);
				resetDELBtn(key, canvas);
			}
		}
		super.onDraw(canvas);
    }
	
	/**
	 * 绘制OK键的点9图
	 * @author Song
	 * @param key
	 * @param canvas
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
}