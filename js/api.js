// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://xao5j4tcal.execute-api.us-east-2.amazonaws.com/Alpha/"; 

var list_segments   = base_url + "listsegments";    // GET
var list_playlists  = base_url + "listplaylists";    // GET

// generated from aws. BAD IDEA to encode here, but just getting something done.
// var remote_url = "https://rct351a2w3.execute-api.us-east-1.amazonaws.com/secure/";

// var search_remote_url = remote_url + "remote";

// var apikey = "OJJuRbpDPX7pJdLDOlDwR1SwyOryDpfa5ChT6ZRs";

// test directly 
// curl -X POST -H "x-api-key: theKey" -H "Content-Type: application/json" -d '{"key":"val"}' https://[api-id].execute-api.[region].amazonaws.com