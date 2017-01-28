/**
 * Created by Marius on 12/18/2016.
 */

/**
 * Using 'SweetAlert2 JS' for the pop-up alert
 * jQuery for sending a 'put' request to 'editHeroName' Controller implemented on the back-end
 * Refreshing the page
 */

function editProfile(id) {

    swal({
        title: 'Edit your Profile',
        html:
            '<input id="swal-firstname" placeholder="first name" class="swal2-input" autofocus>' +
            '<input id="swal-lastname" placeholder="last name" class="swal2-input">' +
            '<select id="sexType" class="swal2-input">' +
                '<option value="UNKNOWN">Unknown</option>' +
                '<option value="MALE">Male</option>' +
                '<option value="FEMALE">Female</option>' +
            '</select>',
        inputPlaceholder: 'Select your sex',
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        allowOutsideClick: false,
        preConfirm: function () {
            return new Promise(function (resolve, reject) {
                    var firstName = $('#swal-firstname').val();
                    var lastName = $('#swal-lastname').val();
                    var sex = $('#sexType').val();
                    if (firstName === '' || lastName === '') {
                        reject('You need to insert a name');
                    } else if ( ! /^[a-zA-Z0-9]*$/.test(firstName) || ! /^[a-zA-Z0-9]*$/.test(lastName)){
                        reject('Only characters and numbers are allowed');
                    } else if (firstName.length < 3 || firstName.length > 30 || lastName.length < 3 || lastName.length > 30){
                        reject('Name should be between 3 and 30 characters long');
                    } else {
                        resolve();
                            $.ajax({
                                url: '/profile/edit',
                                data: {id: id, firstName: firstName, lastName: lastName, sex: sex},
                                type: 'PUT',
                                beforeSend: function (xhr) {
                                    xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
                                },
                                success: function (response) {
                                    console.log('Profile updated' + response);
                                },
                                error: function (response) {
                                    sweetAlert('Oops...', 'Something went wrong!', 'error');
                                    console.log('Error: ' + response);
                                }
                            });
                    }
            })
        },
    }).then(function () {
        swal({
            type: 'success',
            title: 'Your profile has been changed!',
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