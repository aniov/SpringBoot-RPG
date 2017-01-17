/**
 * Created by Marius on 12/18/2016.
 */

/**
 * Using 'SweetAlert2 JS' for the pop-up alert
 * jQuery for sending a 'delete' request to 'deleteUserHero' Controller implemented on the back-end
 * Refreshing the page
 */

function confirmHeroDelete(id) {

    swal({
            title: 'Are you sure?',
            text: 'You will not be able to recover this hero!',
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, I am sure!',
            cancelButtonText: 'No, cancel it!',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',

        }).then(function() {
                console.log('You cliked Ok');
                $.ajax({
                url: '/delete_hero/' + id,
                type: 'DELETE',
                beforeSend: function (xhr) {
                xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
                },
                success: function (response) {
                    console.log('Deleted' + response);
                    swal({
                        title: 'Deleted!',
                        text: 'Your hero has been deleted.',
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
    }, function (dismiss){
             if (dismiss == 'cancel') {
                console.log('You clicked cancel');
                 swal({
                     title: 'Cancelled!',
                     text: 'Your hero is safe :)',
                     type: 'error',
                     confirmButtonClass: 'btn btn-info'
                 })
            }
        });
}