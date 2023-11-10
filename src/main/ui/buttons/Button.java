package ui.buttons;

import ui.ZooGame2;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class Button {
    protected JButton button;
    protected ZooGame2 zooGame2;
    protected String name;
    private boolean active;

    // EFFECTS: constructs a Tool associated with the given editor
    // with its activation button inside the given parent
    public Button(ZooGame2 zooGame2, JComponent parent) {
        this.zooGame2 = zooGame2;
        active = false;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // getters
    public boolean isActive() {
        return active;
    }

    // EFFECTS: sets this Tool's active field to true
    public void activate() {
        active = true;
    }

    // EFFECTS: sets this Tool's active field to false
    public void deactivate() {
        active = false;
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton(JComponent parent);

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }

    public void setEnabled(Boolean value) {
        button.setEnabled(value);
    }

    public String getName() {
        return name;
    }
}
