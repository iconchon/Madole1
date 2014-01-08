package helper;

import helper.User; 
import helper.Course;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
/**
 * ===========================================
 *  BAGIAN INI ATAU FILE INI AKAN DI HAPUS TX
 * ===========================================
 * **/
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	//private static final String DATABASE_NAME = "contactsManager";
	private static final String DATABASE_NAME = "MadoleData";

	// Contacts table name
	//private static final String TABLE_CONTACTS = "contacts";
	private static final String TABLE_USERS = "user";
	private static final String TABLE_COURSE = "course";

	// Contacts Table Columns names
	//private static final String KEY_ID = "id";
	//private static final String KEY_NAME = "name";
	//private static final String KEY_PH_NO = "phone_number";
	private static final String USER_ID = "id";
	private static final String USER_TOKEN = "token";
	private static final String USER_URL = "url";
	
	private static final String COURSE_ID = "id";
	private static final String COURSE_SHORTNAME = "shortname";
	private static final String COURSE_FULLNAME = "fullname";
	
	//private static final String USER_MOD_ID = "userid";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USERS_TABLE = 
				"CREATE TABLE " + TABLE_USERS + "("
				+ USER_ID + " INTEGER PRIMARY KEY," 
				+ USER_TOKEN + " TEXT,"
				+ USER_URL + " TEXT"+ ")";
		db.execSQL(CREATE_USERS_TABLE);
		//=================================
		String CREATE_COURSE_TABLE = 
				"CREATE TABLE " + TABLE_COURSE + "("
				+ COURSE_ID + " INTEGER PRIMARY KEY," 
				+ COURSE_FULLNAME + " TEXT,"
				+ COURSE_SHORTNAME + " TEXT"+ ")";
		db.execSQL(CREATE_COURSE_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);//===
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_TOKEN, user.getToken());
		values.put(USER_URL, user.getUrl());

		// Inserting Row
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		String CREATE_USERS_TABLE = 
				"CREATE TABLE " + TABLE_USERS + "("
				+ USER_ID + " INTEGER PRIMARY KEY," 
				+ USER_TOKEN + " TEXT,"
				+ USER_URL + " TEXT" +")";
		db.execSQL(CREATE_USERS_TABLE);
		
		db.insert(TABLE_USERS, null, values);
		db.close(); // Closing database connection
	}

	User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(
				TABLE_USERS, 
				new String[] { USER_ID, USER_TOKEN, USER_URL }, 
				USER_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		User user = new User(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return user;
	}
	
	// Getting All Contacts
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User loginuser = new User();
				loginuser.setId(Integer.parseInt(cursor.getString(0)));
				loginuser.setToken(cursor.getString(1));
				loginuser.setUrl(cursor.getString(2));
				// Adding contact to list
				userList.add(loginuser);
			} while (cursor.moveToNext());
		}

		// return contact list
		return userList;
	}

	// Updating single contact
	public int updateUser(User loginuser) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_TOKEN, loginuser.getToken());
		values.put(USER_URL, loginuser.getUrl());

		// updating row
		return db.update(TABLE_USERS, values, USER_ID + " = ?",
				new String[] { String.valueOf(loginuser.getId()) });
	}

	// Deleting single contact
	public void deleteContact(User loginuser) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERS, USER_ID + " = ?", new String[] { String.valueOf(loginuser.getId()) });
		db.close();
	}


	// Getting contacts Count
	/**
	 * @return Count of Cursor
	 */
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	
	
	/**
	 * =========================================
	 * New Part and Methods for Course and File
	 * 
	 * =========================================
	 */
	
	void addCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
//		values.put(COURSE_FULLNAME, course.getFullName());
//		values.put(COURSE_SHORTNAME, course.getShortName());

		// Inserting Row
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);

		
		db.insert(TABLE_COURSE, null, values);
		db.close(); // Closing database connection
	}

	Course getCourse(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(
				TABLE_COURSE, 
				new String[] { COURSE_ID, COURSE_FULLNAME, COURSE_SHORTNAME }, 
				COURSE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Course course = new Course(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return course;
	}
	
	// Getting All Contacts
	public List<Course> getAllCourse() {
		List<Course> userList = new ArrayList<Course>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_COURSE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Course myCourse = new Course();
				myCourse.setId(Integer.parseInt(cursor.getString(0)));
//				myCourse.setFullName(cursor.getString(1));
//				myCourse.setShortName(cursor.getString(2));
				// Adding contact to list
				userList.add(myCourse);
			} while (cursor.moveToNext());
		}

		// return contact list
		return userList;
	}

	// Updating single contact
	public int updateCourse(Course myCourse) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
//		values.put(COURSE_FULLNAME, myCourse.getFullName());
//		values.put(COURSE_SHORTNAME, myCourse.getShortName());

		// updating row
		return db.update(TABLE_COURSE, values, COURSE_ID + " = ?",
				new String[] { String.valueOf(myCourse.getId()) });
	}

	// Deleting single contact
	public void deleteCourse(Course myCourse) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COURSE, COURSE_ID + " = ?", new String[] { String.valueOf(myCourse.getId()) });
		db.close();
	}
	public void renewCourse(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
		String CREATE_COURSE_TABLE = 
		"CREATE TABLE " + TABLE_COURSE + "("
		+ COURSE_ID + " INTEGER PRIMARY KEY," 
		+ COURSE_FULLNAME + " TEXT,"
		+ COURSE_SHORTNAME + " TEXT" +")";
		db.execSQL(CREATE_COURSE_TABLE);
		db.close();
	}
	

	// Getting contacts Count
	/**
	 * @return Count of Cursor
	 */
	public int getCourseCount() {
		String countQuery = "SELECT  * FROM " + TABLE_COURSE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}
