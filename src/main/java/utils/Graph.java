package utils;

import org.jblas.FloatMatrix;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    public Vertex vtx;
    public HashMap<String, LinkedList<String>> edge;

    public Graph(Vertex vtx, HashMap<String, LinkedList<String>> edge){
        this.vtx=vtx;
        this.edge=edge;
    }

    public Graph(HashMap<String, Object> data, HashMap<String, Integer> state, HashMap<String, Boolean> active){
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
        vtx=new Vertex(mvtx, state, active);
    }

    public FloatMatrix[] toMatrix(){
        return toMatrix(getVtxIdMap());
    }

    public FloatMatrix[] toMatrix(HashMap<String, Integer> vtx_id_map){

        int mw=vtx.vtx.size();
        float[] gmat=new float[mw*mw];
        edge.forEach((k,v)->{
            if(vtx.active.get(k))
                gmat[vtx_id_map.get(k)+mw*vtx_id_map.get(k)]=1;
            v.forEach((x)->{
                if(vtx.active.get(x))
                    gmat[vtx_id_map.get(k)+mw*vtx_id_map.get(x)]=1;
            });
        });

        float[] constmat=new float[mw];
        vtx.vtx.forEach((k,v)->{
            constmat[vtx_id_map.get(k)]=-v;
        });

        return new FloatMatrix[]{new FloatMatrix(mw, mw, gmat), new FloatMatrix(mw,1,constmat)};
    }

    public HashMap<String, Integer> getVtxIdMap(){
        HashMap<String, Integer> vtx_id_map=new HashMap<String, Integer>();
        int count=0;
        for (String k : vtx.vtx.keySet()) {
            vtx_id_map.put(k,count++);
        }
        return vtx_id_map;
    }

    @Override
    public Graph clone() {
        return new Graph(vtx.clone(), (HashMap<String, LinkedList<String>>)edge.clone());
    }
}
