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

function heroRunFailed(won, levelUp, level) {

    swal({
        title: 'Seems you are Not So lucky',
        text: 'You have to fight...Ha ha ha...',
        type: 'warning',
        allowOutsideClick: false
    }).then (function (){
        if (won){
            heroWonFight(levelUp, level);
        }
        else {
            heroDiedAlert();
        }
    });
}

function heroWonFight(levelUp, level) {

    swal({
        title: 'You won the fight',
        text: 'those moves you made...impressive',
        type: 'success',
        allowOutsideClick: false
    }).then (function (){
        if (levelUp){
            levelUpAlert(level);
        }
    });
}
