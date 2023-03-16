package com.vangcc19135.restaurantManagementSystem.UserExperience.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Bill;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Table;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Drink;

import java.util.ArrayList;

public
class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DrinkManager";
    public static final int DATABASE_VERSION = 1;


    //đồ uống
    private static final String TABLE_DRINK = "DRINK";
    private static final String KEY_ID = "Id";
    private static final String KEY_DRINK_NAME = "DrinkName";
    private static final String KEY_DRINK_AMOUNT = "DrinkAmount";
    private static final String KEY_DRINK_PRICE = "DrinkPrice";

    //bàn
    private static final String TABLE_BOOK = "BOOK";
    private static final String KEY_DRINK_LIST = "DrinkList";

    //thanh toán
    private static final String TABLE_BILL = "BILL";
    private static final String KEY_TOTAL_MONEY = "TotalMoney";
    private static final String KEY_DATE_BILL = "DateBill";

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStudent = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s DOUBLE)",
                TABLE_DRINK, KEY_ID, KEY_DRINK_NAME, KEY_DRINK_AMOUNT,KEY_DRINK_PRICE);
        db.execSQL(createTableStudent);

        String createTableDepartment = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)",
                TABLE_BOOK, KEY_ID, KEY_DRINK_LIST);
        db.execSQL(createTableDepartment);

        String createTableCategoryRoom = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s DOUBLE,%s TEXT)",
                TABLE_BILL, KEY_ID, KEY_DRINK_LIST, KEY_TOTAL_MONEY, KEY_DATE_BILL);
        db.execSQL(createTableCategoryRoom);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableStudent = String.format("DROP TABLE IF EXISTS %s", TABLE_DRINK);
        db.execSQL(dropTableStudent);

        String dropTableDepartment = String.format("DROP TABLE IF EXISTS %s", TABLE_BOOK);
        db.execSQL(dropTableDepartment);

        String dropTableCategoryRoom = String.format("DROP TABLE IF EXISTS %s", TABLE_BILL);
        db.execSQL(dropTableCategoryRoom);

        onCreate(db);
    }

    //thanh toán
    public ArrayList<Bill> getAllBillByForDate(String startDate, String endDate) {
        ArrayList<Bill> list = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_BILL+" WHERE "+KEY_DATE_BILL+" >= '"+startDate+"'"+" AND "+KEY_DATE_BILL+" <='"+endDate+"'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Bill item = new Bill(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
            list.add(item);
            cursor.moveToNext();
        }
        return list;
    }
    public ArrayList<Bill> getAllBill() {
        ArrayList<Bill> list = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_BILL);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Bill item = new Bill(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
            list.add(item);
            cursor.moveToNext();
        }
        return list;
    }

    public Bill getBillLast() {
        String query = String.format("SELECT * FROM %s ORDER BY %s  DESC LIMIT 1", TABLE_BILL,KEY_ID);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Bill bill = new Bill(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));

        return bill;
    }
    public void addBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DRINK_LIST, bill.getListDrink());
        values.put(KEY_TOTAL_MONEY, bill.getTotalMoney());
        values.put(KEY_DATE_BILL, bill.getDateBill());

        db.insert(TABLE_BILL, null, values);
        db.close();
    }

    //bàn
    public void addBook(Table table) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DRINK_LIST, table.getListDrink());

        db.insert(TABLE_BOOK, null, values);
        db.close();
    }
    public void updateBookById(int id, String listDrink) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+TABLE_BOOK+" SET "+KEY_DRINK_LIST+" = \'"+ listDrink+"\' WHERE "+KEY_ID+" = "+ id;

        db.execSQL(strSQL);
    }


    public ArrayList<Table> getAllBook() {
        ArrayList<Table> roomList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_BOOK);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Table room = new Table(cursor.getInt(0), cursor.getString(1));
            roomList.add(room);
            cursor.moveToNext();
        }
        return roomList;
    }

    //Drink
    public void addDrink(Drink drink) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DRINK_NAME, drink.getDrinkName());
        values.put(KEY_DRINK_AMOUNT, drink.getAmount());
        values.put(KEY_DRINK_PRICE, drink.getPrice());


        db.insert(TABLE_DRINK, null, values);
        db.close();
    }

    public ArrayList<Drink> getAllDrink() {
        ArrayList<Drink> departmentList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_DRINK);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Drink department = new Drink(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getDouble(3));
            departmentList.add(department);
            cursor.moveToNext();
        }
        return departmentList;
    }

    public void updateDrinkById(int id, String drinkName,double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+TABLE_DRINK+" SET "+KEY_DRINK_NAME+" = \'"+ drinkName+"\' , "+KEY_DRINK_PRICE+" = "+price+" WHERE "+KEY_ID+" = "+ id;

        db.execSQL(strSQL);
    }

    public void deleteDrinkById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "DELETE FROM " +TABLE_DRINK+" WHERE "+KEY_ID+" = "+ id;

        db.execSQL(strSQL);
    }

}
