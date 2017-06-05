import java.util.LinkedList;

/**
 * Created by noah on 5/31/17.
 */
public class Match {
    long mMatchId;
    long[] mPlayerIds;
    int[] mPlayerHeros;


    public Match(long MatchId, long[] PlayerIds, int[] PlayerHeros) {
        mMatchId = MatchId;
        mPlayerIds = PlayerIds;
        mPlayerHeros = PlayerHeros;
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
}
