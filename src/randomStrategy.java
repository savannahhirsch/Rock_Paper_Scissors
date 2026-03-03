/**
 * selects rock, paper or scissors with equal
 * probability using a random number generator
 */

public class randomStrategy implements strategy
{

    @Override
    public String getMove(String playerMove)
    {

        randomStrategy rand = new randomStrategy();
        java.util.Random rnd = new java.util.Random();
        String computerMove = "";
        int randomNum = rnd.nextInt(3);
        switch (randomNum){

            case 0:
                computerMove = "R";
                break;

            case 1:
                computerMove = "P";
                break;

            case 2:
                computerMove = "S";
                break;

            default:
                computerMove = "X";
                break;
        }
        return computerMove;
    }
}