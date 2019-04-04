package com.gj.cpe.iface;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author guojie
 * <p>
 */
public interface IDBOperator {

    Uri insert(@NonNull Uri uri, @Nullable ContentValues values);

    int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs);

    int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs);

    Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder);
}
