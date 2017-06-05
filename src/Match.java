
/**
 * Created by noah on 5/31/17.
 */
public class Match {
    boolean mValidMatch;
    long mMatchId;
    long[] mPlayerIds;
    int[] mPlayerHeros;


    public Match(long MatchId, long[] PlayerIds, int[] PlayerHeros) {
        mMatchId = MatchId;
        mPlayerIds = PlayerIds;
        mPlayerHeros = PlayerHeros;
        mValidMatch = MatchId > 0; // catches 1v1s or any other not 5v5
    }

    public long getMatchId() {
        return mMatchId;
    }

    public long[] getmPlayerIds() {
        return mPlayerIds;
    }

    public int[] getPlayerHeros() {
        return mPlayerHeros;
    }

    public boolean isValidMatch() {
        return mValidMatch;
    }

    public String toString() {
        if (mValidMatch) // probably shouldnt be hardcoded like this but im lazy rn
            return "Match ID:       " + mMatchId + "\n" +
                "___________________________\n" +
                "Account ID #1:  " + mPlayerIds[0] + "\n" +
                "Hero ID:        " + mPlayerHeros[0] + "\n" +
                "Account ID #2:  " + mPlayerIds[1] + "\n" +
                "Hero ID:        " + mPlayerHeros[1] + "\n" +
                "Account ID #3:  " + mPlayerIds[2] + "\n" +
                "Hero ID:        " + mPlayerHeros[2] + "\n" +
                "Account ID #4:  " + mPlayerIds[3] + "\n" +
                "Hero ID:        " + mPlayerHeros[3] + "\n" +
                "Account ID #5:  " + mPlayerIds[4] + "\n" +
                "Hero ID:        " + mPlayerHeros[4] + "\n" +
                "Account ID #6:  " + mPlayerIds[5] + "\n" +
                "Hero ID:        " + mPlayerHeros[5] + "\n" +
                "Account ID #7:  " + mPlayerIds[6] + "\n" +
                "Hero ID:        " + mPlayerHeros[6] + "\n" +
                "Account ID #8:  " + mPlayerIds[7] + "\n" +
                "Hero ID:        " + mPlayerHeros[7] + "\n" +
                "Account ID #9:  " + mPlayerIds[8] + "\n" +
                "Hero ID:        " + mPlayerHeros[8] + "\n" +
                "Account ID #10: " + mPlayerIds[9] + "\n" +
                "Hero ID:        " + mPlayerHeros[9] + "\n" +
                "==========================\n";
        else //
            return "--Match is Invalid--\n";

    }
}
