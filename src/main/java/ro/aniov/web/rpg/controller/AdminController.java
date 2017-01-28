package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.aniov.web.rpg.service.AccountService;

/**
 * Created by Marius on 1/28/2017.
 */
@Controller
public class AdminController {

    @Autowired
    AccountService accountService;

    @PutMapping(value = "/disable_account/{id}")
    @ResponseBody
    public ResponseEntity disableAccount(@PathVariable(name = "id") Long id){
        try {
            accountService.setEnableAccount(id);
        } catch (PermissionDeniedDataAccessException e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/expire_account/{id}")
    @ResponseBody
    public ResponseEntity expiredAccount(@PathVariable(name = "id") Long id){
        try {
            accountService.setExipredAccount(id);
        } catch (PermissionDeniedDataAccessException e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/lock_account/{id}")
    @ResponseBody
    public ResponseEntity lockAccount(@PathVariable(name = "id") Long id){
        try {
            accountService.setLockAccount(id);
        } catch (PermissionDeniedDataAccessException e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete_account/{id}")
    @ResponseBody
    public ResponseEntity deleteAccount(@PathVariable(name = "id") Long id){
        try {
            accountService.deleteAccount(id);
        } catch (PermissionDeniedDataAccessException | DataIntegrityViolationException e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/change_account_role")
    @ResponseBody
    public ResponseEntity roleAccount(@RequestParam("id") Long id, @RequestParam("role") String role){

        try {
            accountService.roleChange(id, role);
        } catch (PermissionDeniedDataAccessException e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
