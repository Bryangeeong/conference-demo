package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    // LIST ALL
    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    // GET
    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session) {
        // You can save an object, but it won't get committed
        // to the database until you flush it.
        return sessionRepository.saveAndFlush(session);
    }

    // This function header also requires the DELETE HTTP request
    // in the form of "method ="
    // Spring doesn't have a @DeleteMapping
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //Also need to check for children records before deleting.
        sessionRepository.deleteById(id);
    }

    // Requiring the id on the URL, similar to delete and
    // requiring the HTTP verb PUT similar to the delete
    // Note on PUT vs PATCH
    // PUT will replace all of the attributes of an entry
    // PATCH will replace only what is passed in
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Session existingSession = sessionRepository.getOne(id);
        // The third field in the below function allows us to ignore
        // a passed in attribute that we don't want overwritten.
        // In this case, session_id because it's our primary key and it'll
        // copy over null if we don't ignore it.
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
