package com.zxn.mymoneykeyboard;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type[] = new String[] {"SystemKeyboard","SystemKeyBoardEditText","仿微信键盘","仿微信支付键盘"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, type);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                startActivity(SystemKeyboardActivity.class);
                break;
            case 1:
                startActivity(SystemKeyboardEidtTextActivity.class);
                break;
            case 2:
                startActivity(NormalKeyBoardActivity.class);
                break;
            case 3:
                startActivity(PaymentKeyBoardActivity.class);
                break;
        }
    }

    private void startActivity(Class c){
        startActivity(new Intent(this,c));
    }
}
