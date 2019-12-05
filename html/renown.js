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
      processPlaylistResponse(xhr.responseText);
    } else {
      processPlaylistResponse("N/A");
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

    videoNames.push(surl);
      charNames.push(scharacter);
      phrases.push(sline);
  }
    datalist = js.list;
  // Update computation result
    return output;
    
}

function processPlaylistResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var segmentList = document.getElementById('segmentList');
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var segmentJson = js.list[i];
    console.log(segmentJson);
    
    var pname = segmentJson["name"];
    var pseg_id = segmentJson["seg_id"];
    var pseg_order = segmentJson["seg_order"];
    var sysvar = segmentJson["system"];
      if(!playListNames.includes(pname)){
    playListNames.push(pname);
      }
  }

  // Update computation result
    return output;
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
    console.log(selectedVideo.innerHTML);
}

window.onload = function() {
    refreshSegmentsList();
    refreshPlaylistsList();

}



