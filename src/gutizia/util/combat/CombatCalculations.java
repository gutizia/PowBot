package gutizia.util.combat;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Combat;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Prayer;

public class CombatCalculations {

    public static int calculateMaxHit(ClientContext ctx, AttackType attackType, int strBonus, boolean usingVoid) {
        int effectiveLevel = calculateEffectiveLevelMaxHit(ctx, attackType, usingVoid);
        System.out.println("effective level = " + effectiveLevel);
        return (int) Math.floor(0.5 + (float)effectiveLevel * (strBonus + 64) / 640);
    }

    public static int calculateAccuracy(ClientContext ctx, AttackType attackType, int attBonus, boolean usingVoid) {
        int effectiveLevel = calculateEffectiveLevelAccuracy(ctx, attackType, false, usingVoid);
        return effectiveLevel * (attBonus + 64);
    }

    public static int[] calculateDefence(ClientContext ctx, int[] defBonuses) {
        int[] defence = new int[defBonuses.length];
        int effectiveLevel = calculateEffectiveLevelAccuracy(ctx, AttackType.MELEE, true, false);
        for (int i = 0; i < defBonuses.length; i++) {
            defence[i] = effectiveLevel * (defBonuses[i] + 64);
        }
        return defence;
    }

    public static double calculateHitChance(int accuracy, int defence) {
        double hitChance;

        if (accuracy > defence)  {
            hitChance = 1 - (float)(defence + 2) / (2 * (accuracy + 1));
        } else {
            hitChance = (float)accuracy / (2 * (defence + 1));
        }
        return hitChance;
    }

    private static int calculateEffectiveLevelMaxHit(ClientContext ctx, AttackType attackType, boolean usingVoid) {
        int level = 1, effectiveLevel;
        boolean isMelee = false;
        switch (attackType) {
            case RANGE:
                level = ctx.skills.level(Constants.SKILLS_RANGE);
                break;

            case MAGIC:
                throw new IllegalArgumentException("magic can't be calculated with this method, please use it's own dedicated method");

            case MELEE: case CRUSH: case SLASH: case STAB:
                level = ctx.skills.level(Constants.SKILLS_STRENGTH);
                isMelee = true;
                break;

            default:
                break;
        }

        effectiveLevel = (int) Math.floor((double) level * getMaxHitPrayerBonus(ctx));


        effectiveLevel += 8 + (isMelee ? getStrengthStanceBonus(ctx) : getRangeStanceBonus(ctx));

        if (usingVoid) {
            effectiveLevel *= 1.10;
        }

        return effectiveLevel;
    }

    private static double getMaxHitPrayerBonus(ClientContext ctx) {
        for (Prayer.Effect effect : ctx.prayer.activePrayers()) {
            if (effect.equals(Prayer.Effect.BURST_OF_STRENGTH)) {
                return 1.05;
            } else if (effect.equals(Prayer.Effect.SUPERHUMAN_STRENGTH)) {
                return 1.10;
            } else if (effect.equals(Prayer.Effect.ULTIMATE_STRENGTH)) {
                return 1.15;
            } else if (effect.equals(Prayer.Effect.CHIVALRY)) {
                return 1.18;
            } else if (effect.equals(Prayer.Effect.PIETY)) {
                return 1.23;
            } else if (effect.equals(Prayer.Effect.SHARP_EYE)) {
                return 1.05;
            } else if (effect.equals(Prayer.Effect.HAWK_EYE)) {
                return 1.10;
            } else if (effect.equals(Prayer.Effect.EAGLE_EYE)) {
                return 1.15;
            } else if (effect.equals(Prayer.Effect.RIGOUR)) {
                return 1.23;
            }
        }
        return 1;
    }

    private static int getStrengthStanceBonus(ClientContext ctx) {
        switch (ctx.combat.style()) {
            case AGGRESSIVE:
                return 3;

            case CONTROLLED:
                return 1;

            default:
                return 0;
        }
    }

    private static int getRangeStanceBonus(ClientContext ctx) {
        if (ctx.combat.style().equals(Combat.Style.ACCURATE)) {
            return 3;
        }
        return 0;
    }

    private static int calculateEffectiveLevelAccuracy(ClientContext ctx, AttackType attackType, boolean defence, boolean usingVoid) {
        int level = 1, effectiveLevel;
        boolean isMagic = false;
        if (defence) {
            level = ctx.skills.level(Constants.SKILLS_DEFENSE);
            effectiveLevel = (int) Math.floor((double) level * getDefencePrayerBonus(ctx));
            effectiveLevel += getDefenceStanceBonus(ctx) + 8;
        } else {
            switch (attackType) {
                case RANGE:
                    level = ctx.skills.level(Constants.SKILLS_RANGE);
                    break;

                case MAGIC:
                    level = ctx.skills.level(Constants.SKILLS_MAGIC);
                    isMagic = true;
                    break;

                case MELEE: case CRUSH: case SLASH: case STAB:
                    level = ctx.skills.level(Constants.SKILLS_ATTACK);
                    break;
            }
            effectiveLevel = (int) Math.floor((double) level * getAttackPrayerBonus(ctx));
            effectiveLevel += getAttackStanceBonus(ctx) + 8;
        }

        if (usingVoid) {
            effectiveLevel *= (isMagic ? 1.45 : 1.10);
        }
        return effectiveLevel;
    }

    private static double getAttackPrayerBonus(ClientContext ctx) {
        for (Prayer.Effect effect : ctx.prayer.activePrayers()) {
            if (effect.equals(Prayer.Effect.CLARITY_OF_THOUGHT)) {
                return 1.05;
            } else if (effect.equals(Prayer.Effect.IMPROVED_REFLEXES)) {
                return 1.10;
            } else if (effect.equals(Prayer.Effect.INCREDIBLE_REFLEXES)) {
                return 1.15;
            } else if (effect.equals(Prayer.Effect.CHIVALRY)) {
                return 1.15;
            } else if (effect.equals(Prayer.Effect.PIETY)) {
                return 1.20;
            }
        }
        return 1;
    }

    private static int getAttackStanceBonus(ClientContext ctx) {
        switch (ctx.combat.style()) {
            case ACCURATE:
                return 3;

            case CONTROLLED:
                return 1;

            case DEFENSIVE:
                return 3;

            default:
                return 0;
        }
    }

    private static int getDefenceStanceBonus(ClientContext ctx) {
        switch (ctx.combat.style()) {
            case CONTROLLED:
                return 1;

            case DEFENSIVE:
                return 3;

            default:
                return 0;
        }
    }

    private static double getDefencePrayerBonus(ClientContext ctx) {
        for (Prayer.Effect effect : ctx.prayer.activePrayers()) {
            if (effect.equals(Prayer.Effect.THICK_SKIN)) {
                return 1.05;
            } else if (effect.equals(Prayer.Effect.ROCK_SKIN)) {
                return 1.10;
            } else if (effect.equals(Prayer.Effect.STEEL_SKIN)) {
                return 1.15;
            } else if (effect.equals(Prayer.Effect.CHIVALRY)) {
                return 1.20;
            } else if (effect.equals(Prayer.Effect.PIETY)) {
                return 1.25;
            } else if (effect.equals(Prayer.Effect.RIGOUR)) {
                return 1.25;
            } else if (effect.equals(Prayer.Effect.AUGURY)) {
                return 1.25;
            }
        }
        return 1;
    }
}
