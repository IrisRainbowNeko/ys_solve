import core.BFSSolver;
import gui.GUIMain;
import org.yaml.snakeyaml.Yaml;
import utils.Graph;
import utils.Vertex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        /*Yaml yaml=new Yaml();
        HashMap<String, Object> data = null;
        try {
            data = yaml.load(new FileInputStream("./g1.yaml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(data);
        Graph g1=new Graph(data,3);
        Vertex res=new BFSSolver().BFS(g1);
        System.out.println(res.vtx);
        System.out.println(res.data);*/
        new GUIMain().showMainFrame();
    }

}
