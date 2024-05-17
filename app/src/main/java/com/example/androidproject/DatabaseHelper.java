package com.example.androidproject;

import static com.example.androidproject.ImageUtils.bitmapToBase64;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ShoeStore.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";
    private Context mContext;

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_IMAGE + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        insertInitialShoes(db,mContext);
    }

    private void insertInitialShoes(SQLiteDatabase db, Context context) {
        // Inserting 10 Skechers shoes with hypothetical resource IDs and details
        insertShoe(db, "Skechers GoRun Ride 8", 120.00, "Responsive cushioning running shoe", R.drawable.shoe1, context);
        insertShoe(db, "Skechers Max Cushioning", 95.00, "Max cushioned support sneaker", R.drawable.shoe2, context);
        insertShoe(db, "Skechers GoWalk 5", 85.00, "Comfortable walking shoe", R.drawable.shoe3, context);
        insertShoe(db, "Skechers Street Uno", 90.00, "Air-cooled memory foam sneaker", R.drawable.shoe4, context);
        insertShoe(db, "Skechers Arch Fit", 100.00, "Podiatrist-certified arch support", R.drawable.shoe5, context);
        insertShoe(db, "Skechers Delson Antigo", 70.00, "Waterproof casual comfort shoe", R.drawable.shoe6, context);
        insertShoe(db, "Skechers Relaxed Fit", 75.00, "Casual work shoe with slip resistance", R.drawable.shoe7, context);
        insertShoe(db, "Skechers Ultra Flex", 80.00, "Highly flexible comfort shoe", R.drawable.shoe8, context);
        insertShoe(db, "Skechers Elite Flex", 65.00, "Lightweight athletic sneaker", R.drawable.shoe9, context);
        insertShoe(db, "Skechers After Burn", 60.00, "Memory fit cross-trainer shoe", R.drawable.shoe10, context);
    }

    private void insertShoe(SQLiteDatabase db, String name, double price, String description, int imageResourceId, Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResourceId);
        String imageBase64 = bitmapToBase64(bitmap);

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMAGE, imageBase64); // Storing image as a Base64 string
        db.insert(TABLE_PRODUCTS, null, values);
    }
//    private void insertShoe(SQLiteDatabase db, String name, double price, String description, int imageResourceId, Context context) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, name);
//        values.put(COLUMN_PRICE, price);
//        values.put(COLUMN_DESCRIPTION, description);
//        values.put(COLUMN_IMAGE, getBytes(context, imageResourceId));  // Inserting image as byte array
//        db.insert(TABLE_PRODUCTS, null, values);
//
//    }
    private byte[] getBytes(Context context, int resourceId) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
}

