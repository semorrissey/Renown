var videoNames = [];
videoNames.push("FOX1.ogg");
videoNames.push("KHAN1.ogg");
videoNames.push("KHAN2.ogg");
videoNames.push("KIRK1.ogg");
videoNames.push("KIRK2.ogg");
videoNames.push("LINDSROM1.ogg");
videoNames.push("MCCOY1.ogg");
videoNames.push("SPOK1.ogg");
videoNames.push("SPOK2.ogg");
videoNames.push("SULU1.ogg");



function addVideos(){
    var i = 0;
    for(i = 0; i<videoNames.length;i++){
    var videoElement = document.createElement('video');
    document.getElementById('tb').appendChild(videoElement);
    var source = document.createElement('source');
    source.src = videoNames[i];
    source.type = "type/ogg";
    videoElement.appendChild(source);
    }
}

var playListNames = [];

function getPlaylistNames(){
    var nameOfPlaylist = document.getElementById('playlistName').value;
    playListNames.push(nameOfPlaylist);
    console.log(playListNames);
    console.log("hello");
    return false;
}

function showPlaylists(){  
var videoElement = document.getElementById('video');
if(videoElement){
videoElement.remove();
}
 var i;
        for(i = 0; i<playListNames.length; i++){
            var tabs = document.getElementById('tb');
            var x = document.createElement("P");
            x.innerHTML = playListNames[i];
            tabs.appendChild(x);
        }
} 
