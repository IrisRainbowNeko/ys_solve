package core;

import utils.Graph;
import utils.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFSSolver {
    public Vertex spread(Vertex vtx, HashMap<String, LinkedList<String>> edge, String center){
        vtx=vtx.clone();
        vtx.data.add(center);
        vtx.inc(center);
        edge.get(center).forEach(vtx::inc);
        return vtx;
    }

    public boolean is_same(Vertex vtx){
        return new HashSet<Integer>(vtx.vtx.values()).size()==1;
    }

    public Vertex BFS(Graph graph){
        Queue<Vertex> search_que=new LinkedList<Vertex>();
        search_que.offer(graph.vtx);

        while(!search_que.isEmpty()){
            Vertex nvtx=search_que.poll();
            if(is_same(nvtx))
                return nvtx;
            if(nvtx.data.size()>nvtx.vtx.size()*3*5)
                break;
            nvtx.vtx.keySet().forEach((x)->{
                if(nvtx.state.get(x)>0)
                    search_que.offer(spread(nvtx,graph.edge,x));
            });
        }
        return null;
    }
}
