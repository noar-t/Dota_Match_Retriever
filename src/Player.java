/**
 * Created by noah on 6/7/17.
 */
public class Player {
    boolean mRadiantWinner;
    long mAccountId;
    int mHeroId;
    int[] mItemSlots;
    int[] mBackPackSlots;
    //int[] mLevelApiblity; needs to be constructed if i implement this

    public Player(long accountId, int heroId, int[] itemSlots, int[] backPackSlots) {
        mAccountId = accountId;
        mHeroId = heroId;
        mItemSlots = itemSlots;
        mBackPackSlots = backPackSlots;
    }

    public long getAccountId() {
        return mAccountId;
    }

    public int getmHeroId() {
        return mHeroId;
    }

    public int[] getItemSlots() {
        return mItemSlots;
    }

    public int[] getBackPackSlots() {
        return mBackPackSlots;
    }

    public String toString(){
        return ("AccountID : " + mAccountId + "\nHeroID : " + mHeroId + "\n");
    }
}
