package com.sti.assignment.controller;

import com.sti.assignment.model.Player;
import com.sti.assignment.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Simple spring player controller to  manage REST
 */
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    /* get list of players-GET/player-listar alla */
    @GetMapping
    public Iterable<Player> list(){ return playerRepository.findAll(); }
    /* List a single player with specified id -GET /player/id- listar EN*/
    @GetMapping
    @RequestMapping("{id}")
    public Player get(@PathVariable Integer id){
        return playerRepository.findById(id).get();
    }
    /*  create new player-POST /player- skapar ny */
    @PostMapping(consumes = "application/json",produces = "application/json")
    public Player create(@RequestBody Player player){
        return playerRepository.save(player);
    }
    /* delete a particular player based on id-DELETE /player/id - tar bort en */
    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        //Also need to check for children records before deleting.
        playerRepository.deleteById(id);
    }
    /* update a player- uppdaterar (replace)*/
    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    public Player update(@PathVariable Integer id, @RequestBody Player updatedPlayer){
        //PUT- all attributes to be passed in. a PATCH no a m
        //Todo: Add validation to all attributes are passed in, else return a 404 bad payload
        Player player=playerRepository.findById(id).get();
        //BeanUtils.copyProperties(updatedPlayer,player,"id");
        player.setName(updatedPlayer.getName());
        player.setAge(updatedPlayer.getAge());
        player.setJerseyNumber(updatedPlayer.getJerseyNumber());
        player.setCity(updatedPlayer.getCity());
        return playerRepository.save(player);
    }
}
