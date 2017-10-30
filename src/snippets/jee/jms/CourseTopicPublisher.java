package snippets.jee.jms;

import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;

import snippets.jee.jms.dto.CourseDTO;

public class CourseTopicPublisher {

    private TopicConnection connection;
    private TopicSession session;
    private Topic topic;

    public CourseTopicPublisher() throws Exception {
        InitialContext initCtx = new InitialContext();
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory)initCtx.lookup("jms/CourseManagementCF");
        connection = connectionFactory.createTopicConnection();
        connection.start();
        session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = (Topic)initCtx.lookup("jms/courseManagementTopic");
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("22:)");
            }
        }
    }

    public void publishAddCourseMessage (CourseDTO course) throws Exception {
        TopicPublisher sender = session.createPublisher(topic);
        ObjectMessage objMessage = session.createObjectMessage(course);
        sender.send(objMessage);
    }
}
