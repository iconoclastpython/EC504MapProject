/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neighbor;

public class Node {
    int num;       //identify number
    String state;  //state
    String name;   //county name
    double lat;    //latitude
    double lon;    //longitude 
    int parent;
    int leftchild;
    int rightchild;
    double distance;
    boolean depth;//true for depth 0,2,4... takes latitude to judge and false for depth 1,3,5... takes longitude to judge
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
        distance=Double.MAX_VALUE;
    }
    
    Node(int dep,int num,String state,String name,double lat,double lon,int parent,int ll,int rr){
        if(dep==0){
        depth=true;
        }else depth=false;
        this.num=num;
        this.state=state;
        this.name=name;
        this.lat=lat;
        this.lon=lon;
        this.parent=parent;
        leftchild=ll;
        rightchild=rr;
        distance=Double.MAX_VALUE;
    }
}
