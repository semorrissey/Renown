/*eslint no-console: "error"*/
var playListNames = [];

function getPlaylistNames(){
    var nameOfPlaylist = document.getElementById('playlistName').value;
    playListNames.push(nameOfPlaylist);
    console.log(playListNames);
    console.log("hello");
    return false;
}
/*    
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
videoElement.remove();
 var i;
        for(i = 0; i<playListNames.length; i++){
            var tabs = document.getElementById('tb').innerHTML;
            tabs = tabs + playListNames[i].toString;
        }
}  