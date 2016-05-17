package practicumagent; /**
 * Created by robbi on 19-4-2016.
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

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
                            String message = msg.getContent();
                            // an agent introduced itself, now send propose back
                            if(message.equals("intro")) {
                                ACLMessage reply = msg.createReply();
                                reply.setPerformative(ACLMessage.PROPOSE);
                                reply.setLanguage("meta");
                                reply.setContent("proposal");
                                send(reply);
                            }
                            else if(message.equals("gefeliciteerd")) {
                                // stuur een afmelding
                                ACLMessage reply = msg.createReply();
                                reply.setPerformative(ACLMessage.INFORM);
                                reply.setLanguage("meta");
                                reply.setContent("afmelden");
                                send(reply);
                            }
                            else if(message.equals("afmelden")) {
                                // afmelding ontvangen, maak je weer beschikbaar?
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
                                send(reply);
                            }
                            break;
                        case ACLMessage.ACCEPT_PROPOSAL:
                            ACLMessage reply = msg.createReply();
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setLanguage("game");

                            // START SPEL
                            turn();

                            reply.setContent("gamestarted");
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

    public void turn() {
        FifteenStack fs = new FifteenStack();
        boolean took = false;

        // Check each stack
        for(int i = 1; i < 3; i++) {
            if(!took) {
                int amount = fs.look(i);
                if(amount > 0) {
                    Random rand = new Random();
                    int n = rand.nextInt(amount) + 1;
                    fs.take(i,n);
                    took = true;
                }
            }
        }
    }

}
