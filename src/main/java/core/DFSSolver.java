package core;

import utils.Graph;
import utils.Vertex;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DFSSolver extends ISolver{
    @Override
    public Vertex solve(Graph graph) {
        Stack<Vertex> search_stack=new Stack<Vertex>();
        search_stack.push(graph.vtx);

        while(!search_stack.isEmpty()){
            Vertex nvtx=search_stack.pop();
            if(is_same(nvtx))
                return nvtx;
            nvtx.vtx.keySet().forEach((x)->{
                if(nvtx.active.get(x)&&nvtx.data.get(x)<nvtx.state.get(x)-1)
                    search_stack.push(spread(nvtx,graph.edge,x));
            });
        }
        return null;
    }
}
