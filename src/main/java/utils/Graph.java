package utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    public Vertex vtx;
    public HashMap<String, LinkedList<String>> edge;


    public Graph(HashMap<String, Integer> vtx, HashMap<String, LinkedList<String>> edge, HashMap<String, Integer> state){
        this.vtx=new Vertex(vtx, state);
        this.edge=edge;
    }

    public Graph(HashMap<String, Object> data, HashMap<String, Integer> state){
        HashMap<String, Integer> mvtx=new HashMap<String, Integer>();
        edge=new HashMap<String, LinkedList<String>>();

        HashMap<String, Integer> svtx=(HashMap<String, Integer>)data.get("vertex");
        svtx.forEach(mvtx::put);

        ((List<String>)data.get("edge")).forEach(ed->{
            String[] eds=ed.split("-");
            if(!edge.containsKey(eds[0]))
                edge.put(eds[0],new LinkedList<String>());
            if(!edge.containsKey(eds[1]))
                edge.put(eds[1],new LinkedList<String>());

            edge.get(eds[0]).add(eds[1]);
            edge.get(eds[1]).add(eds[0]);
        });
        vtx=new Vertex(mvtx, state);
    }
}
