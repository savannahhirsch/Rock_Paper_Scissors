import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;


public class RockPaperScissorsFrame extends JFrame{


    JPanel mainPnl;
    JPanel buttonPnl;
    JPanel statsPnl;
    JPanel displayPnl;

    JButton rockBtn, paperBtn, scissorsBtn, quitBtn;
    ImageIcon rockIco, paperIco, scissorsIco, quitIco;

    JLabel playWinLbl, tieLbl, compWinLbl;
    JTextField playWinTf, tieTf, compWinTf;

    JTextArea resultsTA;
    JScrollPane resultsSroller;

    int compWin = 0;
    int playWin = 0;
    int tie = 0;

    int compRock = 0;
    int compPaper = 0;
    int compScissors = 0;

    int playRock = 0;
    int playScissors = 0;
    int playPaper = 0;

    String lastCompMove = "";
    String lastPlayMove = "";
    String compStrategy = "";

    cheatStrategy cheat = new cheatStrategy();
    randomStrategy random = new randomStrategy();

    class LeastUsedStrategy implements strategy {

        @Override

        public String getMove(String playerMove){
            int min = Math.min(playRock, Math.min(playPaper, playScissors));
            if(playRock == min)
                return "P";
            else if(playPaper == min)
                return "S";
            else
                return "R";
        }
    }

    LeastUsedStrategy leastUsed = new LeastUsedStrategy();

    class MostUsedStrategy implements strategy {

        @Override

        public String getMove(String playerMove){
            int max = Math.max(playRock, Math.min(playPaper, playScissors));
            if(playRock == max)
                return "P";
            else if(playPaper == max)
                return "S";
            else
                return "R";
        }
    }

    MostUsedStrategy mostUsed = new MostUsedStrategy();

    class LastUsedStrategy implements strategy {

        private String lastMove = "";

        @Override
        public String getMove(String playerMove){
            if(lastPlayMove.equals(""))
            {
                return random.getMove(playerMove);
            }
            else return lastPlayMove;

        }
    }

    LastUsedStrategy lastUsed = new LastUsedStrategy();


    public RockPaperScissorsFrame(){

        super("Rock, Paper, Scissors");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);

        createButtonPanel();
        mainPnl.add(buttonPnl,BorderLayout.NORTH);

        createStatsPanel();
        mainPnl.add(statsPnl, BorderLayout.SOUTH);

