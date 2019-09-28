/*
 * Authors: Fuqing Wang (fuqing04@bu.edu), Hang Xu (xuh@bu.edu)
 * Game: Console-based BlackJack with 2 different play modes
 * Date: Sept. 28, 2019
 */

import java.util.Scanner;

public class BlackJack {

    public static void main(String[] args) {
        System.out.println("Welcome to Fuqing and Hang's BlackJack table!");

        // prepare game
        Game game = new Game();

        // choose play mode based on the user input
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose a play mode: (1 for single player, 2 for versus mode)");
        int mode = scan.nextInt();
        while (mode != 1 && mode != 2) {
            System.out.println("Please enter either 1 or 2");
            mode = scan.nextInt();
        }

        if (mode == 1) {
            System.out.println("Single Player Mode");
            game.PlayMode(1);
        } else {
            System.out.println("Double Player Mode");
            game.PlayMode(2);
        }
    }
}
