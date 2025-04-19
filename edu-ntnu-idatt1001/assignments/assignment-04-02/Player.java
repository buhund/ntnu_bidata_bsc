package Oppgave02;
import java.util.Random;

public class Player {
    int totalScore;
    public Random dice = new java.util.Random(); ; // RNG

    public Player(int score){
        totalScore = score;
    }

    public int diceRoll(int score){
        int newScore;

        int roll = dice.nextInt(6);
        roll +=1;

        if(roll == 1){
            newScore = 0;
        }else if(score > 100){
            newScore = score - roll;
        }else{
            newScore = score + roll;
        }
        return newScore;
    }
    public boolean winner(int score){
        return totalScore == 100;
    }
}
