package com.zxn.keyboard.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.zxn.keyboard.OnInputListener;
import com.zxn.keyboard.R;

import java.lang.ref.WeakReference;


/**
 * 输入支付密码
 * Created by zxn on 2020/9/8.
 */
public class PopEnterPassword extends PopupWindow {

    private OnInputListener mOnInputListener;
    private PasswordView pwdView;
    private View mMenuView;
    private Activity mContext;

    public PopEnterPassword(Context context, OnInputListener listener) {
        super(context);
        this.mOnInputListener = listener;
    }

    public PopEnterPassword(final Activity context) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);
        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);
        //添加密码输入完成的响应
        pwdView.setOnFinishInput(new OnInputListener() {
            @Override
            public void inputFinish(final String password) {
                pwdView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                        //Toast.makeText(mContext, "支付成功，密码为：" + password, Toast.LENGTH_SHORT).show();
                        if (null != mOnInputListener) {
                            mOnInputListener.inputFinish(password);
                        }
                    }
                }, 250);
            }
        });

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }

    public void setOnInputListener(OnInputListener listener) {
        this.mOnInputListener = listener;
        /*if (null != listener) {
            pwdView.setOnFinishInput(listener);
        }*/
    }

    public void show() {
        View decorView = mContext.getWindow().getDecorView();
        showAtLocation(decorView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}
