package com.zxn.mymoneykeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.zxn.keyboard.CustomKeyboardView;
import com.zxn.keyboard.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneyKeyboardActivity extends AppCompatActivity {


    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.ckv_keyboard)
    CustomKeyboardView ckvKeyboard;
    private int change_type;
    private KeyboardUtils keyboardUtil;

    public static void jumpTo(Context context) {
        Intent intent = new Intent(context, MoneyKeyboardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_keyboard);
        ButterKnife.bind(this);


    }


}
