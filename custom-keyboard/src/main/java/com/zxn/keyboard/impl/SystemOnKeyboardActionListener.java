package com.zxn.keyboard.impl;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.zxn.keyboard.action.KeyBoardActionListence;

/**
 * Created by huangjie on 2018/2/3.
 * 类名：
 * 说明：键盘原生接口实现类，可继承重写
 */

public class SystemOnKeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

    private EditText editText;
    private PopupWindow popupWindow;
    private KeyBoardActionListence listence;

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    public void setKeyActionListence(KeyBoardActionListence listence) {
        this.listence = listence;
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (null != editText) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();

            if (primaryCode == Keyboard.KEYCODE_DONE) {// 隐藏键盘
                if (null != popupWindow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                //回调收款按钮.
                listence.onComplete();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
                listence.onClear();
            } else if (primaryCode == 152) {//清空
                editable.clear();
                //listence.onClearAll();
            } else if (primaryCode == 43) {//点击+(加号)
                //保证不可连续输入两个+号
                if (editable != null && editable.length() > 0) {
                    if (editable.toString().substring(editable.length() - 1).equals("+")
                            || editable.toString().substring(editable.length() - 1).equals(".")) {
                        if (start > 0) {
                            editable.insert(start, Character.toString((char) primaryCode));
                            editable.delete(start - 1, start);
                        }
                    } else {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                    listence.onTextChange(editable);
                }
            } else if (primaryCode == 46) {//点击.(小数点)

                if (editable == null
                        || editable.length() == 0
                        || editable.toString().substring(editable.length() - 1).equals("+")) {
                    editable.insert(start, Character.toString((char) 48));
                    start = editText.getSelectionStart();
                    editable.insert(start, Character.toString((char) primaryCode));
                    listence.onTextChange(editable);
                } else {
                    if (!editable.toString().substring(editable.length() - 1).equals(".")) {
                        String[] nums = editable.toString().split("\\+");
                        if (!nums[nums.length - 1].contains(".")) {
                            editable.insert(start, Character.toString((char) primaryCode));
                        }
                    }
                }
            } else if (primaryCode == 48) {//数字0按钮   0
                if (editable != null) {
                    if (editable.length() == 0) {
                        editable.insert(start, Character.toString((char) primaryCode));
                        listence.onTextChange(editable);
                    } else {
                        //输入的第一位是0的情况下,不允许连续输入0.
                        if (!editable.toString().equals("0")) {
                            editable.insert(start, Character.toString((char) primaryCode));
                            listence.onTextChange(editable);
                        }
                    }

                }
            } else if (primaryCode == -721) {//银行卡按钮   -721
                listence.onCustomKeyClick();
            } else {//正常的内容输入.
                editable.insert(start, Character.toString((char) primaryCode));
                listence.onTextChange(editable);
            }
        }
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeUp() {
    }
}
