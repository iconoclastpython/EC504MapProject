/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangesearch;

import java.lang.System;
import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Rangesearch extends HttpServlet{

    /**
     * @param args the command line arguments
     */
    String latitudeParameter1;
    String longitudeParameter1;
    String latitudeParameter2;
    String longitudeParameter2;
    double latitude1;
    double longitude1;
    double latitude2;
    double longitude2;
    public static ArrayList<Node> temp=new ArrayList<Node>();
    public static ArrayList<Integer> result=new ArrayList<Integer>();
    //This function judges whether the area is contained in target(return 1), or intersects(return 0)
    //or total seperate(return -1)
    public static int judge(double[] targetx,double[] targety,double[] areax,double[] areay){
        if(areax[0]>=targetx[0]&&areax[1]<=targetx[1]&&areay[0]>=targety[0]&&areay[1]<=targety[1]){
            /* contained situation */
            return 1;
        }else if(areax[0]>=targetx[1]||areax[1]<=targetx[0]||areay[0]>=targety[1]||areay[1]<=targety[0]){
            /* total seperate */
            return -1;
        }else return 0; //intersects
    }
    
    /*This function put all the child node including itself into the result*/
    public static void report(Node x){
        Stack<Integer> recursion=new Stack<Integer>();
        recursion.add(x.num);
        Node pointer;
        while(!recursion.empty()){
            pointer=temp.get(recursion.pop());
            result.add(pointer.num);
            if(pointer.leftchild!=-1){
                recursion.push(pointer.leftchild);
            }
            if(pointer.rightchild!=-1){
                recursion.push(pointer.rightchild);
            }
        }
    }
    
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws java.io.IOException {
        doPost(req, res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws java.io.IOException {
        latitudeParameter1 = req.getParameter("latitude1");
        longitudeParameter1 = req.getParameter("longitude1");
        latitudeParameter2 = req.getParameter("latitude2");
        longitudeParameter2 = req.getParameter("longitude2");  
        latitude1 = Double.parseDouble(latitudeParameter1);
        longitude1 = Double.parseDouble(longitudeParameter1);
        latitude2 = Double.parseDouble(latitudeParameter2);
        longitude2 = Double.parseDouble(longitudeParameter2);
        FileInputStream file = new FileInputStream(new File(getServletContext().getRealPath("/WEB-INF/rangekdtree.txt")));
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        String line=br.readLine();
        while(line!=null){
            String []abc=new String[13];
            abc=line.split("\t");
            Node node=new Node(Integer.parseInt(abc[0]),Integer.parseInt(abc[1]),abc[2],abc[3],Double.parseDouble(abc[4]),
                    Double.parseDouble(abc[5]),Integer.parseInt(abc[6]),Integer.parseInt(abc[7]),Integer.parseInt(abc[8]),
                    Double.parseDouble(abc[9]),Double.parseDouble(abc[10]),Double.parseDouble(abc[11]),Double.parseDouble(abc[12]));
            temp.add(node);
            line=br.readLine();
        } 
        //First condition
        double []target_lat={latitude1,latitude2};
        double []target_lon={longitude1,longitude2};
        
        Stack<Integer> recursion=new Stack<Integer>();
        Node pointer;
        recursion.push(0);
        while(!recursion.empty()){
            pointer=temp.get(recursion.pop());
            if(pointer.leftchild==-1&&pointer.rightchild==-1){
                /*cheack if the node pointer is in the target area */
                if(pointer.lat>=target_lat[0]&&pointer.lat<=target_lat[1]&&pointer.lon>=target_lon[0]&&pointer.lon<=target_lon[1])
                result.add(pointer.num);
            }else{
                if(pointer.leftchild!=-1){
                    Node left=temp.get(pointer.leftchild);
                    int judge_l=judge(target_lat,target_lon,left.lat_r,left.lon_r);
                    if(judge_l==1){ report(left);}
                    else{ if(judge_l==0) recursion.push(left.num);}
                }
                if(pointer.rightchild!=-1){
                    Node right=temp.get(pointer.rightchild);
                    int judge_r=judge(target_lat,target_lon,right.lat_r,right.lon_r);
                    if(judge_r==1){ report(right);}
                    else{ if(judge_r==0) recursion.push(right.num);}
                }
            }
        }
        String writePath = getServletContext().getRealPath("/range.txt");
        File rangetext = new File(writePath);
        rangetext.delete();
        try (FileWriter writer = new FileWriter(writePath,false)) {
            for (int resu : result) {
            Node temp2=temp.get(resu);
            writer.write(temp2.state+","+temp2.name+","+temp2.lat+","+temp2.lon+"\n");
            }
            writer.close();
        }

        res.getWriter().write("lat1 = " + latitudeParameter1 + " lat2 = " + latitudeParameter2 + " lng1 = " + 
            longitudeParameter1 + " lng2 " + longitudeParameter2 + "Text file created!");    
        }
        Runtime.getRuntime().gc();
        
    }
    

