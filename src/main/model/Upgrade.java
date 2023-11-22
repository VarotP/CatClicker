package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an upgrade object
public class Upgrade implements Writable {
    private String name;
    private int cost;
    private int perSec;
    private int perClick;
    private int unlockedAt;
    private double scalingFactor;
    private String special;

    //REQUIRES: cost >= 0

    public Upgrade(String name, int cost, int persec, int perclick, int unlockedAt, double scalingFactor,
                   String special) {
        this.name = name;
        this.cost = cost;
        this.perSec = persec;
        this.perClick = perclick;
        this.unlockedAt = unlockedAt;
        this.scalingFactor = scalingFactor;
        this.special = special;
    }

    //EFFECTS: returns upgrade as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("cost", getCost());
        json.put("perSec", getPerSec());
        json.put("perClick", getPerClick());
        json.put("scalingFactor", scalingFactor);
        json.put("unlockedAt", getUnlockedAt());
        return json;
    }

    //EFFECTS: returns upgrade as json object
    public JSONObject toJson(Boolean unlocked) {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("cost", getCost());
        json.put("perSec", getPerSec());
        json.put("perClick", getPerClick());
        json.put("scalingFactor", scalingFactor);
        json.put("unlockedAt", getUnlockedAt());
        json.put("unlocked", unlocked);
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPerSec() {
        return perSec;
    }

    public void setPerSec(int perSec) {
        this.perSec = perSec;
    }

    public int getPerClick() {
        return perClick;
    }

    public void setPerClick(int perClick) {
        this.perClick = perClick;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public int getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(int unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Upgrade upgrade = (Upgrade) o;

        return name.equals(upgrade.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
