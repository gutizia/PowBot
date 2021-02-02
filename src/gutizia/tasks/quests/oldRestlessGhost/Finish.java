package gutizia.tasks.quests.oldRestlessGhost;

import org.powerbot.script.rt4.ClientContext;

public class Finish extends OldRestlessGhost {

    Finish(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        LOGGER.info("finishing RestlessGhost");
    }

    @Override
    public boolean activate() {
        return false;
    }
}
