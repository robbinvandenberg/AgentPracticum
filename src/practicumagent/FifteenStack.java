package practicumagent;

/**
 * Created by robbi on 19-4-2016.
 */

//import jade.core.Agent;

public class FifteenStack  {
    private int firstStack = 3;
    private int secondStack = 5;
    private int thirdStack = 7;

    public int look(int stack) {
        return stack;
    }

    public void take(int stack, int amount) {

    }

    public boolean gameOver() {
        return true;
    }

    public String toString() {
        return "";
    }

    public static FifteenStack fromString(String s) {
        return new FifteenStack();
    }


}
