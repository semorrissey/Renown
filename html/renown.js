var videoNames = [];
var videoIDs = [];
var charNames = [];
var phrases = [];
var playListNames = [];
var playlistURL = [];
var timelineSegments =[];
var searchResults=[];
var siteNames = [];
var siteUrls = [];

var dataList;
var selectedVideo;
var previousVideo;
var livePlaylist;
var liveSegmentID;
var currentTab;



// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://3rr4jcxnb3.execute-api.us-east-2.amazonaws.com/Gamma/"; 

var list_segments   = base_url + "listsegments";    // GET
var list_playlists  = base_url + "listplaylists";    // GET
var create_Playlist = base_url + "createplaylist";    // POST
var delete_Playlist = base_url + "deleteplaylist";    // POST
var delete_Segment= base_url + "deletesegment";    // POST
var append_Segment= base_url + "appendsegment";    // POST
var show_Playlist= base_url + "showplaylist";    //POST
var search_Segments= base_url + "searchsegments";    //POST
var upload_Segment= base_url + "uploadsegment";    // POST
var list_sites   = base_url + "listsites";    // GET
var register_site   = base_url + "registersite";    // POST
var remove_segment = base_url + "removesegment"; //POST
var unregister_Site = base_url + "unregistersite"; //POST

function refreshSegmentsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_segments, true);
   xhr.send();

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
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

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
       return processPlaylistListResponse(xhr.responseText);
    } else {
     processPlaylistListResponse("N/A");
    }
  };
}

function refreshSitesList() {
    var xhr = new XMLHttpRequest();
   xhr.open("GET", list_sites, true);
   xhr.send();
   
  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
       return processSitesListResponse(xhr.responseText);
    } else {
     return processSitesListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'segmentList' with a <br>-separated list of id,character,line,url,availableRemote objects.
 */
function processListResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var segmentList = document.getElementById('segmentList');
  resetVideos();
  
  var output = [js.list.length];
  for (var i = 0; i < js.list.length; i++) {
    var segmentJson = js.list[i];
    output[i] = js.list[i];
    
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
  var js = JSON.parse(result);
  var segmentList = document.getElementById('segmentList');
  resetPlaylists();
    
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var segmentJson = js.list[i];
    var pname = segmentJson["name"];
    output = output + "<div id=\"play" + pname + "\"><b>" + pname + ":</b> = " + pname + "<br></div>";
      
    playListNames.push(pname);
  }
    return output;
}

function processSitesListResponse(result) {
  var js = JSON.parse(result);
  var sitesList = document.getElementById('sitesList');
  resetRS();
    
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var sitesJson = js.list[i];
    
    var sname = sitesJson["site_name"];
    var surl = sitesJson["site_url"];
    output = output + "<div id=\"play" + sname + "\"><b>" + sname + ":</b> = " + sname + surl + "<br></div>";
      
    siteNames.push(sname);
    siteUrls.push(surl);
  }
    return output;
}

function handleCreatePlaylistClick(e,name)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = name; 
    
    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", create_Playlist, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processCreateResponse(xhr.responseText);
    	 } else {
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
    handleDisplay();
}

function handleDeletePlaylistClick(e)
{ 

    var form = document.createForm;
    var data = {};
    data["name"] = selectedVideo.innerHTML;
    
    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", delete_Playlist, true);
    xhr.send(js);
    
    clearTimeline();
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processDeleteResponse(xhr.responseText);
    	 } else {
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
    //location.reload();
}

function processSegClick(result){
    var js = JSON.parse(result);
    playlistURL =[];
  for (var i = 0; i < js.list.length; i++) {
    var segmentJson = js.list[i];
    var surl = segmentJson["url"];
    playlistURL.push(surl);
      
}
}

function handleRemoveSegmentClick(e, name, id, order)
{ 
    var form = document.createForm;
    var data = {};
    
    console.log("name: " + name);
    console.log("id: " + id);
    console.log("order: " + order);
    
    data["name"] = name; 
    data["seg_id"] = id;
    data["seg_order"] = order;
    
    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", remove_segment, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processDeleteResponse(xhr.responseText);
    	 } else {
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processDeleteResponse("N/A");
    }
  };
}

