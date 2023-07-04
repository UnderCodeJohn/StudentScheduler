package holmes.studentscheduler.model;

public class Assessment {
    private int assessmentId;
    private int assessmentCourseId;
    private int assessmentTypeId;
    private String assessmentName;
    private String assessmentStartDate;
    private String assessmentEndDate;

    public Assessment(int assessmentId, int assessmentCourseId, int assessmentTypeId, String assessmentName, String assessmentStartDate, String assessmentEndDate) {
        this.assessmentId = assessmentId;
        this.assessmentCourseId = assessmentCourseId;
        this.assessmentTypeId = assessmentTypeId;
        this.assessmentName = assessmentName;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
    }

    public Assessment() {
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", assessmentCourseId=" + assessmentCourseId +
                ", assessmentTypeId=" + assessmentTypeId +
                ", assessmentName='" + assessmentName + '\'' +
                ", assessmentStartDate='" + assessmentStartDate + '\'' +
                ", assessmentEndDate='" + assessmentEndDate + '\'' +
                '}';
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getAssessmentCourseId() {
        return assessmentCourseId;
    }

    public void setAssessmentCourseId(int assessmentCourseId) {
        this.assessmentCourseId = assessmentCourseId;
    }

    public int getAssessmentTypeId() {
        return assessmentTypeId;
    }

    public void setAssessmentTypeId(int assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }
}
