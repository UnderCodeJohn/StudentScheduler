package holmes.studentscheduler.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import holmes.studentscheduler.model.Assessment;
import holmes.studentscheduler.model.Course;
import holmes.studentscheduler.model.Instructor;
import holmes.studentscheduler.model.Note;
import holmes.studentscheduler.model.Term;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TERM_TABLE = "TERM_TABLE";
    public static final String COLUMN_TERM_ID = "COLUMN_ID";
    public static final String COLUMN_TERM_NAME = "COLUMN_TERM_NAME";
    public static final String COLUMN_START_DATE = "COLUMN_START_DATE";
    public static final String COLUMN_END_DATE = "COLUMN_END_DATE";
    public static final String COURSE_TABLE = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "COLUMN_COURSE_ID";
    public static final String COLUMN_COURSE_TERM_ID = "COLUMN_COURSE_TERM_ID";
    public static final String COLUMN_COURSE_NAME = "COLUMN_COURSE_NAME";
    public static final String COLUMN_COURSE_START_DATE = "COLUMN_COURSE_START_DATE";
    public static final String COLUMN_COURSE_END_DATE = "COLUMN_COURSE_END_DATE";
    public static final String COLUMN_COURSE_STATUS_ID = "COLUMN_COURSE_STATUS_ID";
    public static final String COLUMN_COURSE_INSTRUCTOR_ID = "COLUMN_COURSE_INSTRUCTOR_ID";
    public static final String INSTRUCTOR_TABLE = "INSTRUCTOR_TABLE";
    public static final String COLUMN_INSTRUCTOR_ID = "COLUMN_INSTRUCTOR_ID";
    public static final String COLUMN_INSTRUCTOR_NAME = "COLUMN_INSTRUCTOR_NAME";
    public static final String COLUMN_INSTRUCTOR_PHONE = "COLUMN_INSTRUCTOR_PHONE";
    public static final String COLUMN_INSTRUCTOR_EMAIL = "COLUMN_INSTRUCTOR_EMAIL";
    public static final String COLUMN_STATUS_ID = "COLUMN_STATUS_ID";
    public static final String COLUMN_STATUS_NAME = "COLUMN_STATUS_NAME";
    public static final String COLUMN_NOTE_ID = "COLUMN_NOTE_ID";
    public static final String COLUMN_NOTE_COURSE_ID = "COLUMN_NOTE_COURSE_ID";
    public static final String COLUMN_NOTES = "COLUMN_NOTES";
    public static final String COLUMN_NOTE_NAME = "COLUMN_NOTE_NAME";
    public static final String STATUS_TABLE = "STATUS_TABLE";
    public static final String NOTE_TABLE = "NOTE_TABLE";
    public static final String ASSESSMENT_TABLE = "ASSESSMENT_TABLE";
    public static final String COLUMN_ASSESSMENT_ID = "COLUMN_ASSESSMENT_ID";
    public static final String ASSESSMENT_TYPE = "ASSESSMENT_TYPE";
    public static final String ASSESSMENT_TYPE_ID = "ASSESSMENT_TYPE_ID";
    public static final String COLUMN_ASSESSMENT_TYPE_ID = "COLUMN_ASSESSMENT_TYPE_ID";
    public static final String COLUMN_ASSESSMENT_NAME = "COLUMN_ASSESSMENT_NAME";
    public static final String COLUMN_ASSESSMENT_END_DATE = "COLUMN_ASSESSMENT_END_DATE";
    public static final String ASSESSMENT_TYPE_TABLE = "ASSESSMENT_TYPE_TABLE";
    public static final String COLUMN_ASSESSMENT_START_DATE = "COLUMN_ASSESSMENT_START_DATE";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "student_scheduler.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String termTableStatement = "CREATE TABLE " + TERM_TABLE +
                "(" + COLUMN_TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + COLUMN_TERM_NAME + " TEXT, " +
                "" + COLUMN_START_DATE + " TEXT, " +
                "" + COLUMN_END_DATE + " TEXT)";

        String courseTableStatement = "CREATE TABLE " + COURSE_TABLE +
                " (" + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + COLUMN_COURSE_TERM_ID + " INTEGER, " +
                "" + COLUMN_COURSE_NAME + " TEXT, " +
                "" + COLUMN_COURSE_START_DATE + " TEXT, " +
                "" + COLUMN_COURSE_END_DATE + " TEXT, " +
                "" + COLUMN_COURSE_STATUS_ID + " INTEGER, " +
                "" + COLUMN_COURSE_INSTRUCTOR_ID + " INTEGER)";

        String instructorTableStatement = "CREATE TABLE " + INSTRUCTOR_TABLE +
                " (" + COLUMN_INSTRUCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + COLUMN_INSTRUCTOR_NAME + " TEXT, " +
                "" + COLUMN_INSTRUCTOR_PHONE + " TEXT, " +
                "" + COLUMN_INSTRUCTOR_EMAIL + " TEXT)";

        String statusTableStatement = "CREATE TABLE " + STATUS_TABLE +
                " (" + COLUMN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + COLUMN_STATUS_NAME + " TEXT)";

        String noteTableStatement = "CREATE TABLE " + NOTE_TABLE + " (" + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "" + COLUMN_NOTE_COURSE_ID + " INTEGER, " + "" + COLUMN_NOTES + " TEXT, " + "" + COLUMN_NOTE_NAME + " TEXT, " + "FOREIGN KEY (" + COLUMN_NOTE_COURSE_ID + ") REFERENCES " + COURSE_TABLE + " (" + COLUMN_COURSE_ID + ") ON DELETE CASCADE)";

        String assessmentTableStatement = "CREATE TABLE " + ASSESSMENT_TABLE +
                " (" + COLUMN_ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + COLUMN_COURSE_ID + " INTEGER, " +
                "" + COLUMN_ASSESSMENT_TYPE_ID + " INTEGER, " +
                "" + COLUMN_ASSESSMENT_NAME + " TEXT, " +
                "" + COLUMN_ASSESSMENT_START_DATE + " TEXT, " +
                "" + COLUMN_ASSESSMENT_END_DATE + " TEXT, " +
                "" + "FOREIGN KEY (" + COLUMN_COURSE_ID + ") REFERENCES  " + COURSE_TABLE + " (" + COLUMN_COURSE_ID + ") ON DELETE CASCADE)";

        String assessmentTypeTableStatement = "CREATE TABLE " + ASSESSMENT_TYPE_TABLE +
                " (" + ASSESSMENT_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + ASSESSMENT_TYPE + " TEXT)";

        String assessmentStatusType = " INSERT INTO ASSESSMENT_TYPE_TABLE (ASSESSMENT_TYPE) VALUES (''), ('Performance'), ('Objective')";
        String courseStatusType = " INSERT INTO STATUS_TABLE (COLUMN_STATUS_NAME) VALUES (''),('In Progress'), ('Completed'), ('Dropped'), ('Plan to Take')";
        String instructorInfo = " INSERT INTO INSTRUCTOR_TABLE (COLUMN_INSTRUCTOR_NAME, COLUMN_INSTRUCTOR_PHONE, COLUMN_INSTRUCTOR_EMAIL) VALUES ('','','')";
        String courseNotes = " INSERT INTO NOTE_TABLE (" + COLUMN_NOTE_COURSE_ID + "," + COLUMN_NOTES + ", " + COLUMN_NOTE_NAME + ") VALUES (-1, '', '')";

        sqLiteDatabase.execSQL(termTableStatement);
        sqLiteDatabase.execSQL(courseTableStatement);
        sqLiteDatabase.execSQL(instructorTableStatement);
        sqLiteDatabase.execSQL(statusTableStatement);
        sqLiteDatabase.execSQL(noteTableStatement);
        sqLiteDatabase.execSQL(assessmentTableStatement);
        sqLiteDatabase.execSQL(assessmentTypeTableStatement);
        sqLiteDatabase.execSQL(assessmentStatusType);
        sqLiteDatabase.execSQL(courseStatusType);
        sqLiteDatabase.execSQL(instructorInfo);
        sqLiteDatabase.execSQL(courseNotes);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addTerm(Term term) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TERM_NAME, term.getTermName());
        cv.put(COLUMN_START_DATE, term.getTermStartDate());
        cv.put(COLUMN_END_DATE, term.getTermEndDate());

        long insert = sqLiteDatabase.insert(TERM_TABLE, null, cv);

        if (insert == -1) {
            return true;
        } else {
            return false;
        }
    }

    public List<Term> getAllTerms() {
        List<Term> returnTermList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TERM_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                int termId = results.getInt(0);
                String termName = results.getString(1);
                String termStartDate = results.getString(2);
                String termEndDate = results.getString(3);

                Term newTerm = new Term(termId, termName, termStartDate, termEndDate);
                returnTermList.add(newTerm);
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnTermList;
    }

    public boolean deleteTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TERM_TABLE + " WHERE " + COLUMN_TERM_ID + " = " + term.getTermId();

        Cursor result = db.rawQuery(queryString, null);

        if (result.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addCourse(Course course) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COURSE_TERM_ID, course.getCourseTermId());
        cv.put(COLUMN_COURSE_NAME, course.getCourseName());
        cv.put(COLUMN_COURSE_START_DATE, course.getCourseStartDate());
        cv.put(COLUMN_COURSE_END_DATE, course.getCourseEndDate());
        cv.put(COLUMN_COURSE_STATUS_ID, course.getCourseStatusId());
        cv.put(COLUMN_COURSE_INSTRUCTOR_ID, course.getCourseInstructorId());

        long insert = sqLiteDatabase.insert(COURSE_TABLE, null, cv);

        if (insert == -1) {
            return true;
        } else {
            return false;
        }
    }

    public Course getCourse(Course course) {
        Course returnCourse = null;

        String queryString = "SELECT * FROM " + COURSE_TABLE + " WHERE " + COLUMN_COURSE_ID + " = " + course.getCourseId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                int courseId = results.getInt(0);
                int courseTermId = results.getInt(1);
                String courseName = results.getString(2);
                String courseStartDate = results.getString(3);
                String courseEndDate = results.getString(4);
                int courseStatus = results.getInt(5);
                int courseInstructor = results.getInt(6);

                Course newCourse = new Course(courseId, courseTermId, courseName, courseStartDate, courseEndDate, courseStatus, courseInstructor);
                returnCourse = newCourse;
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnCourse;
    }

    public List<Course> getAllCourses(Term term) {
        List<Course> returnCourseList = new ArrayList<>();

        String queryString = "SELECT * FROM " + COURSE_TABLE + " WHERE " + COLUMN_COURSE_TERM_ID + " = " + term.getTermId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                int courseId = results.getInt(0);
                int courseTermId = results.getInt(1);
                String courseName = results.getString(2);
                String courseStartDate = results.getString(3);
                String courseEndDate = results.getString(4);
                int courseStatusId = results.getInt(5);
                int courseInstructorId = results.getInt(6);

                Course newCourse = new Course(courseId, courseTermId, courseName, courseStartDate, courseEndDate, courseStatusId, courseInstructorId);
                returnCourseList.add(newCourse);
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnCourseList;
    }

    public boolean updateCourse(Course course) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COURSE_NAME, course.getCourseName());
        cv.put(COLUMN_COURSE_START_DATE, course.getCourseStartDate());
        cv.put(COLUMN_COURSE_END_DATE, course.getCourseEndDate());
        cv.put(COLUMN_COURSE_INSTRUCTOR_ID, course.getCourseInstructorId());
        cv.put(COLUMN_COURSE_STATUS_ID, course.getCourseStatusId());
        db.update(COURSE_TABLE, cv, COLUMN_COURSE_ID + " = " + course.getCourseId(), null);
        return true;

    }

    public boolean deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + COURSE_TABLE + " WHERE " + COLUMN_COURSE_ID + " = " + course.getCourseId();
        String deleteAssessments = "DELETE FROM " + ASSESSMENT_TABLE + " WHERE " + COLUMN_COURSE_ID + " = " + course.getCourseId();
        String deleteNotes = "DELETE FROM " + NOTE_TABLE + " WHERE " + COLUMN_NOTE_COURSE_ID + " = " + course.getCourseId();

        Cursor result = db.rawQuery(queryString, null);
        Cursor resultAssessment = db.rawQuery(deleteAssessments, null);
        Cursor resultNote = db.rawQuery(deleteNotes, null);

        if (result.moveToFirst() || resultAssessment.moveToFirst() || resultNote.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addInstructor(Instructor instructor) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INSTRUCTOR_NAME, instructor.getInstructorName());
        cv.put(COLUMN_INSTRUCTOR_PHONE, instructor.getInstructorPhone());
        cv.put(COLUMN_INSTRUCTOR_EMAIL, instructor.getInstructorEmail());

        long insert = sqLiteDatabase.insert(INSTRUCTOR_TABLE, null, cv);

        if (insert == -1) {
            return true;
        } else {
            return false;
        }
    }

    public List<Instructor> getAllInstructors() {
        List<Instructor> returnInstructorList = new ArrayList<>();

        String queryString = "SELECT * FROM " + INSTRUCTOR_TABLE + " LIMIT " + -1 + " OFFSET " + 1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                int instructorId = results.getInt(0);
                String instructorName = results.getString(1);
                String instructorPhone = results.getString(2);
                String instructorEmail = results.getString(3);

                Instructor newInstructor = new Instructor(instructorId, instructorName, instructorPhone, instructorEmail);
                returnInstructorList.add(newInstructor);
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnInstructorList;
    }

    public Instructor getInstructor(Course course) {
        Instructor returnInstructor = null;

        String queryString = "SELECT * FROM " + INSTRUCTOR_TABLE + " WHERE " + COLUMN_INSTRUCTOR_ID + " = " + course.getCourseInstructorId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                int instructorId = results.getInt(0);
                String instructorName = results.getString(1);
                String instructorPhone = results.getString(2);
                String instructorEmail = results.getString(3);

                Instructor newInstructor = new Instructor(instructorId, instructorName, instructorPhone, instructorEmail);
                returnInstructor = newInstructor;
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnInstructor;
    }

    public boolean updateInstructor(Instructor instructor) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INSTRUCTOR_NAME, instructor.getInstructorName());
        cv.put(COLUMN_INSTRUCTOR_PHONE, instructor.getInstructorPhone());
        cv.put(COLUMN_INSTRUCTOR_EMAIL, instructor.getInstructorEmail());
        db.update(INSTRUCTOR_TABLE, cv, COLUMN_INSTRUCTOR_ID + " = " + instructor.getInstructorId(), null);
        return true;

    }

    public boolean deleteInstructor(Instructor instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + INSTRUCTOR_TABLE + " WHERE " + COLUMN_INSTRUCTOR_ID + " = " + instructor.getInstructorId();
        String updateCourses = "UPDATE " + COURSE_TABLE + " SET " + COLUMN_COURSE_INSTRUCTOR_ID + " = " + 1 + " WHERE " + COLUMN_COURSE_INSTRUCTOR_ID + " = " + instructor.getInstructorId();

        Cursor result = db.rawQuery(queryString, null);
        Cursor resultCourse = db.rawQuery(updateCourses, null);

        if (result.moveToFirst() || resultCourse.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public String getCourseStatus(Course course) {
        String returnStatus = "";

        String queryString = "SELECT * FROM " + STATUS_TABLE + " WHERE " + COLUMN_STATUS_ID + " = " + course.getCourseStatusId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                String courseStatus = results.getString(1);

                returnStatus = courseStatus;
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnStatus;
    }

    public boolean addAssessment(Assessment assessment) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COURSE_ID, assessment.getAssessmentCourseId());
        cv.put(COLUMN_ASSESSMENT_TYPE_ID, assessment.getAssessmentTypeId());
        cv.put(COLUMN_ASSESSMENT_NAME, assessment.getAssessmentName());
        cv.put(COLUMN_ASSESSMENT_START_DATE, assessment.getAssessmentStartDate());
        cv.put(COLUMN_ASSESSMENT_END_DATE, assessment.getAssessmentEndDate());

        long insert = sqLiteDatabase.insert(ASSESSMENT_TABLE, null, cv);

        if (insert == -1) {
            return true;
        } else {
            return false;
        }
    }

    public List<Assessment> getAllAssessments(Course course) {
        List<Assessment> returnAssessmentList = new ArrayList<>();

        String queryString = "SELECT * FROM " + ASSESSMENT_TABLE + " WHERE " + COLUMN_COURSE_ID + " = " + course.getCourseId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                int assessmentId = results.getInt(0);
                int assessmentCourseId = results.getInt(1);
                int assessmentTypeId = results.getInt(2);
                String assessmentName = results.getString(3);
                String assessmentStartDate = results.getString(4);
                String assessmentEndDate = results.getString(5);

                Assessment newAssessment = new Assessment(assessmentId, assessmentCourseId, assessmentTypeId, assessmentName, assessmentStartDate, assessmentEndDate);
                returnAssessmentList.add(newAssessment);
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnAssessmentList;
    }

    public boolean deleteAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + ASSESSMENT_TABLE + " WHERE " + COLUMN_ASSESSMENT_ID + " = " + assessment.getAssessmentId();

        Cursor result = db.rawQuery(queryString, null);

        if (result.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateAssessment(Assessment assessment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ASSESSMENT_TYPE_ID, assessment.getAssessmentTypeId());
        cv.put(COLUMN_ASSESSMENT_NAME, assessment.getAssessmentName());
        cv.put(COLUMN_ASSESSMENT_START_DATE, assessment.getAssessmentStartDate());
        cv.put(COLUMN_ASSESSMENT_END_DATE, assessment.getAssessmentEndDate());
        db.update(ASSESSMENT_TABLE, cv, COLUMN_ASSESSMENT_ID + " = " + assessment.getAssessmentId(), null);
        return true;

    }

    public String getAssessmentType(Assessment assessment) {
        String returnType = "";

        String queryString = "SELECT * FROM " + ASSESSMENT_TYPE_TABLE + " WHERE " + ASSESSMENT_TYPE_ID + " = " + assessment.getAssessmentTypeId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                String assessmentType = results.getString(1);

                returnType = assessmentType;
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnType;
    }

    public List<Note> getAllNotes(Course course) {
        List<Note> returnNoteList = new ArrayList<>();

        String queryString = "SELECT * FROM " + NOTE_TABLE + " WHERE " + COLUMN_NOTE_COURSE_ID + " = " + course.getCourseId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if (results.moveToFirst()) {
            do {
                int noteId = results.getInt(0);
                int noteCourseId = results.getInt(1);
                String notes = results.getString(2);
                String noteName = results.getString(3);

                Note newNote = new Note(noteId, noteCourseId, notes, noteName);
                returnNoteList.add(newNote);
            } while (results.moveToNext());
        } else {
            // DO NOTHING
        }

        results.close();
        db.close();
        return returnNoteList;
    }

    public boolean addNote(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTE_COURSE_ID, note.getNoteCourseId());
        cv.put(COLUMN_NOTES, note.getNotes());
        cv.put(COLUMN_NOTE_NAME, note.getNoteName());

        long insert = sqLiteDatabase.insert(NOTE_TABLE, null, cv);

        if (insert == -1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + NOTE_TABLE + " WHERE " + COLUMN_NOTE_ID + " = " + note.getNoteId();

        Cursor result = db.rawQuery(queryString, null);

        if (result.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTE_NAME, note.getNoteName());
        cv.put(COLUMN_NOTES, note.getNotes());
        db.update(NOTE_TABLE, cv, COLUMN_NOTE_ID + " = " + note.getNoteId(), null);
        return true;

    }

}
