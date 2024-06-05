 public class Gamer {
    private String playerName;
    private String score;
    private String rank;

    public Gamer(String playerName, String score, String rank) {
        this.playerName = playerName;
        this.score = score;
        this.rank = rank;
    }

     public String getPlayerName() {
         return playerName;
     }

     public void setPlayerName(String playerName) {
         this.playerName = playerName;
     }

     public String getScore() {
         return score;
     }

     public void setScore(String score) {
         this.score = score;
     }

     public String getRank() {
         return rank;
     }

     public void setRank(String rank) {
         this.rank = rank;
     }

 }
