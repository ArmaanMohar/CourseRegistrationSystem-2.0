package Server.ServerModel.Registration;

import java.util.ArrayList;

/**
 * Provides logic and functions to create a course offering or session Each
 * course can be offered in multiple sessions
 * 
 * @author A. Mohar, T. Pritchard, P. Patel
 * @version 1.0
 * @since April 13, 2020
 */
public class CourseOffering {

    /**
     * section number
     */
    private int secNum;

    /**
     * limit of students in this section
     */
    private int secCap;

    /**
     * the course
     */
    private Course theCourse;

    /**
     * student list
     */
    private ArrayList<Student> studentList;

    /**
     * list of registered students, contained in Registration object
     */
    private ArrayList<Registration> offeringRegList;

    /**
     * constructs a CourseOffering Object with specified section number and cap
     * 
     * @param sectionNumber
     * @param sectionCap
     */
    public CourseOffering(int sectionNumber, int sectionCap) {
        setSecNum(sectionNumber);
        setSecCap(sectionCap);
        offeringRegList = new ArrayList<Registration>();
        studentList = new ArrayList<Student>();
    }

    public ArrayList<Registration> getRegisteredStudents(){
        return offeringRegList;
    }

    /**
     * sets section cap
     * 
     * @param sectionCap
     */
    public void setSecCap(int sectionCap) {
        secCap = sectionCap;
    }

    /**
     * sets section number
     * 
     * @param sectionNumber
     */
    public void setSecNum(int sectionNumber) {
        secNum = sectionNumber;
    }

    /**
     * gets section number
     * 
     * @return section number
     */
    public int getSecNum() {
        return secNum;
    }

    /**
     * gets section cap
     * 
     * @return section cap
     */
    public int getSecCap() {
        return secCap;
    }

    /**
     * gets Course object
     * 
     * @return the Course object
     */
    public Course getTheCourse() {
        return theCourse;
    }

    /**
     * sets Course object
     * 
     * @param course
     */
    public void setTheCourse(Course course) {
        theCourse = course;
    }

    @Override
    public String toString() {
        String st = "#";
        st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "#";
        st += "Section Num: " + getSecNum() + ", section cap: " + getSecCap() + "#";

        // We also want to print the names of all students in the section but will do in
        // milestone 3
        return st;
    }

    /**
     * registers a student for this offering
     * 
     * @param registration
     * @param t
     */
    public void addRegistration(Registration registration, Student t) {
        offeringRegList.add(registration);
        studentList.add(t);
    }

    /**
     * number of students in course
     * 
     * @return size of student list
     */
    public int numberOfStudentsOffering() {
        return studentList.size();
    }

    /**
     * removes the last student object registered
     */
    public void removeMaxOffering() {
        offeringRegList.remove(offeringRegList.size() - 1);
        studentList.remove(studentList.size() - 1);
    }

}
