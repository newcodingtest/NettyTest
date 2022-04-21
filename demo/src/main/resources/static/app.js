var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greeting1', function (greeting) {
        showGreeting1(JSON.parse(greeting.body));
        });
        
        stompClient.subscribe('/topic/recv', function (greeting) {
            showGreeting2(JSON.parse(greeting.body));
        });
        
        stompClient.subscribe('/topic/pending', function (greeting) {
            showGreeting3(JSON.parse(greeting.body));
        });
        
        stompClient.subscribe('/topic/heartbeat', function (greeting) {
            showGreeting4(greeting.body);
        });
        
        stompClient.subscribe('/topic/memory', function (greeting) {
            showMemory(JSON.parse(greeting.body));
        });
        
        stompClient.subscribe('/topic/cpu', function (greeting) {
            showCpu(JSON.parse(greeting.body));
        });
        
        stompClient.subscribe('/topic/disk', function (greeting) {
            showDisk(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello1", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/app/hello2", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/app/hello3", {}, JSON.stringify({'name': $("#name").val()}));
      stompClient.send("/app/hello4", {}, JSON.stringify({'name': $("#name").val()}));
      stompClient.send("/app/memory", {}, JSON.stringify({'name': $("#name").val()}));
      stompClient.send("/app/cpu", {}, JSON.stringify({'name': $("#name").val()}));
      stompClient.send("/app/disk", {}, JSON.stringify({'name': $("#name").val()}));
  
}

function heartBeat(){
	  stompClient.send("/app/hello4", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting1(message) {
	var test = message;
	$("#greeting1").html('');
	$("#greeting1Cnt").html('');
	for(var i = 0; i<test.length; i++){
   	 	$("#greeting1").append("<tr><td>" +test[i].data + "</td></tr>");
	}
	$("#greeting1Cnt").append(test.length);
}
function showGreeting2(message) {
	var test = message;
	$("#greeting2").html('');
	$("#greeting2Cnt").html('');
	for(var i = 0; i<test.length; i++){
   	 	 	$("#greeting2").append("<tr><td>" +test[i].regdate + "</td>"+
   	 						"<td>" +test[i].data + "</td>"+
   	 						"<td>" +test[i].flag + "</td></tr>");
	}
	$("#greeting2Cnt").append(test.length);
}

function showGreeting3(message) {
	var test = message;
	$("#greeting3").html('');
	$("#greeting3Cnt").html('');
	for(var i = 0; i<test.length; i++){
   	 	$("#greeting3").append("<tr><td>" +test[i].regdate + "</td>"+
   	 						"<td>" +test[i].data + "</td>"+
   	 						"<td>" +test[i].reason + "</td></tr>");
   	
	}
	$("#greeting3Cnt").append(test.length);
}

//ConcurrentHashMap<String,String>
function showGreeting4(message) {
var test= JSON.parse(message);
 console.log("Interface Network: " + test);
 console.log(test[8081]);
 if(test[8081]!=null &&test[8081]!='undefined'&&test[8081]=='T' ){
 	 document.getElementById("sstatus").innerHTML='T';
 }else{
 	document.getElementById("sstatus").innerHTML='F';
 }
 
  if(test[8082]!=null &&test[8082]!='undefined'&&test[8082]=='T' ){
 	 document.getElementById("pstatus").innerHTML='T';
 }else{
 	document.getElementById("pstatus").innerHTML='F';
 }
}

function showMemory(message) {
	console.log("Memory: "+message);
}

function showCpu(message) {
	console.log("Cpu: "+message);
}

function showDisk(message) {
	var test = JSON.parse(message);
	console.log("Disks1: "+test.totalSize.length);
	console.log("Disks2: "+message[0]);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
 
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() {    
    setInterval(sendName,3000);
     });
    
     

});