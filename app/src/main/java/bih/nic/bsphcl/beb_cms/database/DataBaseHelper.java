package bih.nic.bsphcl.beb_cms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.entities.ActivityCode;
import bih.nic.bsphcl.beb_cms.entities.ActivityGroup;

/**
 * Created by NIC2 on 1/6/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    // The Android's default system path of your application database.
    private static String DB_PATH = "";
    private static String DB_NAME = "crms.db";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT >= 29) {
            DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        } else if (Build.VERSION.SDK_INT >= 21) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
            Log.d("DataBase", "exist");
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            Log.d("DataBase", "exist");
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        String myPath = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                myPath = DB_PATH;
            } else {
                myPath = DB_PATH + DB_NAME;
            }
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS
                            | SQLiteDatabase.OPEN_READWRITE);

        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public boolean databaseExist() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = null;
        if (Build.VERSION.SDK_INT >= 29) {
            outFileName = DB_PATH;
        } else {
            outFileName = DB_PATH + DB_NAME;
        }

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        // Open the database
        String myPath = null;
        if (Build.VERSION.SDK_INT >= 29) {
            myPath = DB_PATH;
        } else {
            myPath = DB_PATH + DB_NAME;
        }
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String CREATE_NEFT_TABLE = "CREATE TABLE NEFT (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,WALLET_ID TEXT,AMOUNT TEXT,UTR_NO TEXT,TOPUP_TIME TEXT,u_id TEXT);";
        String CREATE_STATEMENT_TABLE = "CREATE TABLE Statement (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT   ,CON_ID TEXT,RCPT_NO TEXT,PAY_AMT TEXT,WALLET_BALANCE TEXT,WALLET_ID TEXT,RRFContactNo TEXT,ConsumerContactNo TEXT,transStatus TEXT,MESSAGE_STRING TEXT,Authenticated TEXT,payDate\tTEXT,BILL_NO TEXT,payMode TEXT,CNAME TEXT,IS_PRINTED TEXT,TRANS_ID TEXT,USER_ID TEXT);";
        String CREATE_BookNo_TABLE = "CREATE TABLE BookNo (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,BookNo TEXT,MessageString TEXT,USER_ID TEXT);";
        String CREATE_MRU_TABLE = "CREATE TABLE MRU (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,CON_ID TEXT,ACT_NO TEXT,OLD_CON_ID TEXT,CNAME TEXT,METER_NO TEXT,BOOK_NO TEXT,MOBILE_NO TEXT,PAYBLE_AMOUNT TEXT,BILL_NO TEXT,TARIFF_ID TEXT,MESSAGE_STRING TEXT,DATE_TIME TEXT,FA_HU_NAME TEXT,BILL_ADDR1 TEXT,USER_ID TEXT);";
        db.execSQL(CREATE_NEFT_TABLE);
        db.execSQL(CREATE_STATEMENT_TABLE);
        db.execSQL(CREATE_BookNo_TABLE);
        db.execSQL(CREATE_MRU_TABLE);*/
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) return;
        ClearAllTable(db);
        onCreate(db);
        if (oldVersion == 1) {
            Log.d("New Version", "Data can be upgraded");
            //String CREATE_UNBILLEDCONSUMER_TABLE = "CREATE TABLE UNBILLEDCONSUMER (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,SUB_DIV_ID TEXT, CON_ID TEXT, ACT_NO TEXT, BOOK_NO TEXT, NAME TEXT, ADDRESS TEXT, LAST_PAY_DATE TEXT, USER_ID TEXT);";
            //db.execSQL(CREATE_UNBILLEDCONSUMER_TABLE);
        }

        Log.d("Sample Data", "onUpgrade	: " + newVersion);
    }

    public void ClearAllTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS ActivityGroup");
        db.execSQL("DROP TABLE IF EXISTS ActivityCode");
    }

    public long saveActivityGroup(ArrayList<ActivityGroup> activityGroups) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for (ActivityGroup activityGroup : activityGroups) {
                ContentValues values = new ContentValues();
                values.put("codeGroup", activityGroup.getCodeGroup().trim());
                values.put("srNo", activityGroup.getSrNo());
                values.put("codeGroupDesc", activityGroup.getCodeGroupDesc());
                values.put("status", activityGroup.getStatus());
                String[] whereArgs = new String[]{activityGroup.getCodeGroup().trim()};
                c = db.update("ActivityGroup", values, "srNo=?", whereArgs);
                if (!(c > 0)) {
                    c = db.insert("ActivityGroup", null, values);
                }
            }
        } catch (Exception e) {
            Log.e("ERROR 1", e.getLocalizedMessage());
            Log.e("ERROR 2", e.getMessage());
            Log.e("ERROR 3", " WRITING DATA in LOCAL DB for ActivityGroup");
            // TODO: handle exception
        } finally {
            db.close();
            this.getWritableDatabase().close();
        }
        return c;
    }

    public ArrayList<ActivityGroup> getActivityGroup() {
        ArrayList<ActivityGroup> activityGroups = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String quary = "select * from ActivityGroup where status='A'";
            Cursor cursor = db.rawQuery(quary, null);
            while (cursor.moveToNext()) {
                ActivityGroup activityGroup = new ActivityGroup();
                activityGroup.setCodeGroup(cursor.getString(cursor.getColumnIndex("codeGroup")));
                activityGroup.setSrNo(cursor.getString(cursor.getColumnIndex("srNo")));
                activityGroup.setCodeGroupDesc(cursor.getString(cursor.getColumnIndex("codeGroupDesc")));
                activityGroup.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                activityGroups.add(activityGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityGroups;
    }

    public long saveActivityCode(ArrayList<ActivityCode> activityCodes) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for (ActivityCode activityCode : activityCodes) {
                ContentValues values = new ContentValues();
                values.put("srNo", activityCode.getSrNo().trim());
                values.put("codeGroup", activityCode.getCodeGroup().trim());
                values.put("code", activityCode.getCode().trim());
                values.put("shortTextCode", activityCode.getShortTextCode());
                values.put("status", activityCode.getStatus().trim());
                String[] whereArgs = new String[]{activityCode.getCodeGroup().trim()};
                c = db.update("ActivityCode", values, "srNo=?", whereArgs);
                if (!(c > 0)) {
                    c = db.insert("ActivityCode", null, values);
                }
            }
        } catch (Exception e) {
            Log.e("ERROR 3", "WRITING DATA in LOCAL DB for ActivityCode");
            Log.e("ERROR 1", e.getLocalizedMessage());
            Log.e("ERROR 2", e.getMessage());
        } finally {
            db.close();
            this.getWritableDatabase().close();
        }
        return c;
    }

    public ArrayList<ActivityCode> getActivityCode(String codegroup) {
        ArrayList<ActivityCode> mruEntities = new ArrayList<>();
        mruEntities.clear();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String quary = "select * from ActivityCode where codeGroup='" + codegroup + "' and status='A'";
            Cursor cursor = db.rawQuery(quary, null);
            while (cursor.moveToNext()) {
                ActivityCode activityCode = new ActivityCode();
                activityCode.setSrNo(cursor.getString(cursor.getColumnIndex("srNo")));
                activityCode.setCodeGroup(cursor.getString(cursor.getColumnIndex("codeGroup")));
                activityCode.setCode(cursor.getString(cursor.getColumnIndex("code")));
                activityCode.setShortTextCode(cursor.getString(cursor.getColumnIndex("shortTextCode")));
                activityCode.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                mruEntities.add(activityCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mruEntities;
    }


}