function handleDeleteSegmentClick(e)
{ 
    var form = document.createForm;
    var data = {};
    
    console.log(selectedVideo.innerHTML);
    data["name"] = selectedVideo.innerHTML; 
    
    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", delete_Segment, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processDeleteResponse(xhr.responseText);
    	 } else {
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processDeleteResponse("N/A");
    }
  };
  //location.reload();
}

function handleAppendSegmentClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["name"] = livePlaylist; //This should be wherever playlist name is stored
    data["seg_id"] = liveSegmentID; //This should be wherever video name is stored
    
    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", append_Segment, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processAppendResponse(xhr.responseText);
    	 } else {
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processAppendResponse("N/A");
    }
  };
}

function handleShowPlaylistClick(e, id)
{
    var form = document.createForm;
    var data = {};
    data["name"] = id;//this should be name of playlist

    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", show_Playlist, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processSegClick(xhr.responseText);
    	 } else {
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processSegClick("N/A");
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
    var xhr = new XMLHttpRequest();
    xhr.open("POST", search_Segments, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processSearchResponse(xhr.responseText);
    	 } else {
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
    resetVideos();
    clearDisplay();
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
    document.getElementById('uploadID').value = "";
    data["character"] = document.getElementById('uploadName').value;
     document.getElementById('uploadName').value = "";
    data["line"] = document.getElementById('uploadLine').value;
    document.getElementById('uploadLine').value = "";
    var segments = document.createForm.base64Encoding.value.split(',');
    data["base64EncodedValue"] = segments[1];

    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", upload_Segment, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processCreateResponse(xhr.responseText);
    	 } else {
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processCreateResponse("N/A");
    }
  };
    //location.reload();
}

function handleUploadSiteClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["site_name"] = document.getElementById('remoteName').value;
    document.getElementById('remoteName').value = "";
    data["site_url"] = document.getElementById('remoteURL').value;
    document.getElementById('remoteURL').value = "";
    
    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", register_site, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processCreateResponse(xhr.responseText);
    	 } else {
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processCreateResponse("N/A");
    }
  };
}

function handleUnregisterSiteClick(e)
{ 
    var form = document.createForm;
    var data = {};
    data["site_url"] = selectedVideo.innerHTML;
    console.log(selectedVideo.innerHTML);
    
   // location.reload();
    var js = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", unregister_Site, true);
    xhr.send(js);
    
    xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      processDeleteResponse(xhr.responseText);
    	 } else {
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processDeleteResponse("N/A");
    }
  };
}

function clearDisplay(){
    var myNode = document.getElementById("tb");
  while (myNode.firstChild) {
    myNode.removeChild(myNode.firstChild);
  }
}

function resetVideos(){
    videoNames = [];
    videoIDs = [];
    charNames = [];
    phrases = [];
  //  refreshSegmentsList();
}

function resetPlaylists(){
    playListNames = [];
    playlistURL = [];
 //   refreshPlaylistsList();
}

function resetRS(){
    siteNames = [];
    siteUrls = [];
}

//Issue with global variables, global variabes are being updated but not in the instance the function is called within

function addVideos(){
    currentTab = "library";
    clearDisplay();
    refreshSegmentsList();
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
    videoElement.src = videoNames[i];
    videoElement.type = "video/ogg";
    videoElement.controls = true;
        videoElement.width = "153";
        videoElement.height = '180';
        videoElement.id = "video" + i;
        videoElement.onclick ="";
    document.getElementById('tb').appendChild(videoElement);
    }
}

function addRS(){
    clearDisplay();
    currentTab = "remotesites";
    refreshSitesList();
    var i ;
    for(i = 0; i<siteNames.length;i++){
    var name = document.createElement('p');
        name.innerHTML = siteNames[i];
    var url = document.createElement('p');
        url.innerHTML = siteUrls[i];
    document.getElementById('tb').appendChild(name);
    document.getElementById('tb').appendChild(url);
    }
}

