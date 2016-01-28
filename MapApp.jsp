<!DOCTYPE html>
<html>
  <head>
    <title>Drawing tools</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
        width: 100%;
        position:absolute;
        top: 0;
        left: 0;
        z-index: 0;
      }
      #infoPanel{
        border-radius: 15px;
        background:#fff;
        background-color:rgba(255,255,255,0.8);
        height:500px;
        width:250px;
        position:absolute;
        top:150px;  /* adjust value accordingly */
        left:10px;
        z-index: 99;  /* adjust value accordingly */
      }
      #pleaseWait{
        border-radius: 15px;
        background:#fff;
        background-color:rgba(255,255,255,0.8);
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -50px;
        margin-left: -50px;
        width: 100px;
        height: 75px;
      }
    </style>
  </head>
<body>
<div id="map"></div>
<div id="infoPanel">
  <h4 style="text-align:center">EC504 MapApp Project</h4>
  <p>Team member: Yingchao Zhu, Yigang Wang, Dongtai Du</p>
  <p>Description: You can do two things with this project <br><br>
    1. If you click on a point in the map, it display the decimal latitude and a decimal longitude of the clicked point, and then find and report the 10 closest points to that point from the reference point set.<br><br>
    2. If you shade a rectangular area with rectangle tool above, the application highlights the set of reference points within the area in the map and counts them.
  </p>
  <p>Tools we are using: Google Maps API in javascript, AWS, JAVA for data structure.
  </p>
</div>
<div id="pleaseWait"><h4 style="text-align:center">Please wait...</h4></div>
<script type="text/javascript" language="javascript" src="MapApp.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCR-Qj7Jpzpuw9NqQJAGSbnObqhALITGNs&signed_in=true&libraries=drawing&callback=initMap"
         async defer></script>
    
  </body>
</html>
