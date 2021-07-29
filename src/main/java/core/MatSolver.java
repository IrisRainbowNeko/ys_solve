package core;

import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;
import org.jblas.MatrixFunctions;
import org.jblas.Solve;
import utils.Graph;
import utils.Vertex;
import utils.tool;

import java.util.HashMap;


public class MatSolver extends ISolver{
    public int[] scale_to_int(float[] farr){
        float min_nz=Float.MAX_VALUE;
        for (float v : farr) {
            if (v!=0)
                min_nz=Math.min(Math.abs(v), min_nz);
        }

        int[] iarr=new int[farr.length];
        for (int i = 0; i < farr.length; i++) {
            iarr[i]=Math.round(farr[i]/min_nz);
        }
        return iarr;
    }

    public Vertex solve(Graph graph){
        HashMap<String, Integer> vtx_id_map=graph.getVtxIdMap();
        HashMap<Integer, String> vtx_id_map_inv=new HashMap<Integer, String>();
        vtx_id_map.forEach((k,v)->vtx_id_map_inv.put(v,k));

        FloatMatrix[] gmats=graph.toMatrix(vtx_id_map);
        System.out.println(gmats[0]);
        System.out.println(gmats[0]);
        System.out.println(gmats[1]);

        FloatMatrix inc_counts=Solve.pinv(gmats[0]).mmul(gmats[1]);
        System.out.println(inc_counts);
        int[] inc_iarray=scale_to_int(inc_counts.toArray());
        for (int i = 0; i < inc_iarray.length; i++) {
            inc_iarray[i]=inc_iarray[i]%graph.vtx.state.get(vtx_id_map_inv.get(i));
            if(inc_iarray[i]<0)
                inc_iarray[i]+=graph.vtx.state.get(vtx_id_map_inv.get(i));
        }
        //tool.printArray(inc_array);
        tool.printArray(inc_iarray);
        Vertex res=graph.vtx.clone();
        for (int i = 0; i < inc_iarray.length; i++) {
            if(graph.vtx.active.get(vtx_id_map_inv.get(i))) {
                for (int u = 0; u < inc_iarray[i]; u++) {
                    res.addData(vtx_id_map_inv.get(i));
                }
            }
        }

        /*{
            Vertex vtmp=res.clone();
            for (String datum : vtmp.data) {
                vtmp=spread(vtmp,graph.edge,datum);
            }
            System.out.println(vtmp.vtx);
        }*/

        return res;
    }
}
