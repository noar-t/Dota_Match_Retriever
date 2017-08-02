/**
 * Created by noah on 6/7/17.
 */
public class Player {
    long mAccountId;
    int mHeroId;
    int[] mItemSlots;
    int[] mBackPackSlots;
    //int[] mLevelApiblity; needs to be constructed if i implement this

    public Player(long accountId, int heroId, int playerSlot, int[] backPackSlots, int[] itemSlots) {
        mAccountId = accountId;
        mHeroId = heroId;
        mItemSlots = itemSlots;
        mBackPackSlots = backPackSlots;
    }

    public long getAccountId() {
        return mAccountId;
    }

    public int getHeroId() {
        return mHeroId;
    }

    public int[] getItemSlots() {
        return mItemSlots;
    }

    public int[] getBackPackSlots() {
        return mBackPackSlots;
    }

    public String toString(){
        String output;
       // output = "--------------------\n";
        output = "AccountID   : " + mAccountId + "\n";
        output = output + "HeroID      : " + mHeroId + "\n";

        for (int i = 0; i < 6; i++) {
            output = output + "Item Slot " + i + " : " + mItemSlots[i] + "\n";
        }
        for (int i = 0; i < 3; i++) {
            output = output + "Backpack Slot " + i + " : " + mBackPackSlots[i] + "\n";
        }
        output = output + "--------------------\n";

        return output;
    }
}
