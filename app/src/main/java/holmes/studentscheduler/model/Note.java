package holmes.studentscheduler.model;

public class Note {

    private int noteId;
    private int noteCourseId;
    private String notes;
    private String noteName;

    public Note(int noteId, int noteCourseId, String notes, String noteName) {
        this.noteId = noteId;
        this.noteCourseId = noteCourseId;
        this.notes = notes;
        this.noteName = noteName;
    }

    public Note() {
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteCourseId=" + noteCourseId +
                ", notes='" + notes + '\'' +
                ", noteName='" + noteName + '\'' +
                '}';
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getNoteCourseId() {
        return noteCourseId;
    }

    public void setNoteCourseId(int noteCourseId) {
        this.noteCourseId = noteCourseId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
}
