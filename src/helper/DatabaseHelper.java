package helper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "MadoleDb";

	// Table Names
	private static final String TABLE_USER = "user";
	private static final String TABLE_COURSE = "course";
	private static final String TABLE_FILE = "file";
	private static final String TABLE_COURSE_FILE = "course_file";


	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";

	// USER Table - column names
	private static final String KEY_TOKEN = "token";
	private static final String KEY_URL = "url";

	// COURSE Table - column names
	private static final String KEY_COURSE_NAME = "course name";

	// FILE Table - column names
	private static final String KEY_FNAME = "filename";
	private static final String KEY_FLOCATION = "location";
	
	// COURSE_FILE Table - column names
	private static final String KEY_COURSE_ID = "course_id";
	private static final String KEY_FILE_ID = "file_id";

	
	// Table Create Statements
	
	/** USER table create statement */
	private static final String CREATE_TABLE_USER = "CREATE TABLE "+ TABLE_USER + "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_TOKEN + " TEXT," 
			+ KEY_URL + " TEXT," 
			+ KEY_CREATED_AT + " DATETIME" + ")";

	/** COURSE table create statement */
	private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE + "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_COURSE_NAME + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	/** FILE table create statement */
	private static final String CREATE_TABLE_FILE = "CREATE TABLE " + TABLE_FILE + "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_FNAME + " TEXT,"
			+ KEY_FLOCATION + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	/** COURSE_FILE table create statement */
	private static final String CREATE_TABLE_COURSE_FILE = "CREATE TABLE " + TABLE_COURSE_FILE + "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_COURSE_ID + " INTEGER,"
			+ KEY_FILE_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_COURSE);
		db.execSQL(CREATE_TABLE_FILE);
		db.execSQL(CREATE_TABLE_COURSE_FILE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILE);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "user" table methods ----------------//

	/** Creating a user */
	public long createUser(User usr) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TOKEN, usr.getToken());
		values.put(KEY_URL, usr.getUrl());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long user_id = db.insert(TABLE_USER, null, values);

		return user_id;
	}

	/** get single user	 */
	public User getUser(long user_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
				+ KEY_ID + " = " + user_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		User td = new User();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setToken(c.getString(c.getColumnIndex(KEY_TOKEN)));
		td.setUrl(c.getString(c.getColumnIndex(KEY_URL)));
		td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
		
		c.close();

		return td;
	}

	/** getting all users */
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				User td = new User();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setToken(c.getString(c.getColumnIndex(KEY_TOKEN)));
				td.setUrl(c.getString(c.getColumnIndex(KEY_URL)));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
				
				// adding to todo list
				users.add(td);
			} while (c.moveToNext());
		}
		
		c.close();
		return users;
	}

	/** getting user count */
	public int getUserCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/** Updating a user	 */
	public int updateUser(User usr) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TOKEN, usr.getToken());
		values.put(KEY_URL, usr.getUrl());
		
		Log.e(LOG, "update user");
		
		// updating row
		return db.update(TABLE_USER, values, KEY_ID + " = ?",
				new  String[] { String.valueOf(usr.getId()) });
	}

	/** Deleting a user	 */
	public void deleteUser(long user_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USER, KEY_ID + " = ?",
				new String[] { String.valueOf(user_id) });
	}
	
	//==========================USER END============================//
	
	// ------------------------ "File" table methods ----------------//

	/* Creating a File */
	public long createFile(File file, long[] course_ids) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_FNAME, file.getFileName());
		values.put(KEY_FLOCATION, file.getLocation());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long file_id = db.insert(TABLE_FILE, null, values);

		// insert tag_ids
		for (long course_id : course_ids) {
			createFileCourse(file_id, course_id);
		}

		return file_id;
	}

	/* get single File */
	public File getFile(long file_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_FILE + " WHERE "
				+ KEY_ID + " = " + file_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		File td = new File();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setFileName(c.getString(c.getColumnIndex(KEY_FNAME)));
		td.setLocation(c.getString(c.getColumnIndex(KEY_FLOCATION)));
		td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

		return td;
	}

	/** getting all Files */
	public List<File> getAllFiles() {
		List<File> files = new ArrayList<File>();
		String selectQuery = "SELECT  * FROM " + TABLE_FILE;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				File td = new File();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setFileName(c.getString(c.getColumnIndex(KEY_FNAME)));
				td.setLocation(c.getString(c.getColumnIndex(KEY_FLOCATION)));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// adding to file list
				files.add(td);
			} while (c.moveToNext());
		}

		return files;
	}

	/** getting all files under single course */
	public List<File> getAllFilesByCourse(String course_name) {
		List<File> files = new ArrayList<File>();

		String selectQuery = "SELECT  * FROM " + TABLE_FILE + " td, "
				+ TABLE_COURSE + " tg, " + TABLE_COURSE_FILE + " tt WHERE tg."
				+ KEY_COURSE_NAME + " = '" + course_name + "'" + " AND tg." + KEY_ID
				+ " = " + "tt." + KEY_COURSE_ID + " AND td." + KEY_ID + " = "
				+ "tt." + KEY_FILE_ID;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				File td = new File();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setFileName(c.getString(c.getColumnIndex(KEY_FNAME)));
				td.setLocation(c.getString(c.getColumnIndex(KEY_FLOCATION)));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// adding to todo list
				files.add(td);
			} while (c.moveToNext());
		}

		return files;
	}

	/* getting file count */
	public int getFileCount() {
		String countQuery = "SELECT  * FROM " + TABLE_FILE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/* Updating a file */
	public int updateFile(File file) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FNAME, file.getFileName());
		values.put(KEY_FLOCATION, file.getLocation());

		// updating row
		return db.update(TABLE_FILE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(file.getId()) });
		

	}

	/* Deleting a file */
	public void deleteFile(long file_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FILE, KEY_ID + " = ?",
				new String[] { String.valueOf(file_id) });
	}

	// ------------------------ "Course" table methods ----------------//

	/* Creating tag */
	public long createCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_COURSE_ID, course.getId());
		values.put(KEY_COURSE_NAME, course.getCourseName());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long course_id = db.insert(TABLE_COURSE, null, values);

		return course_id;
	}

	/** getting all tags */
	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<Course>();
		String selectQuery = "SELECT  * FROM " + TABLE_COURSE;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Course t = new Course();
				t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				t.setCourseName(c.getString(c.getColumnIndex(KEY_COURSE_NAME)));

				// adding to tags list
				courses.add(t);
			} while (c.moveToNext());
		}
		return courses;
	}

	/* Updating a tag */
	public int updateCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_COURSE_NAME, course.getCourseName());

		// updating row
		return db.update(TABLE_COURSE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(course.getId()) });
	}

	/* Deleting a tag */
	public void deleteCourse(Course course, boolean should_delete_all_course_files) {
		SQLiteDatabase db = this.getWritableDatabase();

		// before deleting tag
		// check if file under this course should also be deleted
		if (should_delete_all_course_files) {
			// get all files under this course
			List<File> allCourseFiles = getAllFilesByCourse(course.getCourseName());

			// delete all files
			for (File file : allCourseFiles) {
				// delete files
				deleteFile(file.getId());
			}
		}

		// now delete the tag
		db.delete(TABLE_COURSE, KEY_ID + " = ?",
				new String[] { String.valueOf(course.getId()) });
	}

	// ------------------------ "todo_tags" table methods ----------------//

	/*
	 * Creating todo_tag
	 */
	public long createFileCourse(long file_id, long course_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FILE_ID, file_id);
		values.put(KEY_COURSE_ID, course_id);
		values.put(KEY_CREATED_AT, getDateTime());

		long id = db.insert(TABLE_COURSE_FILE, null, values);

		return id;
	}

	/*
	 * Updating a File Course
	 */
	public int updateFileCourse(long id, long course_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_COURSE_ID, course_id);

		// updating row
		return db.update(TABLE_FILE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	/*
	 * Deleting a todo tag
	 */
	public void deleteFileCourse(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FILE, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	
	// ------------------------ END table methods ----------------//
	
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
		Log.d("act","close db");
	}

	/** get datetime */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
