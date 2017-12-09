import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.TimeUnit;

public class FindAverageNumberWithErrorBehaviour extends OneShotBehaviour {

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

            if (connectionExistParam > 0.3) {
                msg.addReceiver(name);
            }
        }

        //Генерируем ошибку в передаче данных до 2%
        Double error = agent.getMyNumber() * (Math.random() * 0.02 - 0.01);
        msg.setContent(Double.toString(agent.getMyNumber() - error));

        //Генерируем задержку в отправке сообщений до 10 миллисекунд
        Integer delay = (int)(Math.random() * 10);

        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {}

        agent.send(msg);
    }

    private void receiveMessage (AverageNumberAgent agent) {
        ACLMessage msg;
        while ((msg = myAgent.receive()) != null) {
            Double numberFromNeighbor = Double.parseDouble(msg.getContent());

            if (msg.getPerformative() == ACLMessage.INFORM) {
                double myNumber = agent.getMyNumber();

                if (numberFromNeighbor < myNumber) {
                    double average = (myNumber + numberFromNeighbor) / 2;
                    agent.setMyNumber(average);

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.REQUEST);

                    //Генерируем ошибку в передаче данных до 2%
                    Double error = (average - numberFromNeighbor) * (Math.random() * 0.02 - 0.01);
                    reply.setContent(Double.toString(average - numberFromNeighbor - error));

                    //Генерируем задержку в отправке сообщений до 10 миллисекунд
                    Integer delay = (int)(Math.random() * 10);

                    try {
                        TimeUnit.MILLISECONDS.sleep(delay);
                    } catch (InterruptedException e) {}

                    agent.send(reply);
                }
            } else if (msg.getPerformative() == ACLMessage.REQUEST) {
                agent.setMyNumber(agent.getMyNumber() + numberFromNeighbor);
            }
        }
    }
}
