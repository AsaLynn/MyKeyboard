package com.zxn.mymoneykeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.zxn.keyboard.OnInputListener;
import com.zxn.keyboard.widget.PopEnterPassword;

public class PaymentKeyBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_key_board);
    }

    public void showPayKeyBoard(View view) {
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        popEnterPassword.setOnInputListener(new OnInputListener() {
            @Override
            public void inputFinish(String password) {
                Toast.makeText(PaymentKeyBoardActivity.this, "password :" + password, Toast.LENGTH_SHORT).show();
            }
        });
        popEnterPassword.show();
    }
}
