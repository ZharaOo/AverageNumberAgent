import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.SequentialBehaviour;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AverageNumberAgent extends Agent {

    protected ArrayList<AID> neighbors = new ArrayList<AID>();
    private Integer myNumber;
    private double maxNumber;
    private boolean isMain = true;
    private int diameter;
    private boolean isPassed = false;

    protected Integer getMyNumber() {
        return this.myNumber;
    }

    protected boolean getIsPassed() {
        return this.isPassed;
    }

    protected void setIsPassed() {
        this.isPassed = true;
    }

    protected double getMaxNumber() {
        return this.maxNumber;
    }

    protected boolean getIsMain() {
        return this.isMain;
    }

    protected int getDiameter() {
        return this.diameter;
    }

    protected void setMaxNumber(double maxNumber) {
        this.maxNumber = maxNumber;
    }

    protected void setIsMain() {
        this.isMain = false;
    }

    protected void setup() {
        maxNumber = Math.random();
        myNumber = (int)(Math.random() * 1000);

        System.out.println("Hello! I am " + getAID().getLocalName() + " My max number is " + maxNumber + ". My number is " + myNumber);

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            diameter = Integer.parseInt((String)args[0]);
            for (int i = 1; i < args.length; i++) {
                neighbors.add(new AID((String)args[i], AID.ISLOCALNAME));
            }
        }
        else {
            doDelete();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {

        }

        SequentialBehaviour mainBehaviour = new SequentialBehaviour();
        mainBehaviour.addSubBehaviour(new FindMainAgentBehaviour());
        mainBehaviour.addSubBehaviour(new FindAverageNumberBehaviour());
        addBehaviour(mainBehaviour);
    }

    protected void takeDown() {
        System.out.println("Agent " + getAID().getLocalName() + " terminating.");
    }
}
