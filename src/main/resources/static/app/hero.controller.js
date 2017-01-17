/**
 * Created by Marius on 12/16/2016.
 */
(function () {
    'use strict';

    angular
        .module('app', [])
        .controller('HeroController', HeroController);

    HeroController.$inject = ['$http', '$window'];

    function HeroController() {

        var vm = this;

        vm.heroes = [];
        vm.deleteHero = deleteHero;

    }

    function deleteHero(id) {
        var url = "/delete_hero/" + id; //path variable
        $http.delete(url)
            .success(function (response, status) {
                alert(status);
                vm.heroes = response.data;
                $window.location.href = "/members";

            }).error(function (response, status) {
            alert("Error deleting, code: " + status)

        });
    }
})();