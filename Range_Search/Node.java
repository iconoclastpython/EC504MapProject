/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangesearch;

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
    double lat_r[];
    double lon_r[];
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
        lat_r=new double[2];
        lon_r=new double[2];
        lat_r[0]=Double.MIN_VALUE;
        lat_r[1]=Double.MAX_VALUE;
        lon_r[0]=Double.MIN_VALUE;
        lon_r[1]=Double.MAX_VALUE;
    }
    Node(int dep,int num,String state,String name,double lat,double lon,int parent,int ll,int rr,
            double lat0,double lat1,double lon0,double lon1){
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
        lat_r=new double[2];
        lon_r=new double[2];
        lat_r[0]=lat0;
        lat_r[1]=lat1;
        lon_r[0]=lon0;
        lon_r[1]=lon1;
    }

}
