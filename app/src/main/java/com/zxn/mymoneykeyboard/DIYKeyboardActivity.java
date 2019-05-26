package com.zxn.mymoneykeyboard;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.zxn.keyboard.KeyBoardUtil;

public class DIYKeyboardActivity extends AppCompatActivity {

    private EditText mInput;
    private KeyboardView mKeyboard;


    public static void jumpTo(Context context) {
        Intent intent = new Intent(context, DIYKeyboardActivity.class);

        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_keyboard);
        initView();

    }

    private void initView() {
        mKeyboard = findViewById(R.id.ky_keyboard);
        mInput = findViewById(R.id.et_input);
        new KeyBoardUtil(mKeyboard, mInput).showKeyboard();
    }

    private void initEvent() {
        /*mInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mInput.hasFocus()) {
                    new KeyBoardUtil(mKeyboard, mInput).showKeyboard();
                }
                return false;
            }
        });*/
    }
}
