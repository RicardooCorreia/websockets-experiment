let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    const $username = $("#username");
    $username.prop("disabled", connected);
    if (!connected) {
        $username.val("")
    }
}


function login() {

    let userSid = $("#username").val()
    connect(userSid)
}

function connect(username) {

    const socket = new SockJS('/test-application-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/notifications' + '-user' + username, function(notification) {
            console.log(JSON.parse(notification.body))
            showNotification(JSON.parse(notification.body).content);
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

function showNotification(message) {
    $("#notifications").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });
    $("#connect").click(function() {
        login();
    });
    $("#disconnect").click(function() {
        disconnect();
    });
});

