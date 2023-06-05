const replySave = document.getElementById('reply-save-btn');
replySave.addEventListener('click', function () {
    let id =Number($("#articleId").val())
    let content = document.getElementById("content").value;
    let data = {
        articleId: id,
        content:content
    }
    $.ajax({
        type: "POST",
        url: "/bbs/comment/create",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            alert("작성 완료");
            location.href = '/bbs/read/' + id;

        },
    }).fail(function (error) {
        alert("오류가 발생했습니다.");
        console.log(JSON.stringify(error));
    });
})

console.log()