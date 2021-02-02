package gutizia.util.combat;

public class AttackOption {
    private final AttackStyle attackStyle;
    private final AttackType attackType;

    public AttackOption(AttackType attackType, AttackStyle attackStyle) {
        this.attackType = attackType;
        this.attackStyle = attackStyle;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public AttackStyle getAttackStyle() {
        return attackStyle;
    }
}
