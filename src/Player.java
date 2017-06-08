/**
 * Created by noah on 6/7/17.
 */
public class Player {
    int mAccountId;
    int[] mItemSlots;
    int[] mBackPackSlots;
    int[] mLevelApiblity; // needs to be constructed if i implement this

    public Player(int accountId/*, int[] itemSlots, int[] backPackSlots*/) {
        mAccountId = accountId;
       // mItemSlots = itemSlots;
       // mBackPackSlots = backPackSlots;
    }

    public int getAccountId() {
        return mAccountId;
    }

    public int[] getItemSlots() {
        return mItemSlots;
    }

    public int[] getBackPackSlots() {
        return mBackPackSlots;
    }
}
