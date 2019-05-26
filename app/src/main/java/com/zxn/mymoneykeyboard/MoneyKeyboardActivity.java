package com.zxn.mymoneykeyboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MoneyKeyboardActivity extends AppCompatActivity {



    public static void jumpTo(Context context) {
        Intent intent = new Intent(context, MoneyKeyboardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_keyboard);
    }
}
