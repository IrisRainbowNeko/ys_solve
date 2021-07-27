package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Vertex {
    public HashMap<String, Integer> vtx=new HashMap<String, Integer>();
    public LinkedList<String> data=new LinkedList<String>();
    public HashMap<String, Integer> state=new HashMap<String, Integer>();

    public Vertex(HashMap<String, Integer> vtx, HashMap<String, Integer> state){
        this.vtx=vtx;
        this.state=state;
    }
    public Vertex(HashMap<String, Integer> vtx, LinkedList<String> data, HashMap<String, Integer> state){
        this.vtx=vtx;
        this.data=data;
        this.state=state;
    }

    public void inc(String key){
        int vnext=vtx.get(key)+1;
        vtx.put(key, vnext>state.get(key)?1:vnext);
    }

    @Override
    public Vertex clone(){
        return new Vertex((HashMap<String, Integer>)vtx.clone(), (LinkedList<String>)data.clone(), state);
    }
}
