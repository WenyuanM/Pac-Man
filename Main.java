package Main;

import HelpingClass.Constants;

/**
 * the test main method: run this to run the whole project!
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game("New Game 1");
        game.start();

        if(Constants.MULTITHREADING){
            Game game2 = new Game("New Game 2");
            game2.start();
        }
    }
}
