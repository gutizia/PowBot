package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.Item;
import gutizia.util.Quest;
import gutizia.util.constants.Items;
import gutizia.util.constants.Runes;
import gutizia.util.settings.QuestSettings;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;

import java.util.ArrayList;

public class WaterfallQuest extends SuperTask {

    final static Area GLARIALS_TOMB = new Area(new Tile(2526,9809,0),new Tile(2557,9848,0));
    final static Area ALMERA_HOUSE = new Area(new Tile(2510,3489,0),new Tile(2526,3502,0));
    final static Area LOWER_RIVER_ISLAND = new Area(new Tile(2512,3466,0),new Tile(2513,3468,0));
    final static Area UPPER_RIVER_ISLAND = new Area(new Tile(2511,3476,0),new Tile(2512,3481,0));
    final static Area FIRST_CHALICE_AREA = new Area(new Tile(2561,9902,0),new Tile(2570,9918));
    final static Area WATERFALL_DUNGEON = new Area(new Tile(2562,9861,0),new Tile(2595,9901,0));
    final static Area SECOND_CHALICE_AREA = new Area(new Tile(2598,9900,0),new Tile(2609,9917,0));
    final static int VARBIT = 65;

    static gutizia.util.Items requiredItems = new gutizia.util.Items(
            new Item(Runes.AIR, 6),
            new Item(Runes.EARTH, 6),
            new Item(Runes.WATER, 6),
            new Item(Items.ROPE, 1)
            // TODO add food to required items
    );

    static gutizia.util.Items startingItems = new gutizia.util.Items(
            new Item(Runes.WATER, 6),
            new Item(Items.ROPE, 1),
            new Item(Items.TELEPORT_TO_HOUSE_TABLET, 5)
            // TODO add food to required items
    );

    static gutizia.util.Items endingItems = new gutizia.util.Items(
            new Item(Runes.AIR, 6),
            new Item(Runes.EARTH, 6),
            new Item(Runes.WATER, 6),
            new Item(Items.ROPE, 1),
            new Item(Items.TELEPORT_TO_HOUSE_TABLET, 5),
            new Item(Items.GLARIALS_AMULET, 1),
            new Item(Items.GLARIALS_URN, 1)
            // TODO add food to required items
    );

    public WaterfallQuest() {
        setTaskInfo(new TaskInfo(
                () -> QuestSettings.getCurrentQuest().equals(Quest.WATERFALL_QUEST),
                () -> ctx.varpbits.varpbit(VARBIT) >= 10,
                "WaterfallQuest",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new StartQuest());
        tasks.add(new Hudon());
        tasks.add(new BookOnBaxtorian());
        tasks.add(new Pebble());
        tasks.add(new Tomb());
        tasks.add(new WaterfallDung());
        tasks.add(new Chalice());
        return tasks;
    }
}
