package com.zxn.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * 键盘功能控制工具类
 * https://blog.csdn.net/qq_29983773/article/details/79501658
 */
public class KeyboardUtils {

    //    private Context mContext;
    @Deprecated
    private EditText ed;
    private CustomKeyboardView mKeyboardView;
    /**
     * 键盘
     */
    private Keyboard mKeyboard;

    private int mType = IKeyboardType.TYPE_MONEY;

    public KeyboardUtils(Activity activity, EditText edit, CustomKeyboardView keyboardView) {
        this(activity, edit, keyboardView, IKeyboardType.TYPE_MONEY);
    }

    public KeyboardUtils(Activity activity, EditText edit, CustomKeyboardView keyboardView, @AKeyboardType int type) {
//        this.mContext = activity;
        this.ed = edit;
        this.mKeyboardView = keyboardView;
        this.mType = type;

        if (mType == IKeyboardType.TYPE_NUMBER) {
            mKeyboard = new Keyboard(activity, R.xml.number_keyboard);
        } else if (mType == IKeyboardType.TYPE_PRICE) {
            mKeyboard = new Keyboard(activity, R.xml.number_keyboard);
        } else {
            mKeyboard = new Keyboard(activity, R.xml.money_keyboard);
        }

        try {
            keyboardView.setContext(activity);
            keyboardView.setKeyboard(mKeyboard);
            keyboardView.setEnabled(true);
            keyboardView.setPreviewEnabled(false);
            keyboardView.setOnKeyboardActionListener(listener);//OnKeyboardActionListener
        } catch (Exception e) {
            Log.e("mKeyboardView", "mKeyboardView init failed!");
        }
    }


    //	private Keyboard k2;
//	private Keyboard k3;
    public boolean isnun = false;// 是否数据键盘
    public boolean isupper = false;// 是否大写

    private KeyboardListener keyboardListener;


    public interface KeyboardListener {
        void onOK();
    }

//    public KeyboardUtils(Context mContext, View parent, EditText edit) {
//        this.mContext = mContext;
//        this.ed = edit;
//        //此处可替换键盘xml
//        mKeyboard = new Keyboard(mContext, R.xml.number_keyboard);
//        mKeyboardView = (CustomKeyboardView) parent.findViewById(R.id.keyboard_view);
//        mKeyboardView.setContext(mContext);
//        mKeyboardView.setKeyboard(mKeyboard);
//        mKeyboardView.setEnabled(true);
//        mKeyboardView.setPreviewEnabled(false);
//        mKeyboardView.setOnKeyboardActionListener(listener);
//    }

//    public KeyboardUtils(Context mContext, EditText edit, CustomKeyboardView mKeyboardView) {
//        this.mContext = mContext;
//        this.ed = edit;
//        //此处可替换键盘xml
//        mKeyboard = new Keyboard(mContext, R.xml.number_keyboard);
//        this.mKeyboardView = mKeyboardView;
//        try {
//            //mKeyboardView = (MyKeyboardView) ((Activity) mContext).findViewById(R.id.keyboard_view);
//            mKeyboardView.setContext(mContext);
//            mKeyboardView.setKeyboard(mKeyboard);
//            mKeyboardView.setEnabled(true);
//            mKeyboardView.setPreviewEnabled(false);
//            mKeyboardView.setOnKeyboardActionListener(listener);
//        } catch (Exception e) {
//            Log.e("mKeyboardView", "mKeyboardView init failed!");
//        }
//    }

//    /**
//     * @param mContext  必须要用Activity实例作为上下文传入
//     * @param edit EditText
//     */
//    public KeyboardUtils(Context mContext, EditText edit) {
//        this.mContext = mContext;
//        this.ed = edit;
//        //此处可替换键盘xml
//        mKeyboard = new Keyboard(mContext, R.xml.number_keyboard);
//        try {
//            mKeyboardView = (CustomKeyboardView) ((Activity) mContext).findViewById(R.id.keyboard_view);
//            mKeyboardView.setContext(mContext);
//            mKeyboardView.setKeyboard(mKeyboard);
//            mKeyboardView.setEnabled(true);
//            mKeyboardView.setPreviewEnabled(false);
//            mKeyboardView.setOnKeyboardActionListener(listener);
//        } catch (Exception e) {
//            Log.e("mKeyboardView", "mKeyboardView init failed!");
//        }
//    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    /**
     * 指定键盘类型.
     *
     * @param keyboardType 键盘类型
     */
    public void setType(@AKeyboardType int keyboardType) {
        mType = keyboardType;
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                // hideKeyboard();
                keyboardListener.onOK();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                mKeyboardView.setKeyboard(mKeyboard);

            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 键盘切换
                if (isnun) {
                    isnun = false;
                    mKeyboardView.setKeyboard(mKeyboard);
                } else {
                    isnun = true;
//					mKeyboardView.setKeyboard(k2);
                }
            } else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    ed.setSelection(start - 1);
                }
            } else if (primaryCode == 57421) { // go right
                if (start < ed.length()) {
                    ed.setSelection(start + 1);
                }
            } else if (primaryCode == 46) {       // 小数点
                String text = ed.getText().toString();
                if (mType == IKeyboardType.TYPE_PRICE) {
                    if (!ed.getText().toString().contains(".") && text.length() <= 7) {
                        editable.insert(start,
                                Character.toString((char) primaryCode));
                    }
                }
            } else {
                String text = ed.getText().toString();
                switch (mType) {
                    case IKeyboardType.TYPE_NUMBER:
                        if (text.length() < 7) {
                            editable.insert(start,
                                    Character.toString((char) primaryCode));
                        }
                        break;

                    case IKeyboardType.TYPE_PRICE:
//                        if ((!text.contains(".") || text.length() - 1
//                                - text.indexOf(".") <= 1)
//                                && text.length() < (text.contains(".") ? 10 : 7)) {
//                            //小数点后最长2位，接受7位整数
//                            editable.insert(start,
//                                    Character.toString((char) primaryCode));
//                        }
//                        break;
                    case IKeyboardType.TYPE_MONEY:
                        if ((!text.contains(".") || text.length() - 1
                                - text.indexOf(".") <= 1)
                                && text.length() < (text.contains(".") ? 10 : 7)) {
                            //小数点后最长2位，接受7位整数
                            editable.insert(start,
                                    Character.toString((char) primaryCode));
                        }
                        break;
                    default:
                        editable.insert(start, Character.toString((char) primaryCode));
                        break;
                }

            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Key> keylist = mKeyboard.getKeys();
        if (isupper) {// 大写切换小写
            isupper = false;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {// 小写切换大写
            isupper = true;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }
}
