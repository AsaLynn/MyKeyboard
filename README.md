# MyKeyboard
- Android自定义键盘的使用

- 高仿微信数字键盘

- 高仿微信支付键盘（密码键盘）

- 密码键盘

效果图：

![image](https://github.com/zhang721688/MyKeyboard/blob/master/image/img.gif)

![image](https://github.com/zhang721688/MyKeyboard/blob/master/image/image01.gif)

![image](https://github.com/zhang721688/MyKeyboard/blob/master/image/image02.gif)

![image](https://github.com/zhang721688/MyKeyboard/blob/master/image/image03.gif)

# 依赖
```
implementation 'com.zxn.keyboard:custom-keyboard:1.0.3'
```
# 使用
```
PopEnterPassword popEnterPassword = new PopEnterPassword(this);
popEnterPassword.setOnInputListener(new OnInputListener() {
    @Override
    public void inputFinish(String password) {
        Toast.makeText(PaymentKeyBoardActivity.this, "password :" + password, Toast.LENGTH_SHORT).show();
    }
});
popEnterPassword.show();
```
# 更新
- custom-keyboard:1.0.3
增加密码键盘