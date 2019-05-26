package com.zxn.mymoneykeyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zxn.keyboard.KeyboardUtil;
import com.zxn.keyboard.CustomKeyboardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class KeyBoard3Activity extends AppCompatActivity {

    @BindView(R.id.btn_showKey)
    Button btnShowKey;
    @BindView(R.id.btn_hideKey)
    Button btnHideKey;
    @BindView(R.id.btn_price)
    Button btnPrice;
    @BindView(R.id.btn_number)
    Button btnNumber;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView myKeyboardView;
    private int change_type;
    private KeyboardUtil keyboardUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_key);
        ButterKnife.bind(this);
        initKeyboard();
        showKeyBoard();
    }




    private void initKeyboard() {
        keyboardUtil = new KeyboardUtil(this, etAmount, myKeyboardView);
        keyboardUtil.setType(KeyboardUtil.TYPE_PRICE);
        keyboardUtil.setKeyboardListener(new KeyboardUtil.KeyboardListener() {

            @Override
            public void onOK() {
                String result = etAmount.getText().toString();
                String msg = "";
                if (!TextUtils.isEmpty(result)) {
                    switch (change_type) {
                        case KeyboardUtil.TYPE_NUMBER:
                            msg += "num:" + result;
                            break;
                        case KeyboardUtil.TYPE_PRICE:
                            msg += "price:" + result;
                            break;
                        default:
                            msg += "input:" + result;
                            break;
                    }
                    Toast.makeText(KeyBoard3Activity.this, msg, Toast.LENGTH_SHORT).show();
                }
                hideKeyBoard();
                change_type = -1;
            }
        });
    }

    /**
     * 显示键盘
     */
    protected void showKeyBoard() {
        etAmount.setText("");
        switch (change_type) {
            case KeyboardUtil.TYPE_NUMBER:
                etAmount.setHint("请输入数量");
                break;

            case KeyboardUtil.TYPE_PRICE:
                etAmount.setHint("请输入价格");
                break;

            default:
                break;
        }
        keyboardUtil.setType(change_type);
        keyboardUtil.showKeyboard();
        showKeyboardWithAnimation();
    }

    private void showKeyboardWithAnimation() {
        if (myKeyboardView.getVisibility() == View.GONE) {
//            Animation animation = AnimationUtils
//                    .loadAnimation(this,
//                            R.anim.slide_in_bottom);
//            keyboard_view.showWithAnimation(animation);
        }
    }

    /**
     * 显示键盘
     */
    protected void hideKeyBoard() {
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }


    @OnClick({R.id.btn_showKey, R.id.btn_hideKey, R.id.btn_price, R.id.btn_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_showKey:
                showKeyBoard();
                break;
            case R.id.btn_hideKey:
                hideKeyBoard();
                break;
            case R.id.btn_price:
                change_type = KeyboardUtil.TYPE_PRICE;
                showKeyBoard();
                break;
            case R.id.btn_number:
                change_type = KeyboardUtil.TYPE_NUMBER;
                showKeyBoard();
                break;
        }
    }
}

