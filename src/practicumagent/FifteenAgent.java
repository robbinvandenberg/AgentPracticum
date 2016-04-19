package practicumagent; /**
 * Created by robbi on 19-4-2016.
 */

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class FifteenAgent extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println(getAID().toString());
            }
        });
    }


}
