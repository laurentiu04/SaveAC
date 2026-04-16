package ro.ac.castravetii;

public class XPSystem {
    private int xp = 0;
    private int level = 0;
    private final int maxLevel = 40;
    private int levelUpXP = 100;

    public void addXP(int amount) {

        if (level == maxLevel) return;

        xp += amount;

        while (xp >= levelUpXP) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        xp -= levelUpXP;
        levelUpXP = 100 + (int)Math.pow(level + 1, 2);

        /** TODO:
         * Sistem de upgrade pentru player (ceva pop-up cu 3 optiuni poate
         * din care player-ul sa aleaga una permanenta.
         * */
    }

    public int getLevel() { return level; }

    public int getXP() { return xp; }

    public int getLevelUpXP() { return levelUpXP; }
}
