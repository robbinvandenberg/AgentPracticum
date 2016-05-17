package practicumagent; /**
 * Created by robbi on 19-4-2016.
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class FifteenAgent extends Agent {

    private boolean inGame = false;

    protected void setup() {
        // Get receivers from arguments
        Object[] args = getArguments();

        addBehaviour(new TickerBehaviour(this, 5000) {
            @Override
            public void onTick() {
                if(!inGame) {
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

                            if(msg.getLanguage().equals("meta")) {
                                // an agent introduced itself, now send propose back
                                if(message.equals("intro")) {
                                    ACLMessage reply = msg.createReply();
                                    reply.setPerformative(ACLMessage.PROPOSE);
                                    reply.setLanguage("meta");
                                    reply.setContent("proposal");
                                    send(reply);
                                }
                                else if(message.equals("gefeliciteerd")) {
                                    // stuur een afmelding en meldt jezelf af
                                    inGame = false;
                                    ACLMessage reply = msg.createReply();
                                    reply.setPerformative(ACLMessage.INFORM);
                                    reply.setLanguage("meta");
                                    reply.setContent("afmelden");
                                    send(reply);
                                }
                                else if(message.equals("afmelden")) {
                                    // afmelding ontvangen, maak je weer beschikbaar?
                                    inGame = false;
                                }
                            }
                            else if(msg.getLanguage().equals("game")) {
                                ACLMessage reply = msg.createReply();
                                reply.setPerformative(ACLMessage.INFORM);

                                // DOE EEN ZET
                                String game = turn(FifteenStack.fromString(message));
                                if(game.equals("gameover")) {
                                    reply.setLanguage("meta");
                                    reply.setContent("gefeliciteerd");
                                }
                                else {
                                    reply.setLanguage("game");
                                    reply.setContent(game);
                                }

                                send(reply);
                            }


                            break;
                        case ACLMessage.PROPOSE:
                            // Propose to start game
                            // refuse or accept

                            // Accept
                            if(!inGame) {
                                inGame = true;
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
                            String game = turn(new FifteenStack());

                            reply.setContent(game);
                            send(reply);
                            break;
                        default:
                            System.out.println("default case");
                    }
                }
                else {
                    block();
                }
            }

        });
    }

    public String turn(FifteenStack fs) {
        boolean took = false;

        // Check each stack
        for(int i = 1; i < 4; i++) {
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

        if(fs.gameOver()) {
            return "gameover";
        }
        else {
            return fs.toString();
        }
    }

}
