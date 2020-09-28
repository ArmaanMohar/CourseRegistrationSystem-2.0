package Server.ServerModel.Registration;

import java.util.ArrayList;

/**
 * Contains logic and datafields to create a CourseCatalogue list which
 * simulates a database
 * 
 * @author A. Mohar, T. Pritchard, P. Patel
 * @version 1.0
 * @since April 13, 2020
 */
public class CourseCatalogue {

    /**
     * the list of course available
     */
    private ArrayList<Course> courseList;

    private DBManager db;

    /**
     * constructs a course list and initializes it to the data in text
     * file(database)
     */
    public CourseCatalogue() {
        courseList = new ArrayList<Course>();
    }

    /**
     * Creates a new CourseOffering for a specifed Course
     * 
     * @param c      course
     * @param secNum section number
     * @param secCap section cap
     */
    public void createCourseOffering(Course c, int secNum, int secCap) {
        if (c != null) {
            CourseOffering theOffering = new CourseOffering(secNum, secCap);
            c.addOffering(theOffering);
        }
    }

    /**
     * searches catalogue for Course based on name and number
     * 
     * @param courseName coursename
     * @param courseNum  course id
     * @return
     */
    public Course searchCat(String courseName, int courseNum) {
        for (Course c : courseList) {
            if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
                return c;
            }
        }

        return null;
    }

    /**
     * Course not found error message
     * 
     * @return String error message
     */
    public String displayCourseNotFoundError() {
        return "Course/s was not found!# #It does not exists.";
    }

    /**
     * gets the course list
     * 
     * @return courselist
     */
    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    @Override
    public String toString() {
        String st = "All courses in the catalogue:# #";
        for (Course c : courseList) {
            // invokes toString method
            st += "#" + c.getCourseName() + " " + c.getCourseNum() + " (" + c.getOfferingList().size() + " Sections)";
        }
        return st;
    }

    /**
     * removes specifed course from Course list
     * 
     * @param coursename course name
     * @param coursenum  course id
     */
    public void removeCourse(String coursename, int coursenum) {
        String courseUpper = coursename.toUpperCase();
        int index = indexOfName(courseUpper, coursenum);

        courseList.remove(index);
    }

    /**
     * gets the index where specifed Course is located within CourseCatalogue list
     * 
     * @param cname course name
     * @param cid   course id
     * @return int value representing index
     */
    private int indexOfName(String cname, int cid) {
        int index = -1;
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseNum() == cid && courseList.get(i).getCourseName().equals(cname)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void setDB(DBManager db) {
        this.db = db;
    }
}
