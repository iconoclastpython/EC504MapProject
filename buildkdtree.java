/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildkdtree;

public class Node {
    int num;       //identify number
    String state;  //state
    String name;   //county name
    double lat;    //latitude
    double lon;    //longitude 
    int parent;
    int leftchild;
    int rightchild;
    boolean depth;//true for depth 0,2,4... takes latitude to judge and false for depth 1,3,5... takes longitude to judge
    //The four double number below forms the "area" that belongs to each node
    double lat0;
    double lat1;
    double lon0;
    double lon1;
    Node(String state,String name,double lat,double lon,int num){
        this.state=state;
        this.name=name;
        this.lat=lat;
        this.lon=lon;
        this.num=num;
        parent=-1;
        leftchild=-1;
        rightchild=-1;
        depth=true;
        lat0=-Double.MAX_VALUE;
        lat1=Double.MAX_VALUE;
        lon0=-Double.MAX_VALUE;
        lon1=Double.MAX_VALUE;
    }

}
