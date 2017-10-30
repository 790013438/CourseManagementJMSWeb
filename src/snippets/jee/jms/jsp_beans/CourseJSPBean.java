package snippets.jee.jms.jsp_beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import snippets.jee.jms.CourseQueueSender;
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

    public void addCourse(HttpServletRequest httpServletRequest) throws Exception {

        //get Http session
        HttpSession session = httpServletRequest.getSession(true);

        //look for instance of CourseQueueSender in Session
        CourseQueueSender courseQueueSender = (CourseQueueSender)session.getAttribute("CourseQueueSender");

        if (courseQueueSender == null) {
            //Create instance of CourseQueueSender and save in Session
            courseQueueSender = new CourseQueueSender();
            session.setAttribute("CourseQueueSender", courseQueueSender);
        }

        //需要做：perform input validation
        if (courseQueueSender != null) {
            try {
                courseQueueSender.sendAddCourseMessage(course);
            } catch (Exception e) {
                System.out.println("47 :)");
            }
        }
    }
}
