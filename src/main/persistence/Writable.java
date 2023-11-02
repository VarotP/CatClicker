package persistence;

import org.json.JSONObject;

// to json interface
public interface Writable {
    //EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