function getPlaylistNames(e){
    var nameOfPlaylist = document.getElementById('playlistName');
    handleCreatePlaylistClick(e,nameOfPlaylist.value);
    playListNames.push(nameOfPlaylist.value);
    nameOfPlaylist.value = "";
    handleDisplay();
    return false;
}

function showPlaylists(){ 
    currentTab = "playlist";
    clearDisplay();
    refreshPlaylistsList();
 var i;
        for(i = 0; i<playListNames.length; i++){
            var tabs = document.getElementById('tb');
            var x = document.createElement("button");
            x.type = "text";
            x.id ="pButton";
             x.innerHTML = playListNames[i];
            x.onclick = function() {
                addPlaylistsToTime(this.innerHTML);};
           
            tabs.appendChild(x);
        }
    
} 

function addToTimeline(){
    var movedObj = selectedVideo.cloneNode(false);
    var id = movedObj.id;
   if(id.includes("video")){
    movedObj.setAttribute("id","inTimeLine");
    var url = movedObj.getAttribute('src');
    var i;
    for(i = 0; i<videoNames.length;i++){
            if(url == videoNames[i]){
                liveSegmentID = videoIDs[i];
            }
                
    }
    timelineSegments.push(movedObj);
    console.log(timelineSegments[timelineSegments.length-1]);
    document.getElementById("timeArea").appendChild(timelineSegments[timelineSegments.length-1]);
    handleAppendSegmentClick(this);
   }
    
   
}

function removeFromTimeline(video){
    var movedObj = selectedVideo.cloneNode(true);
    var children = document.getElementById("timeArea").childNodes;
    var videoId;
    var nameIndex;
    var segID;
    
    var l ;
    for(l = 0; l<videoNames.length; l++){
        if(movedObj.src == videoNames[l]){
            nameIndex = l;
        }
    }
    
    var i;
    for(i = 0; i<videoIDs.length; i++){
        if(videoIDs[nameIndex] ==  videoIDs[i]){
            videoId = videoIDs[i];
        }
    }
    var k;
        for(k=0;k<timelineSegments.length;k++){
            if(movedObj.isEqualNode(timelineSegments[k])){
                timelineSegments.splice(k,1);
                segID = k + 1;
                console.log(segID);
            }
        }
    
     var j;
    for(j = 0; j<children.length;j++){
        if(movedObj.isEqualNode(children.item(j))){
            document.getElementById("timeArea").removeChild(children.item(j));
        }
    }
     if(document.getElementById("timeArea").childNodes.length == 0){
        timelineSegments = [];
    }
     handleRemoveSegmentClick(this,livePlaylist,videoId,segID);
        
}

function clearTimeline(){
     var myNode = document.getElementById("timeArea");
  while (myNode.firstChild) {
    myNode.removeChild(myNode.firstChild);
  }
    timelineSegments = [];
    
}
function addPlaylistsToTime(id){
    livePlaylist = id;
    handleShowPlaylistClick(this,id);
    var i;
    clearTimeline();
    for(i = 0; i<playlistURL.length; i++){
       var videoElement = document.createElement('video');
    videoElement.src = playlistURL[i];
    videoElement.type = "video/ogg";
    videoElement.controls = true;
        videoElement.width = "153";
        videoElement.height = '180';
        videoElement.id = "inTimeline";
        videoElement.onclick ="";
        var movedObj = videoElement.cloneNode(true);
        document.getElementById("timeArea").appendChild(movedObj);
        timelineSegments.push(videoElement);
}
}

function handleDisplay(){
    if(currentTab == "library"){
        resetVideos();
        refreshSegmentsList();
        addVideos();
    }
    else if(currentTab == "playlist"){
        resetPlaylists();
        refreshPlaylistsList();
        showPlaylists();
    }
}




window.addEventListener("click", e => {
    selectedVideo = e.target;
    console.log(e.target);
    console.log(timelineSegments.length);
    console.log(timelineSegments);
});

window.onload = function() {
    refreshSegmentsList();
    refreshPlaylistsList();
    refreshSitesList();
}



