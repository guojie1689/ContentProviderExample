package com.gj.cpe.iface;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gj.cpe.Config;
import com.gj.cpe.bean.BookBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guojie
 * <p>
 */
public class BookDBOperator implements IDBOperator {

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_PRICE = "KEY_PRICE";

    private static final String[] COLUMN_NAME = {"name", "price"};

    private Map<String, BookBean> bookBeanList = new HashMap<>();

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        insertBookBean(values);

        return uri;
    }

    private boolean insertBookBean(ContentValues values) {
        String name = values.getAsString(KEY_NAME);
        double price = values.getAsDouble(KEY_PRICE);

        Log.d(Config.TAG, "insert ---- name:" + name + " price:" + price);

        BookBean bookBean = new BookBean(name, price);

        return bookBeanList.put(name, bookBean) == null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        BookBean bookBean = new BookBean(selection, 0);

        Log.d(Config.TAG, "delete " + bookBean);

        boolean result = bookBeanList.remove(selection) == null;

        if (result) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        boolean isExist = insertBookBean(values);

        for (BookBean bookBean : bookBeanList.values()) {
            Log.d(Config.TAG, "after update:" + bookBean.toString());
        }

        if (isExist) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        MatrixCursor matrixCursor = new MatrixCursor(COLUMN_NAME);

        for (BookBean bookBean : bookBeanList.values()) {
            Log.d(Config.TAG, bookBean.toString());
            matrixCursor.addRow(new Object[]{bookBean.getBookName(), bookBean.getPrice()});
        }

        return matrixCursor;
    }

}
