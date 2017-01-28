/**
 * Created by Marius on 12/18/2016.
 */

/**
 * Using 'SweetAlert2 JS' for the pop-up alert
 * jQuery for sending a 'put/delete' request to admin Controller's
 * Refreshing the page
 */

function disableAccount(id) {

    swal({
        title: 'Are you sure?',
        text: 'Disable / Enable account',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, I am sure!',
        cancelButtonText: 'No, cancel it!',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',

    }).then(function () {
        $.ajax({
            url: '/disable_account/' + id,
            type: 'PUT',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
            },
            success: function () {
                swal({
                    title: 'Account status changed',
                    type: 'success',
                    confirmButtonClass: 'btn btn-info',
                    allowOutsideClick: false
                }).then(function () {
                    window.location.reload();
                })
            },
            error: function () {
                sweetAlert('Oops...', 'Something went wrong!', 'error');
            }
        });
    });
}

function expireAccount(id) {

    swal({
        title: 'Are you sure?',
        text: 'Expired / Non-Expired account',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, I am sure!',
        cancelButtonText: 'No, cancel it!',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',

    }).then(function () {
        $.ajax({
            url: '/expire_account/' + id,
            type: 'PUT',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
            },
            success: function () {
                swal({
                    title: 'Account expire status changed',
                    type: 'success',
                    confirmButtonClass: 'btn btn-info',
                    allowOutsideClick: false
                }).then(function () {
                    window.location.reload();
                })
            },
            error: function () {
                sweetAlert('Oops...', 'Something went wrong!', 'error');
            }
        });
    });
}

function lockAccount(id) {

    swal({
        title: 'Are you sure?',
        text: 'Lock / Un-lock account',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, I am sure!',
        cancelButtonText: 'No, cancel it!',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',

    }).then(function () {
        $.ajax({
            url: '/lock_account/' + id,
            type: 'PUT',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
            },
            success: function () {
                swal({
                    title: 'Account lock status changed',
                    type: 'success',
                    confirmButtonClass: 'btn btn-info',
                    allowOutsideClick: false
                }).then(function () {
                    window.location.reload();
                })
            },
            error: function () {
                sweetAlert('Oops...', 'Something went wrong!', 'error');
            }
        });
    });
}

function deleteAccount(id) {

    swal({
        title: 'Are you sure?',
        text: 'Delete account',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, I am sure!',
        cancelButtonText: 'No, cancel it!',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',

    }).then(function () {
        $.ajax({
            url: '/delete_account/' + id,
            type: 'DELETE',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
            },
            success: function () {
                swal({
                    title: 'Account deleted',
                    type: 'success',
                    confirmButtonClass: 'btn btn-info',
                    allowOutsideClick: false
                }).then(function () {
                    window.location.reload();
                })
            },
            error: function () {
                sweetAlert('Oops...', 'Something went wrong!', 'error');
            }
        });
    });
}