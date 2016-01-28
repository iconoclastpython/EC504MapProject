/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neighbor;
import java.util.*;
import java.io.*;
import java.math.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Neighbor extends HttpServlet{

    String latitudeParameter;
    String longitudeParameter;
    double latitude;
    double longitude;
    /**
     * @param args the command line arguments
     */
    public static Comparator<Node> idComparator=new Comparator<Node>() {

        @Override
        public int compare(Node o1, Node o2) {
            return (int)(o1.distance-o2.distance);
        }   
    };
    public static double Rdistance(double lat,double lon,Node node){
        double x=(lat-node.lat)*Math.cos((lon+node.lon)/2);
        double y=lon-node.lon;
        return (Math.sqrt(x*x+y*y)*6371);  //Km
    }        
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws java.io.IOException {
        doPost(req, res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws java.io.IOException {

        latitudeParameter = req.getParameter("latitude");
        longitudeParameter = req.getParameter("longitude");

        ArrayList<Node> temp=new ArrayList<Node>();
        FixedSizePriorityQueue<Node> container=new FixedSizePriorityQueue<Node>(10,idComparator);
        Stack<Integer> recursion=new Stack<Integer>();
        
        FileInputStream file = new FileInputStream(new File(getServletContext().getRealPath("/WEB-INF/neighborkdtree.txt")));
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        //BufferedReader br=new BufferedReader(new FileReader("/WEB-INF/kdtree.txt"));
        String line=br.readLine();
        int i=0;
        //read data
        while(line!=null){
            String []abc=new String[9];
            abc=line.split("\t");
            Node node=new Node(Integer.parseInt(abc[0]),Integer.parseInt(abc[1]),abc[2],abc[3],Double.parseDouble(abc[4]),
                    Double.parseDouble(abc[5]),Integer.parseInt(abc[6]),Integer.parseInt(abc[7]),Integer.parseInt(abc[8]));
            temp.add(node);
            i++;
            line=br.readLine();
        }
        latitude = Double.parseDouble(latitudeParameter);
        longitude = Double.parseDouble(longitudeParameter);
        Node pointer;
        recursion.add(0);
        while(!recursion.empty()){
            pointer=temp.get(recursion.pop());
            pointer.distance=Rdistance(latitude,longitude,pointer);
            container.add(pointer);
            boolean zuoyou=true;//true for right, false for left
            double compare;
            if(pointer.depth){
                compare=Math.abs(latitude-pointer.lat)*6371;
                if(pointer.lat<=latitude){
                    if(pointer.leftchild!=-1){
                        recursion.add(pointer.leftchild);
                        zuoyou=false;
                    }
                }else{
                    if(pointer.rightchild!=-1){
                        recursion.add(pointer.rightchild);
                    }
                }
            }else{
                compare=Math.abs((longitude-pointer.lon)*Math.cos((pointer.lat+latitude)/2))*6371;
                if(pointer.lon<=longitude){
                    if(pointer.leftchild!=-1){
                        recursion.add(pointer.leftchild);
                        zuoyou=false;
                    }
                }else{
                    if(pointer.rightchild!=-1){
                        recursion.add(pointer.rightchild);
                    }
                }
            }
            if(container.elementsLeft>0||compare<=container.last().distance){
                if(zuoyou){
                    if(pointer.leftchild!=-1) recursion.add(pointer.leftchild);
                }else{
                    if(pointer.rightchild!=-1) recursion.add(pointer.rightchild);
                
                }
            }
        }
        String writePath = getServletContext().getRealPath("/neighbor.txt");
        try (PrintWriter writer = new PrintWriter(writePath)) {
            for (Node temp2 : container) {
                writer.println(temp2.state+","+temp2.name+","+temp2.lat+","+temp2.lon);
            }
        }
        res.getWriter().write("Lat = " + latitudeParameter + "lng = " + longitudeParameter+ " Text file created!");
    }
}

