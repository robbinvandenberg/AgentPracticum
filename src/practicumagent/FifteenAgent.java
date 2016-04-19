package practicumagent; /**
 * Created by robbi on 19-4-2016.
 */

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class FifteenAgent extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if(msg != null) {
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Hello!");
                    reply.addReceiver( msg.getSender() );
                    send(reply);
                }
            }
        });
    }

}
