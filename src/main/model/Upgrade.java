package model;

import org.json.JSONObject;
import persistence.Writable;

public class Upgrade implements Writable {
    private String name;
    private int count;
    private int cost;
    private int perSec;
    private int perClick;
    private double scalingFactor;
    private String special;

    //REQUIRES: cost >= 0

    public Upgrade(String name, int cost, int persec, int perclick, double scalingFactor, String special) {
        this.name = name;
        this.count = 0;
        this.cost = cost;
        this.perSec = persec;
        this.perClick = perclick;
        this.scalingFactor = scalingFactor;
        this.special = special;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("count", getCount());
        json.put("cost", getCost());
        json.put("perSec", getPerSec());
        json.put("perClick", getPerClick());
        json.put("scalingFactor", scalingFactor);
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return (int) (cost * Math.pow(scalingFactor, count));
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
