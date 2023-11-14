package ui.buttons;

import model.Upgradable;
import model.Upgrade;
import ui.ZooGame2;

import javax.swing.*;
import java.awt.event.MouseEvent;

// my own implementation of button class
public abstract class Button {
    protected JButton button;
    protected ZooGame2 zooGame2;
    protected String name;
    protected Upgrade upgrade;
    protected Upgradable upgradable;
    protected boolean active;


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

    // MODIFIES: this
    // EFFECTS: enables or disables the button
    public void setEnabled(Boolean value) {
        button.setEnabled(value);
    }

    // EFFECTS: returns the name of the button
    public String getName() {
        return name;
    }

    // EFFECTS: returns the upgrade associated with the button
    public Upgrade getUpgrade() {
        return upgrade;
    }

    // EFFECTS: returns the upgradable associated with the button
    public Upgradable getUpgradable() {
        return upgradable;
    }
}
