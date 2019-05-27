package com.zxn.keyboard.impl;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.zxn.keyboard.action.KeyBoardActionListener;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 说明：键盘原生接口实现类，可继承重写
 * Created by zxn on 2019/5/27.
 */
public class SystemOnKeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

    private EditText editText;
    private PopupWindow popupWindow;
    private KeyBoardActionListener mKeyBoardActionListener;

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    public void setKeyActionListence(KeyBoardActionListener listener) {
        this.mKeyBoardActionListener = listener;
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
                mKeyBoardActionListener.onComplete();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                        mKeyBoardActionListener.onClear();
                        polynomialArithmetic(editable.toString());
                    }
                }
            } else if (primaryCode == 152) {//清空
                editable.clear();
                //mKeyBoardActionListener.onClearAll();
            } else if (primaryCode == -721) {//银行卡按钮   -721
                mKeyBoardActionListener.onCustomKeyClick();
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
                    mKeyBoardActionListener.onTextChange(editable);
                }
            } else if (primaryCode == 46) {//点击.(小数点)
                if (editable == null
                        || editable.length() == 0
                        || editable.toString().substring(editable.length() - 1).equals("+")) {
                    editable.insert(start, Character.toString((char) 48));
                    start = editText.getSelectionStart();
                    editable.insert(start, Character.toString((char) primaryCode));
                    mKeyBoardActionListener.onTextChange(editable);
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
                        mKeyBoardActionListener.onTextChange(editable);
                    } else {
                        //输入的第一位是0的情况下,不允许连续输入0.
                        if (!editable.toString().equals("0")) {
                            editable.insert(start, Character.toString((char) primaryCode));
                            mKeyBoardActionListener.onTextChange(editable);
                            //运算:
                            polynomialArithmetic(editable.toString());
                        }
                    }

                }
            } else {//正常的内容输入.
                editable.insert(start, Character.toString((char) primaryCode));
                mKeyBoardActionListener.onTextChange(editable);
                polynomialArithmetic(editable.toString());
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

    /**
     * 进行多项式计算.
     *
     * @param quantic 多项式内容.
     * @return 计算结果
     */
    private String polynomialArithmetic(String quantic) {
        String result = "";
        if (quantic.length() == 0) {
            return result;
        }
        Queue<String> nums = new ArrayDeque<>();
        Queue<String> ops = new ArrayDeque<>();
        //将元素分开
        String[] items = quantic.split("\\+");
        for (int i = 0; i < items.length; i++) {
            nums.add(items[i]);
            if (i != items.length - 1) {
                ops.add("+");
            }
        }
        nums.add(items[items.length - 1]);
        result = nums.poll();
        while (!ops.isEmpty()) {
            String num2 = nums.poll();
            String op = ops.poll();
            result = arithmetic(result, num2, op);
            if (num2.equals("0")) {// && op.equals("/")
                return "0";
            }
        }
        Log.i("KeyboardActionListener", "polynomialArithmetic: result-->" + result);
        if (null != mKeyBoardActionListener) {
            mKeyBoardActionListener.onArithmetic(result);
        }
        return result;
    }

    /**
     * 运算
     *
     * @param num1 参与运算的第1位
     * @param num2 参与运算的第2位
     * @param op   参与运算的符号.(加法)
     * @return 运算的结果
     */
    private String arithmetic(String num1, String num2, String op) {
        double result = 0;
        double d1 = Double.parseDouble(num1);
        double d2 = Double.parseDouble(num2);
//        对于double的乘法运算的精度问题用bigdecimal解决
        BigDecimal dd1 = new BigDecimal(num1);
        BigDecimal dd2 = new BigDecimal(num2);
        switch (op) {
            case "+":
                result = dd1.add(dd2).doubleValue();
                break;
            case "-":
                result = dd1.min(dd2).doubleValue();
                break;
            case "*":
                result = dd1.multiply(dd2).doubleValue();
                break;
//            case "/":
//                if (d2 == 0) {
//                    result = 0;
//                    Toast.makeText(this, "存在被0除的错误，请检查计算式！", Toast.LENGTH_LONG).show();
//                } else {
//                    result = dd1.divide(dd2).doubleValue();
//                }
//                break;
            default:
                result = d1;
                break;
        }
        return result + "";
    }
}
