package ejb;

import javax.ejb.Stateful;
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.MapMessage;
import javax.jms.JMSException;
import javax.annotation.Resource;
import javax.jms.TextMessage;

@Stateful
public class FinePaymentManagerBean implements FinePaymentManagerRemote {

    @Resource(mappedName = "jms/QueueConnectionFactory")
    private ConnectionFactory queueConnectionFactory;
    @Resource(mappedName = "jms/Queue")
    private Queue queue;
    
    private String replyText = null;

    public String getResult(){
        return replyText;
    }

    public void sendMessageMem(Long MemId, String type, float pAmt) {
        System.out.println("enter send message");
        Connection queueConnection = null;
        Session session = null;
        MapMessage message = null;
        Queue replyQueue = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;

        try {
            queueConnection = queueConnectionFactory.createConnection();
            session = queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            replyQueue = session.createTemporaryQueue();
            consumer = session.createConsumer(replyQueue);
            FineManagerListener fm = new FineManagerListener();
            consumer.setMessageListener(fm);
            queueConnection.start();
            
            producer = session.createProducer(queue);
            message = session.createMapMessage();
            message.setJMSReplyTo(replyQueue);

            message.setLong("memId", MemId);
            message.setString("option", type);
            message.setFloat("pAmt", pAmt);

            System.out.println("message sending memId " + message.getLong("memId"));
            System.out.println("message sending fine amt " + message.getFloat("pAmt"));
            System.out.println("message sending option " + message.getString("option"));

            producer.send(message);

            System.out.println("22222");
            return;

        } catch (Exception e) {
            System.err.println("LPS PaymentManagerBean: Exception: " + e.toString());
        }
    }

    class FineManagerListener implements MessageListener {

        public void onMessage(Message inMessage) {

            System.out.println("The FinePaymentManagerListener is listening...");
            if (inMessage instanceof TextMessage) {
                System.out.println("inMessage entered");
                TextMessage txtMsg = (TextMessage) inMessage;
                System.out.println(txtMsg);
                System.out.println("PaymentManagerListener.onMessage(): " + "Processing map message...");
                try {
                    replyText = txtMsg.getText();
                    System.out.println(replyText);
                    System.out.println("7654321");
                } catch (JMSException je) {
                    System.out.println("PaymentManagerListener.onMessage: " + "Exception: " + je.toString());
                }
            }
        }
    }
}