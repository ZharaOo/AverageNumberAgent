import jade.core.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AverageNumberAgent extends Agent{
    protected ArrayList<AID> neighbors = new ArrayList<AID>();
    private Double myNumber;

    protected Double getMyNumber() {
        return this.myNumber;
    }

    protected void  setMyNumber(double myNumber) {
        this.myNumber = myNumber;
    }

    protected void setup() {
        myNumber = (double)(int)(Math.random() * 1000);

        System.out.println("Hello! I am " + getAID().getLocalName() +  ". My number is " + myNumber);

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            for (Object neighbour : args) {
                neighbors.add(new AID((String)neighbour, AID.ISLOCALNAME));
            }
        }
        else {
            doDelete();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {}

        addBehaviour(new FindAverageNumberWithErrorBehaviour());
    }

    protected void takeDown() {
        System.out.println("Agent " + getAID().getLocalName() + " terminating.");
    }
}
