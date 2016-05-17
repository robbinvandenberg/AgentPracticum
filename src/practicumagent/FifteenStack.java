package practicumagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by robbi on 19-4-2016.
 */

//import jade.core.Agent;

public class FifteenStack  {
    private int firstStack = 3;
    private int secondStack = 5;
    private int thirdStack = 7;

    public FifteenStack() {};

    public FifteenStack(int firstStack, int secondStack, int thirdStack) {
        this.firstStack = firstStack;
        this.secondStack = secondStack;
        this.thirdStack = thirdStack;
    }

    public int look(int stack) {
        switch(stack) {
            case 1:
                return firstStack;
            case 2:
                return secondStack;
            case 3:
                return thirdStack;
            default:
                System.err.println("no valid stack");
                return -1;
        }

    }

    public void take(int stack, int amount) {
        switch(stack) {
            case 1:
                firstStack -= amount;
                break;
            case 2:
                secondStack -= amount;
                break;
            case 3:
                thirdStack -= amount;
                break;
            default:
                System.err.println("no valid stack");
        }
    }

    public boolean gameOver() {
        if(firstStack <= 0 && secondStack <= 0 && thirdStack <= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public String toString() {
        return firstStack + " " + secondStack + " " + thirdStack;
    }

    public static FifteenStack fromString(String s) {
        Scanner scanner = new Scanner(s);
        List<Integer> list = new ArrayList<Integer>();

        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }

        return new FifteenStack(list.get(0),list.get(1),list.get(2));
    }


}
