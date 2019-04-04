package com.gj.cpe.iface;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gj.cpe.Config;
import com.gj.cpe.bean.UserBean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author guojie
 * <p>
 */
public class UserDBOperator implements IDBOperator {

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_AGE = "KEY_AGE";

    private static final String[] COLUMN_NAME = {"name", "age"};

    private Set<UserBean> bookBeanList = new HashSet<>();

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        insert(values);

        return uri;
    }

    private boolean insert(ContentValues values) {
        String name = values.getAsString(KEY_NAME);
        int age = values.getAsInteger(KEY_AGE);

        Log.d(Config.TAG, "insert --- name:" + name + "  age:" + age);

        UserBean bookBean = new UserBean(name, age);

        return bookBeanList.add(bookBean);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        UserBean bookBean = new UserBean(selection, 0);

        Log.d(Config.TAG, "delete --- bookBean:" + bookBean);

        boolean result = bookBeanList.remove(bookBean);

        if (result) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        boolean isExist = insert(values);

        if (isExist) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Log.d(Config.TAG, "entry query");

        MatrixCursor matrixCursor = new MatrixCursor(COLUMN_NAME);

        for (UserBean bookBean : bookBeanList) {
            matrixCursor.addRow(new Object[]{bookBean.getUserName(), bookBean.getAge()});
        }

        return matrixCursor;
    }

}
