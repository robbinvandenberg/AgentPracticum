package practicumagent; /**
 * Created by robbi on 19-4-2016.
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class FifteenAgent extends Agent {

    protected void setup() {
        // Get receivers from arguments
        Object[] args = getArguments();

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage introMsg = new ACLMessage(ACLMessage.INFORM);
                introMsg.setLanguage("meta");
                introMsg.setContent("intro");

                if(args != null) {
                    // Add receivers
                    for(Object a : args) {
                        introMsg.addReceiver(new AID(a.toString(), AID.ISLOCALNAME));
                    }
                }

                send(introMsg);
            }


        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if(msg != null) {
                    switch(msg.getPerformative()) {
                        case ACLMessage.INFORM:
                            // an agent introduced itself, now send propose back
                            if(msg.getContent().equals("intro")) {
                                ACLMessage reply = msg.createReply();
                                reply.setPerformative(ACLMessage.PROPOSE);
                                reply.setLanguage("meta");
                                reply.setContent("proposal");
                                reply.addReceiver( msg.getSender() );
                                send(reply);
                            }

                            break;
                        case ACLMessage.PROPOSE:
                            // Propose to start game
                            // refuse or accept

                            // Accept
                            if(true) {
                                ACLMessage reply = msg.createReply();
                                reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                                reply.setLanguage("meta");
                                reply.setContent("acceptproposal");
                                reply.addReceiver( msg.getSender() );
                                send(reply);
                            }
                            break;
                        case ACLMessage.ACCEPT_PROPOSAL:
                            ACLMessage reply = msg.createReply();
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setLanguage("game");
                            reply.setContent("gamestarted");
                            reply.addReceiver( msg.getSender() );
                            send(reply);
                            break;
                        default:
                            System.out.println("default case");
                    }
                    //ACLMessage reply = msg.createReply();
                    //reply.setPerformative(ACLMessage.INFORM);
                    //reply.setContent("Hello!");
                    //reply.addReceiver( msg.getSender() );
                    //send(reply);
                    msg = null;
                }
                else {
                    block();
                }
            }

        });
    }

}
