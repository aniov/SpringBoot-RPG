/**
 * Created by Marius on 12/18/2016.
 */

/**
 * Using 'SweetAlert2 JS' for the pop-up alert
 * jQuery for sending a 'put' request to 'editHeroName' Controller implemented on the back-end
 * Refreshing the page
 */

function changeHeroName(id, name) {

    swal({
        title: 'Edit the name of your Hero',
        input: 'text',
        inputPlaceholder: name,
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        allowOutsideClick: false,
        preConfirm: function (newName) {
            return new Promise(function (resolve, reject) {
                    if (newName === '') {
                        reject('You need to insert a name');
                    } else if ( ! /^[a-zA-Z0-9]*$/.test(newName)){
                        reject('Only characters and numbers are allowed');
                    } else if (newName.length < 3 || newName.length > 30){
                        reject('Name should be between 3 and 30 characters long');
                    } else {
                        resolve();
                            $.ajax({
                                url: '/edit_heroname',
                                data: {id: id, name: newName},
                                type: 'PUT',
                                beforeSend: function (xhr) {
                                    xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
                                },
                                success: function (response) {
                                    console.log('Hero Name updated' + response);
                                },
                                error: function () {
                                    sweetAlert('Oops...', 'Something went wrong!', 'error');
                                }
                            });
                    }
            })
        },
    }).then(function (newName) {
        swal({
            type: 'success',
            title: 'Your Hero name has been changed!',
            html: 'Hero\'s name has been changed: ' + newName
        }).then(function () {
            window.location.reload();
        })
    }, function (dismiss){
            if (dismiss == 'cancel') {
                console.log('You clicked cancel');
            }
        }
    );
}