// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://5b7elq1ly4.execute-api.us-east-2.amazonaws.com/alpha"; 

var list_segments   = base_url + "listsegments";    // GET
var list_playlists  = base_url + "listplaylists";    // GET

// generated from aws. BAD IDEA to encode here, but just getting something done.
// var remote_url = "https://rct351a2w3.execute-api.us-east-1.amazonaws.com/secure/";

// var search_remote_url = remote_url + "remote";

// var apikey = "OJJuRbpDPX7pJdLDOlDwR1SwyOryDpfa5ChT6ZRs";

// test directly 
// curl -X POST -H "x-api-key: theKey" -H "Content-Type: application/json" -d '{"key":"val"}' https://[api-id].execute-api.[region].amazonaws.com
/**
 * Refresh constant list from server
 *
 *    GET list_segments
 *    RESPONSE  list of [id, character, line, url, availableRemote] constants 
 */
function refreshConstantsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_segments, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
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
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var segmentJson = js.list[i];
    console.log(segmentJson);
    
    var sid = segmentJson["id"];
    var scharacter = segmentJson["character"];
    var sline = segmentJson["line"];
    var surl = segmentJson["url"];
    var savailableRemote = segmentJson["availableRemote"];
    var sysvar = segmentJson["system"];
    if (sysvar) {
    	output = output + "<div id=\"seg" + sid + "\"><b>" + sid + ":</b> = " + scharacter + sline + surl + savailableRemote + "<br></div>";
    } else {
    	output = output + "<div id=\"seg" + sid + "\"><b>" + sid + ":</b> = " + scharacter + sline + surl + savailableRemote + "(<a href='javaScript:requestDelete(\"" + sid + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
    }
  }

  // Update computation result
  segmentList.innerHTML = output;
    return output;
}


