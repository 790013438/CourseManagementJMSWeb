package snippets.jee.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

import snippets.jee.jms.dto.CourseDTO;

public class CourseTopicSubscriber {

    private TopicConnection connection;
    private TopicSession topicSession;
    private Topic topic;

    private String subscriberName;

    public CourseTopicSubscriber (String name) throws Exception {

        this.subscriberName = name;

        InitialContext initCtx = new InitialContext();
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory)initCtx.lookup("jms/CourseManagementCF");
        connection = connectionFactory.createTopicConnection();
        connection.start();
        topicSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = (Topic)initCtx.lookup("jms/courseManagementTopic");

        TopicSubscriber subscriber = topicSession.createSubscriber(topic);
        subscriber.setMessageListener(new MessageListener() {

            @Override
            public void onMessage (Message message) {
                //We expect ObjectMessage here; of type CourseDTO
                //skipping validation
                try {
                    CourseDTO course = (CourseDTO)((ObjectMessage)message).getObject();
                    //process addCourse action. For example, save it in database
                    System.out.println("Received addCourse notification for Course name - " + course.getName() + " in Subscriber " + subscriberName);
                } catch (Exception e) {
                    System.out.println("47:)");
                    //需要处理: handle and log exception
                }
            }
        });
    }

    public void stop() {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                System.out.println("58:)");
            }
        }
    }
}
