package com.diegojacober.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {

    private static Set<User> users = new HashSet<>();

    static {
        users.add(new User(1, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(2, "João", LocalDate.now().minusYears(26)));
        users.add(new User(3, "Roberto", LocalDate.now().minusYears(45)));
    }

    public List<User> findAll() {
        return users.stream().collect(Collectors.toList());
    }

    public User save(User user) {
        user.setId(users.size() + 1);
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public void delete(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}
