package gutizia.util.combat;

public abstract class CombatStats {
    int[] stats = new int[14];

    public final static int OFF_STAB = 0;
    public final static int OFF_SLASH = 1;
    public final static int OFF_CRUSH = 2;
    public final static int OFF_MAGIC = 3;
    public final static int OFF_RANGE = 4;

    public final static int DEF_STAB = 5;
    public final static int DEF_SLASH = 6;
    public final static int DEF_CRUSH = 7;
    public final static int DEF_MAGIC = 8;
    public final static int DEF_RANGE = 9;

    public final static int STR_MELEE = 10;
    public final static int STR_RANGE = 11;
    public final static int STR_MAGIC = 12;
    public final static int PRAYER = 13;

    private int attackRange = 0;
    private int aggroRange = 0;

    int getOffBonus(AttackStyle attackStyle) {
        switch (attackStyle) {
            case STAB:
                return stats[OFF_STAB];

            case SLASH:
                return stats[OFF_SLASH];

            case CRUSH:
                return stats[OFF_CRUSH];

            case RANGE:
                return stats[OFF_RANGE];

            case MAGIC:
                return stats[OFF_MAGIC];

            default:
                throw new IllegalArgumentException("getOffBonus requires a valid attackStyle value, you sent: " + attackStyle);
        }
    }

    public int getDefBonus(AttackStyle attackStyle) {
        switch (attackStyle) {
            case STAB:
                return stats[DEF_STAB];

            case SLASH:
                return stats[DEF_SLASH];

            case CRUSH:
                return stats[DEF_CRUSH];

            case RANGE:
                return stats[DEF_RANGE];

            case MAGIC:
                return stats[DEF_MAGIC];

            default:
                throw new IllegalArgumentException("getDefBonus requires a valid attackStyle value, you sent: " + attackStyle);
        }
    }

    public int getStrBonus(CombatStyle combatStyle) {
        switch (combatStyle) {
            case MELEE:
                return stats[STR_MELEE];

            case RANGE:
                return stats[STR_RANGE];

            case MAGIC:
                return stats[STR_MAGIC];

            default:
                throw new IllegalArgumentException("getStrBonus requires a valid CombatStyle value, you sent: " + combatStyle);
        }
    }

    public int[] getDefBonuses() {
        return new int[] {stats[CombatStats.DEF_STAB], stats[CombatStats.DEF_SLASH], stats[CombatStats.DEF_CRUSH],
                stats[CombatStats.DEF_MAGIC], stats[CombatStats.DEF_RANGE]};
    }

    public int getPrayBonus() {
        return stats[PRAYER];
    }

    protected void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAggroRange(int aggroRange) {
        this.aggroRange = aggroRange;
    }

    public int getAggroRange() {
        return aggroRange;
    }

    public void printStats() {
        System.out.println("stab offensive bonus: " + stats[OFF_STAB]);
        System.out.println("slash offensive bonus: " + stats[OFF_SLASH]);
        System.out.println("crush offensive bonus: " + stats[OFF_CRUSH]);
        System.out.println("magic offensive bonus: " + stats[OFF_MAGIC]);
        System.out.println("range offensive bonus: " + stats[OFF_RANGE]);
        System.out.println();
        System.out.println("stab defensive bonus: " + stats[DEF_STAB]);
        System.out.println("slash defensive bonus: " + stats[DEF_SLASH]);
        System.out.println("crush defensive bonus: " + stats[DEF_CRUSH]);
        System.out.println("magic defensive bonus: " + stats[DEF_MAGIC]);
        System.out.println("range defensive bonus: " + stats[DEF_RANGE]);
        System.out.println();
        System.out.println("melee strength bonus: " + stats[STR_MELEE]);
        System.out.println("magic strength bonus: " + stats[STR_MAGIC]);
        System.out.println("range strength bonus: " + stats[STR_RANGE]);
        System.out.println();
        System.out.println("prayer bonus: " + stats[PRAYER]);
    }

    public int[] getStats() {
        return stats;
    }
}
