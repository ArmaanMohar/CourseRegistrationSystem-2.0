package Server.ServerModel.Registration;

/**
 * Provides data fields and methods to construct a Registration object and
 * register a Student
 * 
 * @author A. Mohar, T. Pritchard, P. Patel
 * @version 1.0
 * @since April 12, 2020
 */
public class Registration {

    /**
     * the student that wants to register
     */
    private Student theStudent;

    /**
     * the offering in which student wishes to be in
     */
    private CourseOffering theOffering;

    /**
     * registers specified Student for specified CourseOffering
     * 
     * @param st student
     * @param of course offering
     * @return String containing registration infomation
     */
    public String completeRegistration(Student st, CourseOffering of) {
        String ret;
        this.theStudent = st;
        this.theOffering = of;

        if (!(theStudent.maxCourse())) {
            theStudent.removeMaxCourse();
            theOffering.removeMaxOffering();
            ret = "#You cannot register for " + of.getTheCourse().getCourseName() + " "
                    + of.getTheCourse().getCourseNum() + " in Section " + of.getSecNum()
                    + ".# #You are only allowed to register for a maximum of 6 courses.";

        } else if (theOffering.numberOfStudentsOffering() >= theOffering.getSecCap()) {
            ret = "Sorry! There is currently no available place in " + of.getTheCourse().getCourseName() + " "
                    + of.getTheCourse().getCourseNum() + " in Section " + of.getSecNum();
        } else {
            ret = theStudent.getStudentName() + ", you have successfully registered for "
                    + of.getTheCourse().getCourseName() + " " + of.getTheCourse().getCourseNum() + " in Section "
                    + of.getSecNum();
        }
        addRegistration();

        return ret;
    }

    /**
     * links this Registration object with specified student and CourseOffering
     */
    private void addRegistration() {
        theStudent.addRegistration(this);
        theOffering.addRegistration(this, theStudent);
    }

    /**
     * gets the Student
     * 
     * @return the Student
     */
    public Student getTheStudent() {
        return theStudent;
    }

    /**
     * sets the specified student
     */
    public void setTheStudent(Student theStudent) {
        this.theStudent = theStudent;
    }

    /**
     * gets the CourseOffering
     * 
     * @return the course offering
     */
    public CourseOffering getTheOffering() {
        return theOffering;
    }

    /**
     * sets the CourseOffering
     * 
     * @param theOffering course offering
     */
    public void setTheOffering(CourseOffering theOffering) {
        this.theOffering = theOffering;
    }

    @Override
    public String toString() {
        String st = "#";
        st += "Student Name: " + getTheStudent() + ".#";
        st += "The Offering: " + getTheOffering() + ".#";
        st += ".#------------------#";
        return st;
    }
}
