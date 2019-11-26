/*eslint no-console: "error"*/
var playListNames = [];
/*
function getPlaylistNames(){
    var nameOfPlaylist = document.getElementById('playlistName').value;
    playListNames.push(nameOfPlaylist);
    Console.log(playListNames);
    return false;
}
    
function showPlaylists(){
        var i;
        for(i = 0; i<playListNames.length; i++){
            var tabs = document.getElementById('list').innerHTML;
            tabs = tabs + playListNames[i].toString;
        }
    }
    */
function showPlaylists(){  
var videoElement = document.getElementById('video');
videoElement.pause();
videoElement.removeAttribute('src'); // empty source
videoElement.load();
}  