var hasMarker = false;
var hasRectangle = false;
var selectedMarker = null;
var selectedRectangle = null;
var markContent, rectangleContent;
var queryIsClicked = false;
var lat = 0;
var lng = 0;
var ne = 0;
var sw = 0;
var map;
var infoWindow;
var markersArray = [];
//Create a XMLHttpRequest
function getXMLHttpRequest() {
  var xmlHttpReq = false;
  // to create XMLHttpRequest object in non-Microsoft browsers
  if (window.XMLHttpRequest) {
    xmlHttpReq = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
    try {
      // to create XMLHttpRequest object in later versions
      // of Internet Explorer
      xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (exp1) {
      try {
        // to create XMLHttpRequest object in older versions
        // of Internet Explorer
        xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (exp2) {
        xmlHttpReq = false;
      }
    }
  }
  return xmlHttpReq;
}
//Make XMLHttpRequest to call servlet's java class file
function makeRequest(data,shape) {
  //markerServlet
  if(shape == 'marker'){
    var xmlHttpRequest = getXMLHttpRequest();
    xmlHttpRequest.onreadystatechange = getReadyStateHandler(xmlHttpRequest,shape);
    xmlHttpRequest.open("POST", "markerQuery", true);
    xmlHttpRequest.setRequestHeader("Content-Type",
    "application/x-www-form-urlencoded");
    xmlHttpRequest.send(data);
  }
  //rectangleServlet
  if(shape == 'rectangle'){
    var xmlHttpRequest = getXMLHttpRequest();
    xmlHttpRequest.onreadystatechange = getReadyStateHandler(xmlHttpRequest,shape);
    xmlHttpRequest.open("POST", "rectangleQuery", true);
    xmlHttpRequest.setRequestHeader("Content-Type",
    "application/x-www-form-urlencoded");
    xmlHttpRequest.send(data);
  }
}
function getReadyStateHandler(xmlHttpRequest,shape) {

  // an anonymous function returned
  // it listens to the XMLHttpRequest instance
  return function() {
    if (xmlHttpRequest.readyState == 4) {
      if (xmlHttpRequest.status == 200) {
        if(shape == 'marker'){
          var location = [];
          var locationTemp = [];
          var innerLength = 0;
          //Get the text file Neighbor.java generated, parse it, put it into two dimensional array.
          $.get('neighbor.txt', function(data) {
            locationTemp = data.split('\n');
            innerLength = locationTemp[0].split(',').length;
            for (var i = 0; i < locationTemp.length; i++) {
              location[i] = [];
              var temp = locationTemp[i].split(',');
              for (var j = 0; j < innerLength; j++) {
                location[i][j] = temp[j];
              };
            };
            //According to two dimensional array "location", generate markers and infowindow on click on the map.
            for (x = 0; x < location.length; x++) {  
              marker = new google.maps.Marker({
                position: new google.maps.LatLng(location[x][2], location[x][3]),
                map: map
              });
              markersArray.push(marker);
              google.maps.event.addListener(marker, 'click', (function(marker, x) {
                return function() {
                  markerContent = "State: " + location[x][0].toString() + ", City: " + location[x][1].toString() + 
                  '<br />' + "Latitude: " + location[x][2].toString() + ", Longitude: " + location[x][3].toString();
                  infoWindow.setContent(markerContent);
                  infoWindow.open(map, marker);
                }
              })(marker, x));
            }
          });
          $('#pleaseWait').hide();
        }
        else if(shape == 'rectangle'){
          var location = [];
          var locationTemp = [];
          var innerLength = 0;
          $.get('range.txt', function(data) {
            locationTemp = data.split('\n');
            alert("There are " + locationTemp.length + " markers in this box.");
            innerLength = locationTemp[0].split(',').length;
            for (var i = 0; i < locationTemp.length; i++) {
              location[i] = [];
              var temp = locationTemp[i].split(',');
              for (var j = 0; j < innerLength; j++) {
                location[i][j] = temp[j];
              };
            };
            for (x = 0; x < location.length; x++) { 
              marker = new google.maps.Marker({
                position: new google.maps.LatLng(location[x][2], location[x][3]),
                map: map
              });
              markersArray.push(marker);
              google.maps.event.addListener(marker, 'click', (function(marker, x) {
                return function() {
                  markerContent = "State: " + location[x][0].toString() + ", City: " + location[x][1].toString() + 
                  '<br />' + "Latitude: " + location[x][2].toString() + ", Longitude: " + location[x][3].toString();
                  infoWindow.setContent(markerContent);
                  infoWindow.open(map, marker);
                }
              })(marker, x));
            }
          });
          $('#pleaseWait').hide();
        }
      } else {
        alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
      }
    }
  };
}
//"Clear" button on right center
function RightCenterControl(controlDiv, map) {
  // Set CSS for the control border.
  var clearButton = document.createElement('div');
  clearButton.style.backgroundColor = '#fff';
  clearButton.style.border = '2px solid #fff';
  clearButton.style.borderRadius = '3px';
  clearButton.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
  clearButton.style.cursor = 'pointer';
  clearButton.style.marginBottom = '22px';
  clearButton.style.textAlign = 'center';
  clearButton.title = 'Click to recenter the map';
  controlDiv.appendChild(clearButton);
  // Set CSS for the control interior.
  var clearButtonText = document.createElement('div');
  clearButtonText.style.color = 'rgb(25,25,25)';
  clearButtonText.style.fontFamily = 'Roboto,Arial,sans-serif';
  clearButtonText.style.fontSize = '16px';
  clearButtonText.style.lineHeight = '38px';
  clearButtonText.style.paddingLeft = '5px';
  clearButtonText.style.paddingRight = '5px';
  clearButtonText.innerHTML = 'Clear';
  clearButton.appendChild(clearButtonText);
  clearButton.addEventListener('click', function(){
    clearOverlays();
  });
}
//"Query" button on bottom center.
function CenterControl(controlDiv, map) {
  // Set CSS for the control border.
  var queryButton = document.createElement('div');
  queryButton.style.backgroundColor = '#fff';
  queryButton.style.border = '2px solid #fff';
  queryButton.style.borderRadius = '3px';
  queryButton.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
  queryButton.style.cursor = 'pointer';
  queryButton.style.marginBottom = '22px';
  queryButton.style.textAlign = 'center';
  queryButton.title = 'Click to recenter the map';
  controlDiv.appendChild(queryButton);
  // Set CSS for the control interior.
  var queryButtonText = document.createElement('div');
  queryButtonText.style.color = 'rgb(25,25,25)';
  queryButtonText.style.fontFamily = 'Roboto,Arial,sans-serif';
  queryButtonText.style.fontSize = '16px';
  queryButtonText.style.lineHeight = '38px';
  queryButtonText.style.paddingLeft = '5px';
  queryButtonText.style.paddingRight = '5px';
  queryButtonText.innerHTML = 'Query';
  queryButton.appendChild(queryButtonText);
  //If Query button is clicked, send parameter and request to corresponding servlet.
  queryButton.addEventListener('click', function() {
    if(hasMarker){
      $('#pleaseWait').show();
      var postData = "latitude=" + lat + "&longitude=" + lng;
      makeRequest(postData,'marker');
      hasMarker = false;
    }
    if(hasRectangle){
      $('#pleaseWait').show();
      var postData = "latitude2=" + ne.lat() + "&latitude1=" + sw.lat() + "&longitude2=" + ne.lng() + "&longitude1=" + sw.lng();
      makeRequest(postData,'rectangle');
      hasRectangle = false;
    }
  });
}
//Function to clear all the markers on the map
function clearOverlays() {
  for (var i = 0; i < markersArray.length; i++ ) {
    markersArray[i].setMap(null);
  }
  markersArray.length = 0;
}
function initMap() {
  $('#pleaseWait').hide();
  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 38, lng: -97},
    zoom: 5
  });
  infoWindow = new google.maps.InfoWindow();
  // Create the DIV to hold the control and call the CenterControl() constructor
  // passing in this DIV.
  var centerControlDiv = document.createElement('div');
  var centerControl = new CenterControl(centerControlDiv, map);
  centerControlDiv.index = 1;
  map.controls[google.maps.ControlPosition.BOTTOM_CENTER].push(centerControlDiv);
  // Create the DIV to hold the control and call the RightCenterControl() constructor
  // passing in this DIV.
  var rightCenterControlDiv = document.createElement('div');
  var rightCenterControl = new RightCenterControl(rightCenterControlDiv, map);
  rightCenterControlDiv.index = 1;
  map.controls[google.maps.ControlPosition.RIGHT_CENTER].push(rightCenterControlDiv);
  //Initialize DrawingManager
  var drawingManager = new google.maps.drawing.DrawingManager({
    drawingMode: null,
    drawingControl: true,
    drawingControlOptions: {
      position: google.maps.ControlPosition.TOP_CENTER,
      drawingModes: [
        google.maps.drawing.OverlayType.MARKER,
        google.maps.drawing.OverlayType.RECTANGLE
      ]
    }
  });
  drawingManager.setMap(map);
  //If a reactangle shade completed on the map
  google.maps.event.addListener(drawingManager, 'rectanglecomplete', function(rectangle){
    hasRectangle = true;
    selectedRectangle = rectangle;
    ne = selectedRectangle.getBounds().getNorthEast();
    sw = selectedRectangle.getBounds().getSouthWest();
    var rectangleContent = '<b>Rectangle created.</b><br>' + 'New north-east corner: ' + ne.lat() + ', ' + ne.lng() + '<br>' + 'New south-west corner: ' + sw.lat() + ', ' + sw.lng();
    infoWindow.setContent(rectangleContent);
    infoWindow.setPosition(ne);
    infoWindow.open(map);
  })
  //If a marker completed on the map
  google.maps.event.addListener(drawingManager, 'markercomplete', function(marker){
    hasMarker = true;
    selectedMarker = marker;
    lat = selectedMarker.getPosition().lat();
    lng = selectedMarker.getPosition().lng();
    markContent = "Latitude: " + lat.toString() + ", Longitude: " +  lng.toString();
    infowindow = new google.maps.InfoWindow({
      content: markContent
    });
    infowindow.open(map, selectedMarker);
  })
  //Allow only one marker or one rectangle on the map drawed by Drawing manager
  $('#map').on('mousedown', function() {
    if(selectedMarker) {
       selectedMarker.setMap(null);
    }
    if(selectedRectangle){
      selectedRectangle.setMap(null);
      infoWindow.close();
    }
  });
}
