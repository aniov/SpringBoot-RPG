package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ro.aniov.web.rpg.dto.AccountDTO;
import ro.aniov.web.rpg.service.AccountService;

import javax.validation.Valid;

/**
 * Created by Marius on 12/9/2016.
 */
@Controller
public class RegisterController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/register")
    public String sendRegisterForm(Model model, AccountDTO accountDTO){

        model.addAttribute("accountDTO", accountDTO);
        return "register";
    }

    @PostMapping("/register")
    public String receiveRegisterForm(Model model, @ModelAttribute(value = "accountDTO") @Valid AccountDTO accountDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "register";
        }
        if (accountService.findAccountByEmail(accountDTO.getEmail()) != null){
            model.addAttribute("There is an account with that email adress: " +  accountDTO.getEmail());
            return "register";
        }
        accountService.registerAccount(accountDTO);
        return "redirect:/";

    }
}
