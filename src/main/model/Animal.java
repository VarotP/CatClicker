//package model;
//
//import org.json.JSONObject;
//import persistence.Writable;
//
//public class Animal extends Upgradable implements Writable {
//
//    //REQUIRES:
//    //EFFECTS: creates upgradable
//    public Animal(String name, int cost, int persec, int perclick, double scalingfactor, String special) {
//        super(name, cost, persec, perclick, scalingfactor, special);
//    }
//
//
//    //EFFECTS: returns animal object as JSON
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("name", getName());
//        json.put("count", getCount());
//        json.put("cost", getCost());
//        json.put("perSec", getPerSec());
//        json.put("perClick", getPerClick());
//        json.put("scalingFactor", getScalingFactor());
//        return json;
//    }
//}
