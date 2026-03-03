/**
 * looks at player's move and always chooses
 * winning counter move
 */


class cheatStrategy implements strategy {

    /**
     * returns the winning move against player's choice
     * @param playerMove
     * @return
     */

    @Override
    public String getMove(String playerMove){

        String computerMove = "";

        switch (playerMove){

            case "R":
                computerMove = "P";
                break;

            case "P":
                computerMove = "S";
                break;

            case "S":
                computerMove = "R";
                break;

            default:
                computerMove = "X";
                break;
        }

        return computerMove;
    }
}