const userUpdate = document.getElementById('user-update-btn')
userUpdate.addEventListener('click', function() {
    let password = document.getElementById('password').value;
    let newPassword = document.getElementById('newPassword').value;
    let checkPassword = document.getElementById('checkPassword').value;
    let tel = document.getElementById('tel').value;
    let address = document.getElementById('address').value;
    let data = {
        password: password,
        newPassword: newPassword,
        checkPassword: checkPassword,
        tel: tel,
        address: address
    }
    $.ajax({
        type: "POST",
        url: "/update",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            if (response === "success") {
                alert("수정되었습니다.");
                location.href = '/homepage';
            } else if (response === "Password error") {
                alert("비밀번호가 잘못되었습니다.");
            } else if (response === "newPassword error") {
                alert("새로운 비밀번호가 일치하지 않습니다.");
            } else {
                alert("오류가 발생했습니다.");
            }
        },
        error: function (error) {
            alert("예상치 못한 오류가 발생했습니다.");
            console.log(JSON.stringify(error));
        }
    });

})