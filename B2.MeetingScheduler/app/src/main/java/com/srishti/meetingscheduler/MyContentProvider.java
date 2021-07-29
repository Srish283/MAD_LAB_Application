package com.srishti.meetingscheduler;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import java.util.HashMap;


    public class MyContentProvider extends ContentProvider {
        static final String PROVIDER_NAME = "com.srishti.meetingscheduler.MyContentProvider";
        static final String URL = "content://" + PROVIDER_NAME + "/meetings";
        static final Uri CONTENT_URI = Uri.parse(URL);

        static final String ID = "id";
        static final String DATE = "date";
        static final String TIME = "time";
        static final String AGENDA = "agenda";

        private static HashMap<String, String> MEETINGS_PROJECTION_MAP;

        static final int MEETINGS = 1;
        static final int MEETING_ID = 2;

        static final UriMatcher uriMatcher;
        static{
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(PROVIDER_NAME, "meetings", MEETINGS);
            uriMatcher.addURI(PROVIDER_NAME, "meetings/#", MEETING_ID);
        }

        private SQLiteDatabase db;
        static final String DATABASE_NAME = "Meeting";
        static final String TABLE_NAME = "meetings";
        static final int DATABASE_VERSION = 1;


        private static class DatabaseHelper extends SQLiteOpenHelper {
            DatabaseHelper(Context context){
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                //db.execSQL(CREATE_DB_TABLE);
                db.execSQL("CREATE TABLE "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,agenda TEXT NOT NULL,date TEXT NOT NULL,time TEXT NOT NULL )");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
            }
        }
        public MyContentProvider() {
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            int count = 0;
            switch (uriMatcher.match(uri)){
                case MEETINGS:
                    count = db.delete(TABLE_NAME, selection, selectionArgs);
                    break;

                case MEETING_ID:
                    String id = uri.getPathSegments().get(1);
                    count = db.delete( TABLE_NAME, ID +  " = " + id +
                            (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }

            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }

        @Override
        public String getType(Uri uri) {
            switch (uriMatcher.match(uri)){
                /**
                 * Get all student records
                 */
                case MEETINGS:
                    return "vnd.android.cursor.dir/vnd.srishti.MeetContentProvider.meetings";
                /**
                 * Get a particular student
                 */
                case MEETING_ID:
                    return "vnd.android.cursor.item/vnd.srishti.MeetContentProvider.meetings";
                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            /**
             * Add a new student record
             */
            long rowID = db.insert(	TABLE_NAME, "", values);

            /**
             * If record is added successfully
             */
            if (rowID > 0) {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }

            throw new SQLException("Failed to add a record into " + uri);
        }

        @Override
        public boolean onCreate() {
            Context context = getContext();
            DatabaseHelper dbHelper = new DatabaseHelper(context);


            db = dbHelper.getWritableDatabase();
            return (db == null)? false:true;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection,
                            String[] selectionArgs, String sortOrder) {

            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(TABLE_NAME);

            switch (uriMatcher.match(uri)) {
                case MEETINGS:
                    qb.setProjectionMap(MEETINGS_PROJECTION_MAP);
                    break;

                case MEETING_ID:
                    qb.appendWhere( ID + "=" + uri.getPathSegments().get(1));
                    break;

                default:
            }

            if (sortOrder == null || sortOrder == ""){
                /**
                 * By default sort on student names
                 */
                sortOrder = DATE;
            }

            Cursor c = qb.query(db,	projection,	selection,
                    selectionArgs,null, null, sortOrder);
            /**
             * register to watch a content URI for changes
             */
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection,
                          String[] selectionArgs) {

            int count = 0;
            switch (uriMatcher.match(uri)) {
                case MEETINGS:
                    count = db.update(TABLE_NAME, values, selection, selectionArgs);
                    break;

                case MEETING_ID:
                    count = db.update(TABLE_NAME, values, ID + " = " + uri.getPathSegments().get(1) +
                            (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }
