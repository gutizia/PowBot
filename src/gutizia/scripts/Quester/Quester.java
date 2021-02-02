package gutizia.scripts.Quester;

import gutizia.scripts.Script;
import gutizia.tasks.quests.cooks.assistant.CooksAssistant;

import java.util.ArrayList;

@org.powerbot.script.Script.Manifest(
        name = "Quester",
        description = "Does quests for you",
        properties = "",
        version = "0.0.0")

public class Quester extends Script {

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void poll() {
        super.poll();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void initStandardTasks() {
        newTasks = new ArrayList<>();
        newTasks.add(new CooksAssistant());
        changingTasks = true;
    }


}
