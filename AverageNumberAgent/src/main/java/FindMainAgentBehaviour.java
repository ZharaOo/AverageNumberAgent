import jade.core.behaviours.Behaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.TimeUnit;

public class FindMainAgentBehaviour extends Behaviour {
    private int iterations = 0;

    public void action() {
        AverageNumberAgent agent = (AverageNumberAgent)myAgent;
        sendMessage(agent);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {

        }
        receiveMessage(agent);
        iterations++;
    }

    private void sendMessage (AverageNumberAgent agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for (AID name : agent.neighbors) {
            msg.addReceiver(name);
        }
        msg.setContent(Double.toString(agent.getMaxNumber()));
        myAgent.send(msg);
    }

    private void receiveMessage (AverageNumberAgent agent) {
        ACLMessage msg;
        while ((msg = myAgent.receive()) != null) {
            double maxFromNeighbors = Double.parseDouble(msg.getContent());

            if (maxFromNeighbors > agent.getMaxNumber()) {
                agent.setIsMain();
                agent.setMaxNumber(maxFromNeighbors);
            }
        }
    }

    public  boolean done() {
        AverageNumberAgent agent = (AverageNumberAgent)myAgent;
        return iterations == agent.getDiameter();
    }
}