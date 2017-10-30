package snippets.jee.jms.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import snippets.jee.jms.CourseQueueReceiver;

@WebServlet(urlPatterns="/JMSReceiverInitServlet", loadOnStartup=1)
public class JMSReceiverInitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CourseQueueReceiver courseQueueReceiver = null;

    public JMSReceiverInitServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            courseQueueReceiver = new CourseQueueReceiver("Receiver1");
        } catch (Exception e) {
            log("Error creating CourseQueueReceiver", e);
        }
    }

    @Override
    public void destroy() {
        if (courseQueueReceiver != null) {
            courseQueueReceiver.stop();
            super.destroy();
        }
    }

}
