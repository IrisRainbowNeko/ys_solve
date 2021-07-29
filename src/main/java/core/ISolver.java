package core;

import utils.Graph;
import utils.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class ISolver {
    public abstract Vertex solve(Graph graph);

    public Vertex spread(Vertex vtx, HashMap<String, LinkedList<String>> edge, String center){
        vtx=vtx.clone();
        vtx.addData(center);
        vtx.inc(center);
        edge.get(center).forEach(vtx::inc);
        return vtx;
    }

    public boolean is_same(Vertex vtx){
        return new HashSet<Integer>(vtx.vtx.values()).size()==1;
    }
}
