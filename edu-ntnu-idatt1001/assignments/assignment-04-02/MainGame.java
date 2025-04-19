package Oppgave02;

public class MainGame {
    public static void main(String[] args) {

        // Generate two players
        Player[] player = new Player[2];
        player[0] = new Player(0);
        player[1] = new Player(0);

        // Starting at 0 turns
        int turns = 0;

        // While both players are not
        while(!player[0].winner(player[0].totalScore) && player[1].winner(player[1].totalScore)) {

            player[0].totalScore = player[0].diceRoll(player[0].totalScore);
            player[1].totalScore = player[1].diceRoll(player[1].totalScore);
            turns += 1;
            System.out.println("Turns: " + turns + ".\n" +
            "Score, Player 1: " + player[0].totalScore + ".\n" +
            "Score, Player 2: " + player[1].totalScore + ".\n");
        }
        // Check for winner.
        if( (player[0].winner(player[0].totalScore)) && (player[1].winner(player[1].totalScore)) ){
            System.out.println("Everyone's a winner! YAY!");
        }else if (player[0].winner(player[0].totalScore)){
            System.out.println("Player 1 is victorious! All hail!!");
        }else if (player[0].winner(player[0].totalScore)) {
            System.out.println("Player 2 have claimed the victory! Glory eternal!!");
        }
    }
}
