$('#content').summernote({
    placeholder: '내용을 입력해 주세요',
    tabsize: 2,
    height: 400,
    lang: "ko-KR",
    toolbar: [
        ['style', ['style']],
        ['font', ['bold', 'underline', 'clear']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'video']],
        ['view', ['fullscreen', 'codeview', 'help']]
    ]
});

const saveBtn = document.getElementById("save-btn")
saveBtn.addEventListener('click', function() {
    let data = {
        title: $("#title").val(),
        content: $("#content").val()
    };
    $.ajax({
        type: "POST",
        url: "/bbs/add",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            alert("작성 완료");
            location.href = "/bbs";
        },
    }).fail(function(error) {
        alert("오류가 발생했습니다.");
        console.log(JSON.stringify(error));
    });
})