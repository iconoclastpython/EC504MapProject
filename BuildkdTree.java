/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildkdtree;
import java.io.*;
import java.util.*;

public class Buildkdtree {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        BufferedReader br=new BufferedReader(new FileReader("NationalFile.txt"));
        ArrayList<Node> temp=new ArrayList<Node>();
        String line=br.readLine();
        int i=0;
        //read data
        while(line!=null){
            String []abc=new String[4];
            abc=line.split("\t");
            Node node=new Node(abc[0],abc[1],Double.parseDouble(abc[2]),Double.parseDouble(abc[3]),i);
            temp.add(node);
            i++;
            line=br.readLine();
        }
        for(int j=1;j<temp.size();j++){
           Node pointer=temp.get(0);
           Node need=temp.get(j);
           boolean flag=true; 
           while(flag){
            if(pointer.depth){
               if(need.lat<pointer.lat){
                  if(pointer.leftchild==-1){
                    pointer.leftchild=need.num;
                    need.parent=pointer.num;
                    need.depth=!need.depth;
                    //assign range
                    need.lon0=pointer.lon0;
                    need.lon1=pointer.lon1;
                    need.lat0=pointer.lat0;
                    need.lat1=pointer.lat;
                    flag=false;
                  }else{
                    need.depth=!need.depth;
                    pointer=temp.get(pointer.leftchild);
                  }  
               }else{
                   if(pointer.rightchild==-1){
                    pointer.rightchild=need.num;
                    need.parent=pointer.num;
                    need.depth=!need.depth;
                    flag=false;
                    //assign range
                    need.lon0=pointer.lon0;
                    need.lon1=pointer.lon1;
                    need.lat0=pointer.lat;
                    need.lat1=pointer.lat1;
                   }else{
                    need.depth=!need.depth;
                    pointer=temp.get(pointer.rightchild);
                   }    
               }
            }else{
            if(need.lon<pointer.lon){
                  if(pointer.leftchild==-1){
                    pointer.leftchild=need.num;
                    need.parent=pointer.num;
                    need.depth=!need.depth;
                    //assign range
                    need.lon0=pointer.lon0;
                    need.lon1=pointer.lon;
                    need.lat0=pointer.lat0;
                    need.lat1=pointer.lat1;
                    flag=false;
                  }else{
                    need.depth=!need.depth;
                    pointer=temp.get(pointer.leftchild);
                  }  
               }else{
                   if(pointer.rightchild==-1){
                    pointer.rightchild=need.num;
                    need.parent=pointer.num;
                    need.depth=!need.depth;
                    //assign range
                    need.lon0=pointer.lon;
                    need.lon1=pointer.lon1;
                    need.lat0=pointer.lat0;
                    need.lat1=pointer.lat1;
                    flag=false;
                   }else{
                    need.depth=!need.depth;
                    pointer=temp.get(pointer.rightchild);
                   }    
               }
           }  
           }   
        }
        try (PrintWriter writer = new PrintWriter("kdtree.txt","UTF-8")) {
            for (Node temp2 : temp) {
                int shit;
                if(temp2.depth){
                    shit=0;
                }else shit=1;
                writer.println(shit+"\t"+temp2.num+"\t"+temp2.state+"\t"+temp2.name+"\t"+temp2.lat+"\t"+temp2.lon+"\t"+temp2.parent+"\t"
                        +temp2.leftchild+"\t"+temp2.rightchild+"\t"+temp2.lat0+"\t"+temp2.lat1+"\t"+temp2.lon0+"\t"+temp2.lon1);
            }
        }
    }
}
