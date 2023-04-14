import java.util.*;
class Player{
    int pid, score, rank;
    boolean turn, rankAssigned;
    Player(int i){
        this.pid = i+1;
        this.score = 0;
        this.turn = false;
        this.rank = 0;
        this.rankAssigned = false;
    }
}
class Dice_Game{
    int N, M;
    static int roundRank=1;
    Player[] arr, shuffledPlayers;
    Dice_Game(int N, int M){
        this.N = N;     // no. of players
        this.M = M;     // total score
        arr = new Player[N];
        for (int i=0; i<N; i++)
            arr[i] = new Player(i);
    }
    void shuffle(){ //function to shuffle the player array
        List<Player> playerList = new ArrayList<>();  //convert array to list
        for (Player player : arr)
            playerList.add(player);
        Collections.shuffle(playerList);  //shuffle the list
        shuffledPlayers = new Player[N];
        shuffledPlayers = playerList.toArray(new Player[0]); //convert list back to array
        //shuffledPlayers = playerList.toArray(new Player[playerList.size()]);
    }
    void display(){
        System.out.println("CURRENT RANK TABLE");
        System.out.println("PLAYER\tSCORE");
        for (Player p : shuffledPlayers){
            if (p.score >= M){
                if (!p.rankAssigned) {
                    p.rank = roundRank++;
                    p.rankAssigned = true;
                }
                System.out.println(p.pid+"\t"+p.score+"\tCompleted | RANK: "+ p.rank);
            }
            else
                System.out.println(p.pid+"\t"+p.score);
        }
        System.out.println();
    }
    int diceRoll(){
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("Press 'r' to roll the dice: ");
            char ans = sc.next().charAt(0);
            if (ans=='r'){
                Random rand = new Random();
                return rand.nextInt(6) + 1;
            }
            else
                System.out.println("Invalid input!");
        }
    }
    void play(){
        shuffle();
        while (true)
        {
            boolean complete = true;
            for (Player p : shuffledPlayers)
                if (p.score < M)
                {
                    complete = false; //as long as complete is zero it will keep running
                    display();
                    if (p.turn){
                        p.turn = false;
                        continue;
                    }
                    System.out.println("Player-"+p.pid+" it's your turn");
                    int dice_output = diceRoll();
                    System.out.println("Dice Roll = "+dice_output);
                    switch (dice_output){
                        case 1->{
                            int dice_2 = diceRoll();
                            if (dice_2 == 1) {
                                System.out.println("You rolled 2 1's!");
                                System.out.println("Player-" + p.pid + " will miss next turn");
                                p.score += 2;
                                p.turn = true;
                            }
                            else
                                p.score += dice_2;
                        }
                        case 6->{
                            System.out.println("You get another turn!");
                            int dice_2 = diceRoll();
                            p.score += (6 + dice_2);
                        }
                        default -> p.score += dice_output;
                    }
                }
            if (complete) {
                display();
                break;
            }
        }
    }
    void finalList(){  //final list to display rankings after game is over
        Arrays.sort(arr, Comparator.comparingInt(p->p.rank));
        System.out.println("\nSCORES");
        System.out.println("RANK\tPLAYER\tSCORE");
        for (Player p : arr)
            System.out.println(p.rank+"\t"+p.pid+"\t"+p.score);
    }
}
public class Main {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]); // N: no. of players
        int M = Integer.parseInt(args[1]); // M: no. of points
        Dice_Game game = new Dice_Game(N, M);
        game.play();
        System.out.println("CONGRATS ON FINISHING THE GAME!");
        game.finalList();
    }
}