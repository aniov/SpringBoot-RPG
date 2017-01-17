/**
 * Created by Marius on 12/22/2016.
 */

function startGame(id) {

    $.ajax({
        url: '/ini_game',
        data: {heroId: id},
        type: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('X-CSRF-Token', $('meta[name="_csrf"]').attr('content'))
        },
        success: function (response) {
            console.log('Game initialized' + response);
            window.location.replace("/game_play");
        },
        error: function () {
            sweetAlert('Oops...', 'Something went wrong!', 'error');
        }
    });

}
