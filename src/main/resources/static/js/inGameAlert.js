/**
 * Created by Marius on 12/22/2016.
 */

function heroDiedAlert() {

    swal({
        title: 'You died!',
        text: 'Return to Member area',
        type: 'warning',
        confirmButtonClass: 'btn btn-info',
        allowOutsideClick: false
    }).then(function () {
        window.location.replace("/member");
    });

}

function levelUpAlert(level) {

    swal({
        title: 'You are a real Fighter!',
        text: 'You are now level ' + level,
        type: 'success',
        allowOutsideClick: false
    })

}

function gameWonAlert() {

    swal({
        title: 'You won on this MAP !!!',
        text: 'Return to Member area and continue the Adventure',
        type: 'success',
        confirmButtonClass: 'btn btn-success',
        allowOutsideClick: false
    }).then(function () {
        window.location.replace("/member");
    });

}
