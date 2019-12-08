var videoNames = [];
var videoIDs = [];
var charNames = [];
var phrases = [];
var playListNames = [];
var timelineSegments =[];
var searchResults=[];

var dataList;
var selectedVideo;
var previousVideo;
var livePlaylist;
var liveSegmentID;
var currentTab;

// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://hnmtzz8j71.execute-api.us-east-2.amazonaws.com/Beta/"; 

var list_segments   = base_url + "listsegments";    // GET
var list_playlists  = base_url + "listplaylists";    // GET
var create_Playlist = base_url + "createplaylist";    // POST
var delete_Playlist = base_url + "deleteplaylist";    // POST
var delete_Segment= base_url + "deletesegment";    // POST
var append_Segment= base_url + "appendsegment";    // POST
var show_Playlist= base_url + "showplaylist";    //POST
var search_Segments= base_url + "searchsegments";    //POST
var upload_Segment= base_url + "uploadsegment";    // POST

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
    videoIDs.push(sid);
    videoNames.push(surl);
    charNames.push(scharacter);
    phrases.push(sline);
      
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
      
    playListNames.push(pname);
  }
    return output;
}

function handleCreatePlaylistClick(e,name)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = name; 
    
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
    console.log("InnterHTML: " + selectedVideo.innerHTML);
    
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
    
    data["name"] = selectedVideo.innerHTML; 
    
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

function handleAppendSegmentClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = livePlaylist.innerHTML; //This should be wherever playlist name is stored
    data["seg_id"] = liveSegmentID; //This should be wherever video name is stored
    
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", append_Segment, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      console.log ("XHR:" + xhr.responseText);
	      processAppendResponse(xhr.responseText);
    	 } else {
    		 console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processAppendResponse("N/A");
    }
  };
}

function handleShowPlaylistClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = selectedVideo.innerHTML;//this should be name of playlist
    
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", show_Playlist, true);
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

function handleSearchSegmentsClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["character"] = document.getElementById('searchName').value;
    data["line"] = document.getElementById('searchPhrase').value;
    
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", search_Segments, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      console.log ("XHR:" + xhr.responseText);
	      processSearchResponse(xhr.responseText);
    	 } else {
    		 console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processSearchResponse("N/A");
    }
  };
}

function processSearchResponse(result) {
    videoNames = [];
    videoIDs = [];
    charNames = [];         
    phrases = [];
    removeVideos();
    processListResponse(result);
    addVideos();
    videoNames = [];
    videoIDs = [];
    charNames = [];
    phrases = [];
}


function processAppendResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("Appended :" + result);
}

function handleUploadSegmentClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["seg_id"] = document.getElementById('uploadID').value;
    data["character"] = document.getElementById('uploadName').value;
    data["line"] = document.getElementById('uploadLine').value;
    var segments = document.createForm.base64Encoding.value.split(',');
    data["base64EncodedValue"] = segments[1];

    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", upload_Segment, true);
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
    currentTab = "library";
    removePlaylists();
    var i ;
    for(i = 0; i<videoNames.length;i++){
    var name = document.createElement('p');
        name.innerHTML = charNames[i];
    var id = document.createElement('p');
        id.innerHTML = videoIDs[i];
    var phrase = document.createElement('p');
        phrase.innerHTML = phrases[i];
    document.getElementById('tb').appendChild(name);
    document.getElementById('tb').appendChild(id);
    document.getElementById('tb').appendChild(phrase);
    var videoElement = document.createElement('video');
    var source = document.createElement('source');
    videoElement.src = videoNames[i];
    videoElement.type = "video/ogg";
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

function addRS(){
    currentTab = "remote";
    removePlaylists();
    removeVideos();
    
}


function getPlaylistNames(e){
    var nameOfPlaylist = document.getElementById('playlistName').value;
    handleCreatePlaylistClick(e,nameOfPlaylist);
    playListNames.push(nameOfPlaylist);
    return false;
}

function showPlaylists(){ 
    currentTab = "playlist";
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
    var url = movedObj.getAttribute('src');
    var i;
    for(i = 0; i<videoNames.length;i++){
            if(url == videoNames[i]){
                liveSegmentID = videoIDs[i];
            }
                
    } 
    console.log(liveSegmentID);
    console.log(livePlaylist.innerHTML);
    handleAppendSegmentClick(this);
}

function removeFromTimeline(video){
    var movedObj = selectedVideo.cloneNode(true);
    document.getElementById("videoArea").removeChild(movedObj);
}

function clearTimeline(){
     var myNode = document.getElementById("videoArea");
  while (myNode.firstChild) {
    myNode.removeChild(myNode.firstChild);
  }
    timelineSegments = [];
    refreshSegmentsList();
}


window.onclick = e =>{
    selectedVideo = e.target;
    if(currentTab == "playlist"){
        livePlaylist = e.target
    }
}

window.onload = function() {
    refreshSegmentsList();
    refreshPlaylistsList();

}



