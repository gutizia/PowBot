package gutizia.util.userInterface;

import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;

public abstract class TabUI extends AbstractUI {
    protected JTabbedPane tab;

    protected abstract void initTab();

    public TabUI(ClientContext ctx, String fTitle) {
        super(ctx, fTitle);
        initTab();
    }
}
