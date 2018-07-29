package com.david.timaandattandace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.david.timaandattandace.model.EmpBean;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    String tbname = "emp", emplname = "emplname", empid = "empoid", empname = "name", pin = "pin", vacation_hours = "vacation_hours", sickdays = "sickdays", holydays = "holyday", shifted = "shifted", insert_date = "insert_date", delete_date = "delete_date", update_date = "update_date", isdelete = "isdelete";
    String tbname1 = "emp_check", id1 = "id", empid1 = "empid", type1 = "type", date1 = "date1", time1 = "time1";
    String tbname2 = "empabsent", id2 = "id", empid2 = "empid", type2 = "type", from_date = "from_date", to_date = "to_date", from_time = "from_time", to_time = "to_time";

    public Database(Context context) {
        super(context, "EMP", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + tbname + " (" + empid + " integer primary key autoincrement," + empname + " text," + emplname + " text ," + pin + " text," + vacation_hours + " integer," + sickdays + " integer,"
                + holydays + " integer," + shifted + " integer," + insert_date + " date," + update_date + " date," + delete_date + " date," + isdelete + " boolean)";
        db.execSQL(sql);
        String sql1 = "create table " + tbname1 + " (" + id1 + " integer primary key autoincrement, " + empid1 + " integer, " + type1 + " integer, " + date1 + " date, " + time1 + " text)";
        db.execSQL(sql1);
        String sql2 = "create table " + tbname2 + " (" + id2 + " integer primary key autoincrement, " + empid2 + " integer, " + type2 + " integer, " + from_date + " date, " + to_date + " date, " + from_time + " text , " + to_time + " text)";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void adddataemp(EmpBean empBean) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(empname, empBean.getName());
        contentValues.put(emplname, empBean.getEmplname());
        contentValues.put(pin, empBean.getPin());
        contentValues.put(shifted, empBean.getShifted());
        contentValues.put(insert_date, empBean.getInsertdate());
        db.insert(tbname, null, contentValues);
        db.close();
    }

    public void adddatacheck(EmpBean empBean) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(empid1, empBean.getEmpid());
        contentValues.put(type1, empBean.getType1());
        contentValues.put(date1, empBean.getDate1());
        contentValues.put(time1, empBean.getTime1());
        db.insert(tbname1, null, contentValues);
        db.close();
    }

    public void adddataab(EmpBean empBean) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(empid2, empBean.getEmpid());
        contentValues.put(type2, empBean.getType1());
        contentValues.put(from_date, empBean.getFrom_date());
        contentValues.put(to_date, empBean.getTo_date());
        contentValues.put(from_time, empBean.getFrom_time());
        contentValues.put(to_time, empBean.getTo_time());
        db.insert(tbname2, null, contentValues);
        db.close();
    }

    public ArrayList<EmpBean> display() {
        ArrayList<EmpBean> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * From " + tbname;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            EmpBean empBean = new EmpBean();
            empBean.setEmplname(cursor.getString(2));
            empBean.setName(cursor.getString(1));
            empBean.setEmpid(cursor.getInt(0));
            arrayList.add(empBean);
        }
        db.close();
        return arrayList;
    }

    public int clockin_out(int eid) {
//        ArrayList<EmpBean> arrayList=new ArrayList<>();
        int n = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + tbname1;
        try {
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Log.i("empId", String.valueOf(cursor.getInt(1)));
                if (cursor.getInt(1) == eid) {
                    n = cursor.getInt(2);
                }
            }
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }

        db.close();
        return n;
    }

    public Cursor excal_clock() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tbname1, null);
        return cursor;
    }

    public Cursor excal_shift() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tbname2, null);
        return cursor;
    }

    public void emp_update(EmpBean empBean) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(empname, empBean.getName());
        contentValues.put(emplname, empBean.getEmplname());
        contentValues.put(pin, empBean.getPin());
        contentValues.put(shifted, empBean.getShifted());
        database.update(tbname, contentValues, empid + "=" + empBean.getEmpid(), null);
        database.close();
    }

    public void emp_delete(int eid) {
        SQLiteDatabase database = getReadableDatabase();
        database.delete(tbname, empid + "=" + eid, null);
        database.close();
    }

}
