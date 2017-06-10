import java.util.ArrayList;

/**
 * Created by noah on 5/31/17.
 */
public class Match {
    long mMatchId;
    int mRadiantScore;
    int mDireScore;
    boolean mValidMatch;
    boolean mRadiantWin;
    ArrayList<Player> mPlayers;


    public Match(long matchId, boolean radiantWin, int radiantScore, int direScore, ArrayList<Player> players) {
        mMatchId = matchId;
        mRadiantScore = radiantScore;
        mDireScore = direScore;
        mValidMatch = matchId > 0; // catches 1v1s or any other not 5v5
        mRadiantWin = radiantWin;

        mPlayers = players;
    }

    public long getMatchId() {
        return mMatchId;
    }

    /*public long[] getPlayerIds() {
        return mPlayerIds;
    }

    public int[] getPlayerHeros() {
        return mPlayerHeros;
    }*/

    public boolean isValidMatch() {
        return mValidMatch;
    }

    public String toString() {
        String output;
        output = "Match Id: " + mMatchId + "\n";
        output = output + "===================\n";
        output = output + "Dire Score : " + mDireScore + " vs Radiant Score : " + mRadiantScore + "\n";
        output = output + "RadiantWinner : " + mRadiantWin + "\n";
        output = output + "\n===================\n" + "Radiant Team\n" + "===================\n";
        for (int i = 0; i <  5; i++)
            output = output + mPlayers.get(i);

        output = output + "\n===================\n" + "Dire Team\n" + "===================\n";
        for (int i = 5; i <  10; i++)
            output = output + mPlayers.get(i);

        return output;
    }
}
