package gutizia.util.combat;

public class Enemy extends CombatStats {

    public Enemy() {

    }

    /**
     * looks up stats of enemy name from internal database, and if not found, tries to look up the wiki
     * @return true if found stats from either database or the wiki
     */
    private boolean lookUpStats() {
        /*
        * if enemy name is in database
        *   get stats from there
        * else
        *   try and look up oldschool.runescape.wiki/w/ENEMY_NAME
        *
        * */

        return false;
    }

}
