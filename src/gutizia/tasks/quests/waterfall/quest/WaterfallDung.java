package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.bank.GetToABank;
import gutizia.tasks.bank.WithdrawItems;
import org.powerbot.script.Tile;

import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;

class WaterfallDung extends SuperTask {

    WaterfallDung() {
        setTaskInfo(new TaskInfo(
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 4 &&
                !WaterfallQuest.FIRST_CHALICE_AREA.containsOrIntersects(ctx.players.local()) &&
                !WaterfallQuest.SECOND_CHALICE_AREA.containsOrIntersects(ctx.players.local()),
                () -> WaterfallQuest.FIRST_CHALICE_AREA.containsOrIntersects(ctx.players.local()) ||
                        WaterfallQuest.SECOND_CHALICE_AREA.containsOrIntersects(ctx.players.local()),
                "WaterfallDungeon",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToABank(() -> !inventory.getItems().containsAll(WaterfallQuest.endingItems.getIds())));
        tasks.add(new WithdrawItems(WaterfallQuest.endingItems, true));
        tasks.add(new GetToAlmera(() -> inventory.getItems().containsAll(WaterfallQuest.endingItems.getIds()) &&  // have items to get into waterfall dungeon
                !(ctx.players.local().tile().distanceTo(new Tile(2521,3495,0)) < 100 || // not within 100 tiles of almera
                        WaterfallQuest.WATERFALL_DUNGEON.containsOrIntersects(ctx.players.local()))));
        tasks.add(new BoardRaft(() -> true));
        tasks.add(new UseRopeOnRock());
        tasks.add(new EnterWaterfallDoor());
        tasks.add(new GetKey());
        tasks.add(new GetToChaliceRoom());
        return tasks;
    }
}
