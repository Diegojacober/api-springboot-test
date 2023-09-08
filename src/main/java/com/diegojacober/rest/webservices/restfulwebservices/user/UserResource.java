package com.diegojacober.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

    private UserDAOService service;

    public UserResource(UserDAOService service) {
        this.service = service;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = service.findAll();
        for (User u: users) {
            int userId = u.getId();
            Link selfLink = linkTo(User.class).slash(userId).withSelfRel();
            u.add(selfLink);
        }
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<EntityModel<User>> findAll(@PathVariable int id) {
        User user = service.findOne(id);

        if (user != null) {
            EntityModel<User> entityModel = EntityModel.of(user);
            WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAll());
            entityModel.add(link.withRel("all-users"));
            return ResponseEntity.ok().body(entityModel);
        }

        throw new UserNotFoundException("User not found id " + id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUSer(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
