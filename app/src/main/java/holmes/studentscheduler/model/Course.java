package holmes.studentscheduler.model;

public class Course {
    private int courseId;
    private int courseTermId;
    private String courseName;
    private String courseStartDate;
    private String courseEndDate;
    private int courseStatusId;
    private int courseInstructorId;

    private Term courseTerm;

    public Course(int courseId, int courseTermId, String courseName, String courseStartDate, String courseEndDate, int courseStatusId, int courseInstructorId) {
        this.courseId = courseId;
        this.courseTermId = courseTermId;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatusId = courseStatusId;
        this.courseInstructorId = courseInstructorId;
        //this.courseTerm = courseTerm;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseTermId=" + courseTermId +
                ", courseName='" + courseName + '\'' +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", courseEndDate='" + courseEndDate + '\'' +
                ", courseStatusId=" + courseStatusId +
                ", courseInstructorId=" + courseInstructorId +
                ", courseTerm=" + courseTerm +
                '}';
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public int getCourseStatusId() {
        return courseStatusId;
    }

    public void setCourseStatusId(int courseStatusId) {
        this.courseStatusId = courseStatusId;
    }

    public int getCourseInstructorId() {
        return courseInstructorId;
    }

    public void setCourseInstructorId(int courseInstructorId) {
        this.courseInstructorId = courseInstructorId;
    }

    public Term getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(Term courseTerm) {
        this.courseTerm = courseTerm;
    }
}
