var videoNames = [];
var charNames = [];
var phrases = [];
var playListNames = [];

var dataList;
var selectedVideo;
var previousVideo;

// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://5b7elq1ly4.execute-api.us-east-2.amazonaws.com/alpha/"; 

var list_segments   = base_url + "listsegments";    // GET
var list_playlists  = base_url + "listplaylists";    // GET
var create_Playlist = base_url + "createplaylist";    // POST
var delete_Playlist = base_url + "deleteplaylist";    // POST
var delete_Segment= base_url + "deletesegment";    // POST

function refreshSegmentsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_segments, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
       return processListResponse(xhr.responseText);
    } else {
     return processListResponse("N/A");
    }
  };
}

function refreshPlaylistsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_playlists, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
       return processPlaylistListResponse(xhr.responseText);
    } else {
     return processPlaylistListResponse("N/A");
    }
  };
}


/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'segmentList' with a <br>-separated list of id,character,line,url,availableRemote objects.
 */
function processListResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var segmentList = document.getElementById('segmentList');
  
  var output = [js.list.length];
  for (var i = 0; i < js.list.length; i++) {
    var segmentJson = js.list[i];
    output[i] = js.list[i];
    //console.log(segmentJson);
    
    var sid = segmentJson["id"];
    var scharacter = segmentJson["character"];
    var sline = segmentJson["line"];
    var surl = segmentJson["url"];
    var savailableRemote = segmentJson["availableRemote"];
    var sysvar = segmentJson["system"];

  }

  // Update computation result
    return output;
}

function processPlaylistListResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var segmentList = document.getElementById('segmentList');
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var segmentJson = js.list[i];
    console.log(segmentJson);
    
    var pname = segmentJson["name"];
    output = output + "<div id=\"play" + pname + "\"><b>" + pname + ":</b> = " + pname + "<br></div>";
  }
    return output;
}

function handleCreatePlaylistClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = form.playlistName.value; 
    
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", create_Playlist, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      console.log ("XHR:" + xhr.responseText);
	      processCreateResponse(xhr.responseText);
    	 } else {
    		 console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processCreateResponse("N/A");
    }
  };
}

function processCreateResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
}

function handleDeletePlaylistClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = selectedVideo.innerHTML;
    
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", delete_Playlist, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      console.log ("XHR:" + xhr.responseText);
	      processDeleteResponse(xhr.responseText);
    	 } else {
    		 console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processDeleteResponse("N/A");
    }
  };
}
    
function processDeleteResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("deleted :" + result);
}

function handleDeleteSegmentClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = selectedVideo; 
    
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", delete_Segment, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      console.log ("XHR:" + xhr.responseText);
	      processDeleteResponse(xhr.responseText);
    	 } else {
    		 console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processDeleteResponse("N/A");
    }
  };
}

function removeVideos(){
    var myNode = document.getElementById("tb");
  while (myNode.firstChild) {
    myNode.removeChild(myNode.firstChild);
  }
    videoNames = [];
    refreshSegmentsList();
}

function removePlaylists(){
      var myNode = document.getElementById("tb");
  while (myNode.firstChild) {
    myNode.removeChild(myNode.firstChild);
  }
    playListNames = [];
    refreshPlaylistsList();
}

function addVideos(){
    removePlaylists();
    var i ;
    for(i = 0; i<videoNames.length;i++){
    var name = document.createElement('p');
        name.innerHTML = charNames[i];
    var phrase = document.createElement('p');
        phrase.innerHTML = phrases[i];
    document.getElementById('tb').appendChild(name);
    document.getElementById('tb').appendChild(phrase);
    var videoElement = document.createElement('video');
    var source = document.createElement('source');
    source.src = videoNames[i];
    source.type = "video/ogg";
    videoElement.controls = true;
        videoElement.width = "153";
        videoElement.height = '180';
        videoElement.id = "video" + i;
        videoElement.onclick =""
    videoElement.append(source);
    document.getElementById('tb').appendChild(videoElement);
    }
    console.log(videoNames);
}


function getPlaylistNames(){
    var nameOfPlaylist = document.getElementById('playlistName').value;
    playListNames.push(nameOfPlaylist);
    console.log(playListNames);
    console.log("hello");
    return false;
}

function showPlaylists(){  
    removeVideos();
 var i;
        for(i = 0; i<playListNames.length; i++){
            var tabs = document.getElementById('tb');
            var x = document.createElement("p");
            x.innerHTML = playListNames[i];
            tabs.appendChild(x);
        }
} 

function addToTimeline(){
    var movedObj = selectedVideo.cloneNode(true);
    document.getElementById("videoArea").appendChild(movedObj);
}

function removeFromTimeline(video){
    var movedObj = selectedVideo.cloneNode(true);
    document.getElementById("videoArea").removeChild(movedObj);
}


window.onclick = e =>{
    selectedVideo = e.target;
}

window.onload = function() {
    refreshSegmentsList();
    refreshPlaylistsList();

}



