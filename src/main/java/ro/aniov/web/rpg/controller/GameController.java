/**
 * Created by Marius on 12/20/2016.
 */
package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ro.aniov.web.rpg.dto.HeroControlDTO;
import ro.aniov.web.rpg.dto.HeroIdDTO;
import ro.aniov.web.rpg.dto.HeroPlayDTO;
import ro.aniov.web.rpg.service.HeroService;
import ro.aniov.web.rpg.service.gamePlay.GameMap;
import ro.aniov.web.rpg.service.gamePlay.GamePlay;

import javax.servlet.http.HttpSession;


@Controller
public class GameController {

    @Autowired
    private HeroService heroService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    GamePlay gamePlay;

    private GameMap gameMap;

    private HeroIdDTO heroIdDTO;

    private HeroPlayDTO heroPlayDTO;

    /** We get 'heroIdDTO from the 'Session'
     *  Initialize the 'gameMap', 'heroPlayDTO' and add them to the model
     * */
    @GetMapping(value = "/game_play")
    public String setGame(HeroControlDTO heroControlDTO, Model model){

        /** If User was afk and the session expire, we redirect him to '/member' page */
        if ((heroIdDTO = (HeroIdDTO) httpSession.getAttribute("heroIdDTO")) == null)
            return "redirect:/member";

        /** If the hero doesn't belong to the User we redirect to Login page*/
        if (!heroService.isHeroIdBoundToAuthorizedUser(heroIdDTO.getHeroId()))
            return "redirect:/login";

        gameMap = (GameMap) httpSession.getAttribute("gameMap");
        heroPlayDTO = (HeroPlayDTO) httpSession.getAttribute("heroPlayDTO");

        //Hero hero = heroService.getHeroById(heroIdDTO.getHeroId());
        //HeroPlayDTO heroPlayDTO = new HeroPlayDTO(hero);

        model.addAttribute("gameMap", gameMap);
        model.addAttribute("heroPlayDTO", heroPlayDTO);
        model.addAttribute("heroControlDTO", heroControlDTO);
        return "game_play";
    }

    @PutMapping(value = "/game_play")
    public String playGame(@ModelAttribute HeroControlDTO heroControlDTO){

        gamePlay.play(heroControlDTO.getResponse());
        return "redirect:game_play";
    }

    /** We put 'heroIdDTO' model in Session , the front-end will redirect to '/game_play' */
    @PostMapping("/ini_game")
    public HttpEntity initializeGame(@ModelAttribute HeroIdDTO heroIdDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        iniGameMap(heroIdDTO);
        heroPlayDTO = new HeroPlayDTO(heroService.getHeroById(heroIdDTO.getHeroId()));

        httpSession.setAttribute("heroIdDTO", heroIdDTO);
        httpSession.setAttribute("heroPlayDTO", heroPlayDTO);
        httpSession.setAttribute("gameMap", gameMap);

        return new ResponseEntity(HttpStatus.OK);
    }

    /** Create a new 'gameMap' only if it doesn't exist for the current hero*/
    private void iniGameMap(HeroIdDTO heroIdDTO){
        int level = heroService.getHeroLevel(heroIdDTO.getHeroId());

        /** Keeping the user game UP even if he's starting new games, or log's out  !!!
        if( !(gameMap instanceof GameMap)){
            gameMap = new GameMap(level, heroIdDTO.getHeroId());
        }
        else {
            Set<Long> heroIds = GameMap.hId;
            if ( !heroIds.contains(heroIdDTO.getHeroId())) {
                System.out.println("Contains heroId: " + heroIds.contains(heroIdDTO.getHeroId()));
                gameMap = new GameMap(level, heroIdDTO.getHeroId());
            }
        }
        for (GameMap instance : GameMap.gameMapsInstances){
            if (instance.getHeroId().equals(heroIdDTO.getHeroId())){
                gameMap = instance;
            }
        }*/

        /** Creating a new GameMap every time he enter's GamePlay mode*/
        gameMap = new GameMap(level, heroIdDTO.getHeroId());
    }

}
