package com.zxn.keyboard;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 控制键盘可以使用的类型.
 * Created by zxn on 2019/5/26.
 */
@IntDef({IKeyboardType.TYPE_MONEY, IKeyboardType.TYPE_NUMBER, IKeyboardType.TYPE_PRICE})
@Retention(RetentionPolicy.SOURCE)
public @interface AKeyboardType {

}
