package snippets.jee.jms;

public class CourseTopicPublisher {

    private TopicConnection connection;
    private TopicSession session;
    private Topic topic;

    public CourseTopicPublisher() throws Exception {
        InitialContext initCtx = new InitialContext();
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory)initCtx.lookup("jms/CourseManagementCF");
        connection connectionFactory.createTopicConnection();
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
