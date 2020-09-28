package Server.ServerModel.Registration;

import java.util.ArrayList;

/**
 * Provides data fields and methods to create a Java data-type, representing a
 * University Course.
 * 
 * @author A. Mohar, T. Pritchard, P. Patel
 * @version 1.0
 * @since April 13, 2020
 */
public class Course {
    /**
     * name of course
     */
    private String courseName;

    /**
     * course id or number associated with it (e.g. ENGG 200)
     */
    private int courseNum;

    /**
     * the prerequisites required to take before this course
     */
    private ArrayList<Course> preReq;

    /**
     * course is offered in different sessions
     */
    private ArrayList<CourseOffering> offeringList;

    /**
     * constructs a course object and assigns it preReqs and sessions
     * 
     * @param courseName
     * @param courseNum
     */
    public Course(String courseName, int courseNum) {
        this.setCourseName(courseName);
        this.setCourseNum(courseNum);
        preReq = new ArrayList<Course>();
        offeringList = new ArrayList<CourseOffering>();
    }

    /**
     * creates an offering for the course
     * 
     * @param offering
     */
    public void addOffering(CourseOffering offering) {
        if (offering != null && offering.getTheCourse() == null) {
            offering.setTheCourse(this);
            if (!offering.getTheCourse().getCourseName().equals(courseName)) {
                return;
            }
            offeringList.add(offering);
        }
    }

    /**
     * getter function
     * 
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * setter function, sets course name
     * 
     * @param courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * getter function
     * 
     * @return course number
     */
    public int getCourseNum() {
        return courseNum;
    }

    /**
     * sets course number
     * 
     * @param courseNum
     */
    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public ArrayList<CourseOffering> getOfferingList() {
        return offeringList;
    }

    /**
     * checks the number of students registered in course
     * 
     * @return number of students
     */
    public int checkStudentNumber() {
        int student = 0;
        for (int i = 0; i < offeringList.size(); i++) {
            student += offeringList.get(i).numberOfStudentsOffering();
        }
        return student;
    }

    /**
     * checks if course can run, as a course requires 9 students to run
     */
    public String conditionSNumThisCourse() {
        int studentNum = checkStudentNumber();
        if (studentNum < 8) {
            return("Total number of student in " + courseName + "   " + courseNum
                    + " is " + checkStudentNumber() + ", this course cannot be run ");
        }
        return ("Total number of student in " + courseName + "   " + courseNum
        + " is " + checkStudentNumber() + ", this course can be run ");
    }

    @Override
    public String toString() {
        return (getCourseName() + " " + getCourseNum());
    }

    /**
     * gets course offering at specified index
     * 
     * @param i the index of the offeringList
     * @return offering at specified index
     */
    public CourseOffering getCourseOfferingAt(int i) {
        if (i < 0 || i >= offeringList.size()) {
            return null;
        } else {
            return offeringList.get(i);
        }
    }
    
    /**
     * returns the size of the offeringList
     * @return the size of the offeringList
     */
    public int offeringListSize() {
    	return offeringList.size();
    }
    
    /**
     * returns the preReq list size
     * @return the size of the preReq list
     */
    public int preReqSize() {
    	return preReq.size();
    }

    /**
     * adds a prerequisite to the course
     * 
     * @param preReq
     */
    public void addPreReq(Course preReq) {
        this.preReq.add(preReq);
    }
}
