package Server.ServerModel.Registration;

import java.util.ArrayList;

/**
 * Provides data fields and methoods to construct a Student object
 * 
 * @author A. Mohar, T. Pritchard, P. Patel
 * @version 1.0
 * @since April 13, 2020
 */
public class Student {
    /**
     * Student's name
     */
    private String studentName;

    /**
     * Student id
     */
    private int studentId;

    /**
     * list of registrations student has i.e. courses
     */
    private ArrayList<Registration> studentRegList;

    private DBManager db;

    /**
     * Constructs a Student object with specifed name and id Initializes
     * registrations list
     * 
     * @param studentName name of the student
     * @param studentId   id of the student
     */
    public Student(String studentName, int studentId) {
        this.setStudentName(studentName);
        this.setStudentId(studentId);
        studentRegList = new ArrayList<Registration>();
    }

    /**
     * gets Student's name
     * 
     * @return studentName student name
     */
    public String getStudentName() {
        return studentName;
    }

    public boolean hasRegAdded(String courseName, int courseId) {
        for (Registration r : studentRegList) {
            if (r.getTheOffering().getTheCourse().getCourseName().contentEquals(courseName)
                    && r.getTheOffering().getTheCourse().getCourseNum() == courseId) {
                return true;
            }
        }
        return false;
    }

    /**
     * sets Student name
     * 
     * @param studentName name of the student
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * gets Student's Id
     * 
     * @return student id
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * sets Student Id
     * 
     * @param studentId id of the student
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        String st = "Student Name: " + getStudentName() + "#" + "Student Id: " + getStudentId() + "# #";
        return st;
    }

    /**
     * adds specifed Registration object into Student's registration list
     * 
     * @param registration registration object instance
     */
    public void addRegistration(Registration registration) {
        studentRegList.add(registration);
    }

    /**
     * Removes specifed course from Student's registered courses
     * 
     * @param courseName course name
     * @return True if removal was successful, false otherwise
     */
    public boolean removeRegistration(String courseName, int courseId) {
        int count = 0;
        boolean removed = false;
        for (Registration r : studentRegList) {
            if (r.getTheOffering().getTheCourse().getCourseName().contentEquals(courseName)
                    && r.getTheOffering().getTheCourse().getCourseNum() == courseId) {
                studentRegList.remove(count);
                removed = true;
                break;
            }
            count++;
        }
        return removed;
    }

    /**
     * gets list of courses student is in
     * 
     * @return arraylist
     */
    public ArrayList<Registration> getStudentRegList() {
        return studentRegList;
    }

    /**
     * Restricts each Student to be able to take a maximum of 6 courses
     */
    public boolean maxCourse() {
        if (this.studentRegList.size() >= 6)
            return false;
        return true;
    }

    /**
     * Removes the last course in registration list
     */
    public void removeMaxCourse() {
        studentRegList.remove(studentRegList.size() - 1);
    }

    public void setDB(DBManager db) {
        this.db = db;
    }
}
