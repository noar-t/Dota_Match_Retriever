import java.util.ArrayList;

/**
 * Created by noah on 5/31/17.
 */
public class Match {
    private long mMatchId;
    private int mRadiantScore;
    private int mDireScore;
    private int mMatchDuration;
    private boolean mValidMatch;
    private boolean mRadiantWin;
    private ArrayList<Player> mPlayers;


    public Match(long matchId, boolean radiantWin, int radiantScore, int direScore, int matchDuration, ArrayList<Player> players) {
        mMatchId = matchId;
        mRadiantScore = radiantScore;
        mDireScore = direScore;
        mMatchDuration = matchDuration;
        mValidMatch = matchId > 0; // catches 1v1s or any other not 5v5
        mRadiantWin = radiantWin;

        mPlayers = players;
    }

    public long getMatchId() {
        return mMatchId;
    }

    public int getMatchDuration() {
        return mMatchDuration;
    }

    public int getRadiantScore() {
        return mRadiantScore;
    }

    public int getDireScore() {
        return mDireScore;
    }

    public boolean isRadiantWin() {
        return mRadiantWin;
    }

    public ArrayList<Player> getPlayers() {
        return mPlayers;
    }

    public boolean isValidMatch() {
        return mValidMatch;
    }

    public boolean isWinner(long accountId) {
        int x = 0;
        for (Player i : mPlayers) {
            if (i.getAccountId() == accountId && x < 5 && mRadiantWin)
                return true;
            if (i.getAccountId() == accountId && x >= 5 && !mRadiantWin)
                return true;
            x++;
        }
        return false;
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
