package gutizia.util.combat;

public enum WeaponCategory {
    TWO_HAND_SWORD("2h Sword", new AttackOption(AttackType.SLASH, AttackStyle.ACCURATE),
            new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.SLASH, AttackStyle.DEFENSIVE)),
    AXE("Axe", new AttackOption(AttackType.SLASH, AttackStyle.ACCURATE), new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.SLASH, AttackStyle.DEFENSIVE)),
    BANNER("Banner", new AttackOption(AttackType.STAB, AttackStyle.ACCURATE), new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.STAB, AttackStyle.DEFENSIVE)),
    BLUNT("Blunt", new AttackOption(AttackType.CRUSH, AttackStyle.ACCURATE), new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.DEFENSIVE)),
    BLUDGEON("Bludgeon", new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE),new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE)),
    BULWARK("Bulwark", new AttackOption(AttackType.CRUSH, AttackStyle.ACCURATE), new AttackOption(AttackType.NONE, AttackStyle.NONE)),
    CLAW("Claw", new AttackOption(AttackType.SLASH, AttackStyle.ACCURATE), new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.STAB, AttackStyle.CONTROLLED), new AttackOption(AttackType.SLASH, AttackStyle.DEFENSIVE)),
    PICKAXE("Pickaxe", new AttackOption(AttackType.STAB, AttackStyle.ACCURATE), new AttackOption(AttackType.STAB, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.STAB, AttackStyle.DEFENSIVE)),
    POLEARM("Polearm", new AttackOption(AttackType.STAB, AttackStyle.CONTROLLED), new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.STAB, AttackStyle.DEFENSIVE)),
    POLESTAFF("Polestaff", new AttackOption(AttackType.CRUSH, AttackStyle.ACCURATE), new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.DEFENSIVE)),
    SCYTHE("Scythe", new AttackOption(AttackType.SLASH, AttackStyle.ACCURATE), new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.SLASH, AttackStyle.DEFENSIVE)),
    SLASH_SWORD("Slash Sword", new AttackOption(AttackType.SLASH, AttackStyle.ACCURATE), new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.STAB, AttackStyle.CONTROLLED), new AttackOption(AttackType.SLASH, AttackStyle.DEFENSIVE)),
    SPEAR("Spear", new AttackOption(AttackType.STAB, AttackStyle.CONTROLLED), new AttackOption(AttackType.SLASH, AttackStyle.CONTROLLED),
            new AttackOption(AttackType.CRUSH, AttackStyle.CONTROLLED), new AttackOption(AttackType.STAB, AttackStyle.DEFENSIVE)),
    SPIKED("Spiked", new AttackOption(AttackType.CRUSH, AttackStyle.ACCURATE), new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.STAB, AttackStyle.CONTROLLED), new AttackOption(AttackType.CRUSH, AttackStyle.DEFENSIVE)),
    STAB_SWORD("Stab Sword", new AttackOption(AttackType.STAB, AttackStyle.ACCURATE), new AttackOption(AttackType.STAB, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.STAB, AttackStyle.DEFENSIVE)),
    UNARMED("Unarmed", new AttackOption(AttackType.CRUSH, AttackStyle.ACCURATE), new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.DEFENSIVE)),
    WHIP("Whip", new AttackOption(AttackType.SLASH, AttackStyle.ACCURATE), new AttackOption(AttackType.SLASH, AttackStyle.CONTROLLED),
            new AttackOption(AttackType.SLASH, AttackStyle.DEFENSIVE)),

    BOW("Bow", new AttackOption(AttackType.RANGE, AttackStyle.ACCURATE), new AttackOption(AttackType.RANGE, AttackStyle.RAPID),
            new AttackOption(AttackType.RANGE, AttackStyle.LONGRANGE)),
    CHINCHOMPA("Chinchompa", new AttackOption(AttackType.RANGE, AttackStyle.ACCURATE), new AttackOption(AttackType.RANGE, AttackStyle.RAPID),
            new AttackOption(AttackType.RANGE, AttackStyle.LONGRANGE)),
    CROSSBOW("Crossbow", new AttackOption(AttackType.RANGE, AttackStyle.ACCURATE), new AttackOption(AttackType.RANGE, AttackStyle.RAPID),
            new AttackOption(AttackType.RANGE, AttackStyle.LONGRANGE)),
    GUN("Gun", new AttackOption(AttackType.NONE, AttackStyle.NONE), new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE)),
    THROWN("Thrown", new AttackOption(AttackType.RANGE, AttackStyle.ACCURATE), new AttackOption(AttackType.RANGE, AttackStyle.RAPID),
            new AttackOption(AttackType.RANGE, AttackStyle.LONGRANGE)),
    BLADED_STAFF("Bladed Staff", new AttackOption(AttackType.STAB, AttackStyle.ACCURATE), new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.DEFENSIVE), new AttackOption(AttackType.MAGIC, AttackStyle.DEFENSIVE_AUTOCAST),
            new AttackOption(AttackType.MAGIC, AttackStyle.AUTOCAST)),
    POWERED_STAFF("Powered Staff", new AttackOption(AttackType.MAGIC, AttackStyle.ACCURATE), new AttackOption(AttackType.MAGIC, AttackStyle.ACCURATE),
            new AttackOption(AttackType.MAGIC, AttackStyle.LONGRANGE)),
    STAFF("Staff", new AttackOption(AttackType.CRUSH, AttackStyle.ACCURATE), new AttackOption(AttackType.CRUSH, AttackStyle.AGGRESSIVE),
            new AttackOption(AttackType.CRUSH, AttackStyle.DEFENSIVE), new AttackOption(AttackType.MAGIC, AttackStyle.DEFENSIVE_AUTOCAST),
            new AttackOption(AttackType.MAGIC, AttackStyle.AUTOCAST)),
    SALAMANDER("Salamander", new AttackOption(AttackType.SLASH, AttackStyle.AGGRESSIVE), new AttackOption(AttackType.RANGE, AttackStyle.ACCURATE),
            new AttackOption(AttackType.MAGIC, AttackStyle.DEFENSIVE_AUTOCAST));


    private AttackOption[] attackOptions;
    private String categoryName;

    WeaponCategory(String categoryName, AttackOption... attackOptions) {
        this.attackOptions = attackOptions;
        this.categoryName = categoryName;
    }

    public AttackOption[] getAttackOptions() {
        return attackOptions;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
