package com.gj.cpe;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gj.cpe.iface.BookDBOperator;
import com.gj.cpe.iface.IDBOperator;
import com.gj.cpe.iface.UserDBOperator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guojie
 * <p>
 */
public class DataBaseProvider extends ContentProvider {

    private UriMatcher matcher;

    private final static int DB_USER = 1;
    private final static int DB_BOOK = 2;

    private static final String AUTHOR_NAME = "com.gj.cpe.databaseprovider";

    private static Map<Integer, IDBOperator> operatorMap = new HashMap<>();

    static {
        operatorMap.put(DB_USER, new UserDBOperator());
        operatorMap.put(DB_BOOK, new BookDBOperator());
    }

    /**
     * 代表ContentProvider的创建，可以用来进行一些初始化操作
     *
     * @return
     */
    @Override

    public boolean onCreate() {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHOR_NAME, "userdb", DB_USER);
        matcher.addURI(AUTHOR_NAME, "bookdb", DB_BOOK);

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        IDBOperator operator = getOperator(uri);

        if (operator != null) {
            return operator.query(uri, projection, selection, selectionArgs, sortOrder);
        }

        return null;
    }

    private IDBOperator getOperator(Uri uri) {

        int code = matcher.match(uri);

        return operatorMap.get(code);
    }


    /**
     * 用来返回一个Uri请求所对应的MIME类型
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "";
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        IDBOperator operator = getOperator(uri);

        if (operator != null) {
            Uri result = operator.insert(uri, values);

            this.getContext().getContentResolver().notifyChange(uri, null);

            return result;
        }

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        IDBOperator operator = getOperator(uri);

        if (operator != null) {
            int result = operator.delete(uri, selection, selectionArgs);

            this.getContext().getContentResolver().notifyChange(uri, null);

            return result;
        }

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        IDBOperator operator = getOperator(uri);

        if (operator != null) {
            int result = operator.update(uri, values, selection, selectionArgs);

            this.getContext().getContentResolver().notifyChange(uri, null);

            return result;
        }

        return 0;
    }
}
