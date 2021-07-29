package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Vertex {
    public HashMap<String, Integer> vtx=new HashMap<String, Integer>();
    public HashMap<String, Integer> data=new HashMap<String, Integer>();
    public HashMap<String, Integer> state=new HashMap<String, Integer>();
    public HashMap<String, Boolean> active=new HashMap<String, Boolean>();

    public Vertex(HashMap<String, Integer> vtx, HashMap<String, Integer> state, HashMap<String, Boolean> active){
        this.vtx=vtx;
        this.state=state;
        this.active=active;
        vtx.forEach((k,v)->data.put(k,0));
    }
    public Vertex(HashMap<String, Integer> vtx, HashMap<String, Integer> data, HashMap<String, Integer> state, HashMap<String, Boolean> active){
        this.vtx=vtx;
        this.data=data;
        this.state=state;
        this.active=active;
    }

    public void inc(String key){
        int vnext=vtx.get(key)+1;
        vtx.put(key, vnext>state.get(key)?1:vnext);
    }

    public void addData(String key){
        if(!data.containsKey(key))
            data.put(key, 1);
        else
            data.put(key, data.get(key)+1);
    }

    @Override
    public Vertex clone(){
        return new Vertex((HashMap<String, Integer>)vtx.clone(), (HashMap<String, Integer>)data.clone(), state, active);
    }
}
