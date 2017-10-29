package snippets.jee.jms.jsp_beans;

import snippets.jee.jms.dto.CourseDTO;

public class CourseJSPBean {

    private CourseDTO course = new CourseDTO();

    public void setId (int id) {
        course.setId(id);
    }

    public String getName() {
        return course.getName();
    }

    public void setName (String name) {
        course.setName(name);
    }

    public int getCredits() {
        return course.getCredits();
    }

    public void setCredits (int credits) {
        course.setCredits(credits);
    }

    public void addCourse() {

    }
}
