package gutizia.tasks.combat.cow;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.bank.lumbridge.DepositItemsInLumbridge;
import gutizia.util.TraverseUtil;
import gutizia.util.combat.Target;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Paths;
import gutizia.util.settings.CombatSettings;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;

import java.util.ArrayList;

import static gutizia.util.combat.TargetSelector.targetSelector;

public class Cow extends SuperTask {

    public Cow() {
        setTaskInfo(new TaskInfo(
                () -> targetSelector.getTargetToKill().equals(Target.COWS) &&
                        (TraverseUtil.getClosestTileDistance(Paths.LUMBRIDGE_COW_PEN, ctx.players.local().tile()) < 10 ||
                                Areas.LUMBRIDGE_COW_PEN.containsOrIntersects(ctx.players.local())),
                () -> !targetSelector.getTargetToKill().equals(Target.COWS) &&
                        Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()) ||
                        Areas.LUMBRIDGE_BANK.contains(ctx.players.local()),
                "Cow",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToCourtyard());
        tasks.add(new DepositItemsInLumbridge());
        tasks.add(new GetToCows());
        tasks.addAll(CombatSettings.getCombatTasks());
        return tasks;
    }
}
