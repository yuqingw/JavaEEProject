package ejb;

import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.jms.Session;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "jms/Queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageBean implements MessageListener {

    @PersistenceContext()
    EntityManager em;
    PaymentEntity pe = null;
    FineEntity fe = null;
    MemberEntity me = null;

    @Resource(mappedName = "jms/QueueConnectionFactory")
    private ConnectionFactory queueConnectionFactory;
    private Random processingTime = new Random();

    public MessageBean() {
    
    }

    public String Payment(Long memId, float amt) {
        pe = new PaymentEntity();
        pe.setAmount(amt);
        em.persist(pe);
        me = em.find(MemberEntity.class, memId);
        if (me.getFine().getAmount() > 0) {
            me.getFine().getPayments().add(pe);
            me.getFine().setAmount(0 - amt);
            return ("Payment made successfully\n"+"The outstanding fine now is "+me.getFine().getAmount()+"");
        }
        return ("Error in payment process");
    }

    public String checkFine(Long memId) {
        System.out.println("check fine entered!");
        if (em.find(MemberEntity.class, memId) == null) {
            return ("Invalid member ID");
        }
        me = em.find(MemberEntity.class, memId);
        System.out.println(me.getId());
        if (me.getFine()!=null) {
            if (me.getFine().getAmount()>0.0001)
                //to avoid float calculation error (e.g 2.2-2.20 = 0.00001)
                return ("The outstanding fine is " + me.getFine().getAmount() + " ");
            else return ("No outstanding fine");
        } else {
            return ("No outstanding fine");
        }
    }

    public void onMessage(Message inMessage) {
        System.out.println("message driven bean received the message");
        MapMessage msg = null;
        try {
            if (inMessage instanceof MapMessage) {
                msg = (MapMessage) inMessage;
                System.out.println("yeah!");
                Thread.sleep(processingTime.nextInt(5) * 1000);
                System.out.println(msg);
                setupEntities(msg);
            } else {
                System.out.println("MessageBean.onMessage: " + "Message of wrong type: " + inMessage.getClass().getName());
            }
        } catch (InterruptedException ie) {
            System.out.println("MessageBean.onMessage: " + "InterruptedException: " + ie.toString());
        } catch (Throwable te) {
            System.out.println("MessageBean.onMessage: Exception: " + te.toString());
        }

    }

    private void setupEntities(MapMessage msg) throws JMSException {
        float pAmt = 0;
        Long mId;
        String type = null;

        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        TextMessage replyMsg = null;
        Destination replyDest = null;
        Connection queueConnection = null;

        mId = msg.getLong("memId");
        pAmt = msg.getFloat("pAmt");
        type = msg.getString("option");
        String result = null;

        System.out.println(""+mId+" "+pAmt+" "+type);
        if (type.equals("display")) {
            System.out.println("display entered");
            result = checkFine(mId);
            System.out.println(result);
        }
        
        if (type.equals("pay")) {
            result = Payment(mId, pAmt);
            System.out.println(result);
        }

        try {
            connection = queueConnectionFactory.createConnection();
        } catch (Exception ex) {
            System.out.println("MessageBean.setUpEntities: " + "Unable to connect to JMS provider: " + ex.toString());
        }
        try {
            replyDest = msg.getJMSReplyTo();
            session = connection.createSession(true, 0);
            producer = session.createProducer(replyDest);
            System.out.println(result);
            replyMsg = session.createTextMessage(""+result+"");
            producer.send(replyMsg);
        } catch (JMSException je) {
            System.out.println("MessageBean.setUpEntities: " + "JMSException: " + je.toString());
        } catch (Exception e) {
            System.out.println("MessageBean.setUpEntities: " + "Exception: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException je) {
                    System.out.println("MessageBean.setUpEntities: " + "JMSException: " + je.toString());
                }
            }
            connection = null;
        }
        if (queueConnection != null) {
            try {
                queueConnection.close();
            } catch (JMSException je) {
                System.out.println("MessageBean.setUpEntities: " + "JMSException: " + je.toString());
            }
            queueConnection = null;
        }
    }
}