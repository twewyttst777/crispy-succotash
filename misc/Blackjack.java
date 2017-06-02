import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;

public class Blackjack
{
    private ArrayList<Player> players = new ArrayList();
    private Player player;
    private int playerIndex;
    private PlayingCardDeck playingDeck = new PlayingCardDeck();
    private Scanner userInput = new Scanner(System.in);
    
    public Blackjack(Player player)
    {
        this.players.add(player);
        this.player = player;
        
        System.out.println("Welcome to Blackjack!");
        playGame();
    }
    
    public void playGame()
    {
        setPlayers();
        boolean playerWantsToPlay = true;
        while(playerWantsToPlay)
        {
            setBets();
            
            playRound();
            playRound();
            
        }
    }
    
    public void setPlayers()
    {
        int numberOfCpu = (int) (Math.random() * 5 + 1);
        for(int i = 0; i < numberOfCpu; i++)
        {
            this.players.add(new Player("Guy"+i, 50));
        }
        Collections.shuffle(players);
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getName().equals(this.player.getName()))
            {
                this.playerIndex = i;
            }
        }
        Player dealer = new Player("Bob The Dealer", 9001);
        this.players.add(dealer);
    }
    
    public void setBets()
    {
        double playerBet = 0;
        if(players.get(playerIndex).checkWallet() > 0)
        {
            System.out.println("You have $" + players.get(playerIndex).checkWallet() + ", what is your bet?");
            while(true)
            {
                playerBet = userInput.nextDouble();
                //System.out.println("Okay wise guy, I don't know what casinos you've been to, but at Crispy Succotash, you have to bet NUMBERS!");
                
                if(playerBet > players.get(playerIndex).checkWallet())
                {
                    System.out.println("You can't bet more than what you have!");
                }
                else if (playerBet < 1)
                {
                    System.out.println("You have to bet more than $0!");
                }
                else
                {
                    players.get(playerIndex).setBet(playerBet);
                    break;
                }
            }           
        }
        else
        {
            System.out.println("You don't have any money. I'm sorry, you have to leave. >:(");
        }
    }
    
    public void playRound()
    {
        for(Player player : players)
        {
            player.draw(playingDeck);
            System.out.println(player.showHand());
            if(player.cardsValue() > 21)
            {
                System.out.println(player.getName() + " has over 21, they are now out of the game.");
            }
        }
    }
    
    public void hitOrStandPlayer(Player player)
    {
        String userThingy = userInput.nextLine();
        while(true)
        {
            if(userThingy.equals("hit"))
            {
                player.draw(playingDeck);
                break;
            }
            else if(userThingy.equals("stand"))
            {
                break;
            }
            else
            {
                System.out.println("HAHA, oh man are you funny, choose for real this time...");
            }
        }
    }
    
    public void hitOrStandAI(Player computer)
    {
        int random = (int) (Math.random() * 2 + 1);
        switch(random)
        {
            case 1:
                computer.draw(playingDeck);
                break;
            case 2:
                break;
            default:
                System.out.println("Error in AI decision.");
                break;
        }
    }
    
    public Player exit()
    {
        return players.get(playerIndex);
    }
}