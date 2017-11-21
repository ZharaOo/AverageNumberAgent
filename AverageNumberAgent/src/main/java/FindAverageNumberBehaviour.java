import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.TimeUnit;

public class FindAverageNumberBehaviour extends OneShotBehaviour {
    protected static final String I_AM_PASSED = "I am passed";

    public void action () {
        AverageNumberAgent agent = (AverageNumberAgent)myAgent;

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {

        }

        if (agent.getIsMain()) {
            System.out.println(myAgent.getAID().getLocalName() + " is main ");
            agent.setIsPassed();
            AverageNumber averageNumber = getAverageFromNeighbors(agent, null,new AverageNumber(agent.getMyNumber()));
            System.out.println("Average number: " + averageNumber.calculate());
        } else {
            while (true) {
                ACLMessage mainMsg = myAgent.blockingReceive();

                if (!agent.getIsPassed()) {
                    agent.setIsPassed();

                    AverageNumber averageNumber = AverageNumber.parseString(mainMsg.getContent());
                    averageNumber.add(agent.getMyNumber());
                    averageNumber = getAverageFromNeighbors(agent, mainMsg,averageNumber);

                    ACLMessage answerMsg = mainMsg.createReply();
                    answerMsg.setContent(averageNumber.toString());
                    //System.out.println(agent.getAID().getLocalName() + " i reply to " + mainMsg.getSender().getLocalName());
                    agent.send(answerMsg);
                } else {
                    sendPastReplyMessage(mainMsg);
                }
            }
        }
    }

    protected AverageNumber getAverageFromNeighbors(AverageNumberAgent agent, ACLMessage mainMsg, AverageNumber averageNumberParam ) {
        AverageNumber averageNumber = averageNumberParam;

        for (AID name : agent.neighbors) {
            if (mainMsg != null && name.equals(mainMsg.getSender())) {
                continue;
            }

            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(name);
            msg.setContent(averageNumber.toString());
            agent.send(msg);

            // System.out.println(agent.getAID().getLocalName() + " i send to " + name.getLocalName());

            while (true) {
                msg = agent.receive();

                if (msg != null) {
                    if (name.equals(msg.getSender())) {
                        String msgContent = msg.getContent();

                        if (!msgContent.equals(I_AM_PASSED)) {
                            averageNumber = AverageNumber.parseString(msgContent);
                        }
                        break;
                    } else {
                        sendPastReplyMessage(msg);
                    }
                } else {
                    block();
                }
            }
        }

        return averageNumber;
    }

    protected void sendPastReplyMessage(ACLMessage msg) {
        ACLMessage answerMsg = msg.createReply();
        answerMsg.setContent(I_AM_PASSED);
        myAgent.send(answerMsg);
    }
}
