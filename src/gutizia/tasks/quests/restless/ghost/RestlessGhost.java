package gutizia.tasks.quests.restless.ghost;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.Quest;
import gutizia.util.settings.QuestSettings;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;

import java.util.ArrayList;

public class RestlessGhost extends SuperTask {

    final static Tile[] LUMBRIDGE_CHURCH = new Tile[] {new Tile(3227,3219,0),new Tile(3234,3216,0),new Tile(3236,3209,0)};
    final static Area ALTAR_ROOM = new Area(new Tile(3111,9564,0),new Tile(3121,9569,0));
    final static Area COFFIN_ROOM = new Area(new Tile(3247,3190,0),new Tile(3252,3195,0));
    final static Area CHURCH = new Area(new Tile(3240,3204,0),new Tile(3247,3215,0));
    final static Area SHACK = new Area(new Tile(3144,3173,0),new Tile(3151,3177,0));
    final static int VARBIT = 107;

    public RestlessGhost() {
        setTaskInfo(new TaskInfo(
                () -> QuestSettings.getCurrentQuest().equals(Quest.RESTLESS_GHOST),
                () -> ctx.varpbits.varpbit(VARBIT) >= 10,
                "RestlessGhost",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToFatherAereck());
        tasks.add(new TalkToFatherAereck());
        return tasks;
    }
}
