import java.util.LinkedList;

/**
 * Created by noah on 5/31/17.
 */
public class Match {
    long mMatchId;
    long[] mPlayerIds;// = new long[10];


    public Match(long MatchId, long[] PlayerId) {
        mMatchId = MatchId;
        mPlayerIds = PlayerId;
    }

    public long getMatchId() {
        return mMatchId;
    }

    public long[] getmPlayerIds() {
        return mPlayerIds;
    }
}
