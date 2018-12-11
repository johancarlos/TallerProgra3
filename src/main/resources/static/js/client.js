var redditRequest = new XMLHttpRequest();
var url = 'http://localhost:8090/';
function reportPost(postId, userId) {

    $.post(url + "post/reportpost",
        {
            post: postId,
            user: userId
        },
        function (data, status) {
            console.log("Data : " + data + " Status : " + status);
        })
}
function addEventsToUserHomePage(){
    $(".report-button").on("click", changeFromReportToReported)
}
function changeFromReportToReported(){
    $(this).removeAttr("th:onclick");
    $(this).removeAttr("onclick");
    $(this).html("<span class=\"spot\"></span><b>REPORTED</b>")
}

function hidePost(postId, userId){;
    $.post(url + "post/hidepost",
        {
            post: postId,
            user: userId
        },
        function (data, status) {
            $("#" + postId).hide();
            console.log("Data : " + data + " Status : " + status);
        })
}

function like(e, b){
   // th:href="${'/postlike/' + post.id}"
   console.log(e);
   var a = $("#karma");
   console.log(a.text(Number(a.text()) + 1));
   ust = url + '/postlike-rest/ + e';
   console.log(ust);
   $.get( url + 'postlike-rest/' + e, function( data ) {
   });
}

function like1(e, b){
   // th:href="${'/postlike/' + post.id}"
   console.log(e);
   var a = $("#karma1");
   console.log(a.text(Number(a.text()) + 1));
   ust = url + '/postlike-rest/ + e';
   console.log(ust);
   $.get( url + 'postlike-rest/' + e, function( data ) {
   });
}


function unlike(e, b){
   // th:href="${'/postlike/' + post.id}"
   console.log(e);
   var a = $("#karma");
   console.log(a.text(Number(a.text()) - 1));

   $.get( url + 'postunlike-rest/' + e, function( data ) {
   });
}

function unlike1(e, b){
   // th:href="${'/postlike/' + post.id}"
   console.log(e);
   var a = $("#karma1");
   console.log(a.text(Number(a.text()) - 1));

   $.get( url + 'postunlike-rest/' + e, function( data ) {
   });
}
