import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.TimeUnit;

public class FindAverageNumberWithErrorBehaviour extends OneShotBehaviour {

    private final static double PROBABILITY_OF_CONNECTION_BREAK = 0.3;
    private final static int MAX_DELAY = 10;
    private final static double MAX_ERROR = 0.05;

    public void action() {
        AverageNumberAgent agent = (AverageNumberAgent)myAgent;

        for (int i = 0; i < 1000; i++) {
            sendMessage(agent);
            receiveMessage(agent);
        }

        System.out.println("Average number is " + agent.getMyNumber());
    }

    private void sendMessage (AverageNumberAgent agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        for (AID name : agent.neighbors) {
            //Генерируем возможность обрыва связи.
            double connectionExistParam = Math.random();

            if (connectionExistParam > PROBABILITY_OF_CONNECTION_BREAK) {
                msg.addReceiver(name);
            }
        }

        //Генерируем ошибку в передаче данных до 2%
        double error = agent.getMyNumber() * ((Math.random() - 0.5) * MAX_ERROR);
        msg.setContent(Double.toString(agent.getMyNumber() - error));

        //Генерируем задержку в отправке сообщений до 10 миллисекунд
        int delay = (int)(Math.random() * MAX_DELAY);

        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }

        agent.send(msg);
    }

    private void receiveMessage (AverageNumberAgent agent) {
        ACLMessage msg;
        while ((msg = myAgent.receive()) != null) {
            double numberFromNeighbor = Double.parseDouble(msg.getContent());

            if (msg.getPerformative() == ACLMessage.INFORM) {
                double myNumber = agent.getMyNumber();

                if (numberFromNeighbor < myNumber) {
                    double average = (myNumber + numberFromNeighbor) / 2;
                    agent.setMyNumber(average);

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.REQUEST);

                    //Генерируем ошибку в передаче данных до 2%
                    double error = (average - numberFromNeighbor) * ((Math.random() - 0.5) * MAX_ERROR);
                    reply.setContent(Double.toString(average - numberFromNeighbor - error));

                    //Генерируем задержку в отправке сообщений до 10 миллисекунд
                    int delay = (int)(Math.random() * MAX_DELAY);

                    try {
                        TimeUnit.MILLISECONDS.sleep(delay);
                    } catch (InterruptedException e) {
                        System.out.println(e.toString());
                    }

                    agent.send(reply);
                }
            } else if (msg.getPerformative() == ACLMessage.REQUEST) {
                agent.setMyNumber(agent.getMyNumber() + numberFromNeighbor);
            }
        }
    }
}