        createDisplayPanel();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        setVisible(true);

    }


    public void createDisplayPanel(){

        displayPnl = new JPanel();
        resultsTA = new JTextArea(15, 50);
        resultsSroller = new JScrollPane(resultsTA);
        displayPnl.add(resultsSroller);

    }



    public void createStatsPanel(){

        statsPnl = new JPanel();
        statsPnl.setLayout(new GridLayout(3, 2));

        playWinLbl = new JLabel("Player Win : ");

        compWinLbl = new JLabel("Computer Win : ");
        tieLbl = new JLabel("Tie : ");
        playWinTf = new JTextField(3);
        playWinTf.setText(0 + "");
        compWinTf = new JTextField(3);
        compWinTf.setText(0 + "");
        tieTf = new JTextField(3);
        playWinTf.setText(0 + "");
        statsPnl.add(compWinLbl);
        statsPnl.add(compWinTf);
        statsPnl.add(playWinLbl);
        statsPnl.add(playWinTf);
        statsPnl.add(tieLbl);
        statsPnl.add(tieTf);


    }



    public void createButtonPanel(){

        buttonPnl = new JPanel();
        buttonPnl.setLayout(new GridLayout(1,4));

        rockBtn = new JButton("Rock", resizeIcon("rock.jpg", 80, 80));
        rockBtn.addActionListener((ActionEvent ae) ->
                {
                    playRock ++;

                    resolveMove("R");
                }
        );

        paperBtn = new JButton("Paper", resizeIcon("paper.png", 80, 80));
        paperBtn.addActionListener((ActionEvent ae) ->
                {
                    playPaper ++;
                    resolveMove("P");
                }
        );

        scissorsBtn = new JButton("Scissors", resizeIcon("scissor.png", 80, 80));
        scissorsBtn.addActionListener((ActionEvent ae) ->
                {
                    playScissors ++;
                    resolveMove("S");
                }
        );

        quitBtn = new JButton("Quit", resizeIcon("quit.png", 80, 80));
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        buttonPnl.add(rockBtn);
        buttonPnl.add(paperBtn);
        buttonPnl.add(scissorsBtn);
        buttonPnl.add(quitBtn);

    }

    /**
     * randomly chooses and executes one of the strategies
     * to get computer's move
     * @param playerMove
     * @return computer's move
     */

    public String getComputerMove(String playerMove){

        java.util.Random rnd = new Random();
        int prob = rnd.nextInt(100) + 1;


        String computerMove = " ";

        if(prob <=10)
        {
            compStrategy = "Cheat";
            computerMove = cheat.getMove(playerMove);
        }
        else if (prob <=30)
        {
            compStrategy = "Least Used";
            computerMove = leastUsed.getMove(playerMove);

        }
        else if (prob <=50)
        {
            compStrategy = "Most Used";
            computerMove = mostUsed.getMove(playerMove);
        }
        else if(prob <= 70)
        {
            compStrategy = "Last Used";
            computerMove = lastUsed.getMove(playerMove);

        }
        else
        {
            compStrategy = "Random";
            computerMove = random.getMove(playerMove);
        }

        switch(computerMove)
        {
            case "R":
                compRock++;
                lastCompMove = "R";
                break;

            case "P":
                compPaper++;
                lastCompMove = "P";
                break;

            case "S":
                compScissors++;
                lastCompMove = "S";
                break;

            default:
                computerMove = "X";
                break;
        }

        return computerMove;
    }

    /**
     * resolves one round of play and compares moves, updates
     * stats
     * @param playerMove
     */



    public void resolveMove(String playerMove){

        String computerMove = getComputerMove(playerMove);
        String resultStr = "";

        resultsTA.append("Player: " + playerMove + "Computer: " + computerMove + "\n");


        if(computerMove.equals("R"))
        {
            if(playerMove.equals("R"))
            {
                resultStr = "Rock vs Rock it's a TIE!";
                tie++;
                tieTf.setText(tie + "");

            }
            else if (playerMove.equals("P"))
            {
                resultStr = "Paper covers Rock, Player Wins!";
                playWin++;
                playWinTf.setText(playWin + "");

            }
            else{
                resultStr = "Rock breaks Scissors, Computer WINS!";
                compWin++;
                compWinTf.setText(compWin + "");

            }
        }
        else if (computerMove.equals("P"))
        {
            if(playerMove.equals("R"))
            {
                resultStr = "Paper covers Rock, Computer WINS!";
                compWin++;
                compWinTf.setText(compWin + "");
            }
            else if(playerMove.equals("P")){

                resultStr = "Paper vs Paper it's a TIE!";
                tie++;
                tieTf.setText(tie + "");
            }
            else{
                resultStr = "Scissors cuts Paper Player WINS!";
                playWin++;
                playWinTf.setText(playWin + "");
            }
        }
        else
        {
            if(computerMove.equals("R"))
            {
                resultStr = "Rock breaks scissors Computer WINS!";
                compWin++;
                compWinTf.setText(compWin + "");
            }
            else if (computerMove.equals("P"))
            {
                resultStr = "Scissors cuts Paper Player WINS!";
                playWin++;
                playWinTf.setText(playWin + "");
            }
            else{
                resultStr = "Scissors vs Scissors it's a TIE!";
                tie++;
                tieTf.setText(tie + "");
            }
        }
        resultsTA.append(resultStr + " (Computer: " + compStrategy + ")\n");


    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

}