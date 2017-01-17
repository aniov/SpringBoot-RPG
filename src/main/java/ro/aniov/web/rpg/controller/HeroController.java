/**
 * Created by Marius on 12/14/2016.
 */
package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.aniov.web.rpg.dto.HeroDTO;
import ro.aniov.web.rpg.service.HeroService;

import javax.validation.Valid;

@Controller
public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping(value = "/new_hero")
    public String createNewHero(Model model, HeroDTO heroDTO) {

        model.addAttribute("heroDTO", heroDTO);
        return "new_hero";
    }

    @PostMapping(value = "/new_hero")
    public String createNewHero(@ModelAttribute(value = "heroDTO") @Valid HeroDTO heroDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "new_hero";
        }
        heroService.createNewHero(heroDTO);
        return "redirect:/member";
    }

    @DeleteMapping(value = "/delete_hero/{id}")
    @ResponseBody
    public ResponseEntity deleteHero(@PathVariable("id") Long id) {

        try {
            heroService.deleteHero(id);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/edit_heroname")
    @ResponseBody
    public ResponseEntity editHeroName(@RequestParam(value = "id", required = true) Long id,
                                        @RequestParam(value = "name", required = true) String name) {
        try {
            heroService.editName(id, name);

        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}