package snippets.jee.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;

import snippets.jee.jms.dto.CourseDTO;

public class CourseQueueReceiver {

    private QueueConnection queueConnection;
    private QueueSession queueSession;
    private Queue queue;

    private String receiverName;

    public CourseQueueReceiver (String name) throws Exception {

        //save receiver name
        this.receiverName = name;

        //look up JMS connection factory
        InitialContext initContext = new InitialContext();
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory)initContext.lookup("jms/CourseManagementCF");

        //create JMS connection
        queueConnection = connectionFactory.createQueueConnection();
        queueConnection.start();

        //create JMS session
        queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        //look up queue
        queue = (Queue)initContext.lookup("jms/courseManagementQueue");

        QueueReceiver queueReceiver = queueSession.createReceiver(queue);
        //register message listener
        queueReceiver.setMessageListener (new MessageListener() {

            @Override
            public void onMessage (Message message) {
                //we expect ObjectMessage here; of type CourseDTO
                //skipping validation
                try {
                    CourseDTO course = (CourseDTO)((ObjectMessage)message).getObject();
                    //process addCourse action. For example, save it in the database

                    System.out.println("Received addCourse message for Course name - " + course.getName() + " in Receiver " + receiverName);
                } catch (Exception e) {
                    System.out.println("41:)");
                }
            }
        });
    }

    public void stop() {
        if (queueConnection != null) {
            try {
                queueConnection.close();
            } catch (JMSException e) {
                System.out.println("55:)");
            }
        }
    }

}
