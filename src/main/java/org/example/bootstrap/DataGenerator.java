package org.example.bootstrap;

import net.datafaker.Faker;
import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class DataGenerator implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final Faker faker;

    public DataGenerator(UserService userService, RoleService roleService, Faker faker) {
        this.userService = userService;
        this.roleService = roleService;
        this.faker = faker;
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, UserDTO> users = new HashMap<>();

        Random random = new Random();

        Map<Long, RoleDTO> roles = Map.of(
                1L, new RoleDTO(1L, "Admin"),
                2L, new RoleDTO(2L, "Manager"),
                3L, new RoleDTO(3L, "User")
        );

        roleService.saveAll(roles);

        IntStream.rangeClosed(0, 10).forEach(i -> {
                    String username = faker.internet().emailAddress();
                    users.put(username, new UserDTO(
                            faker.name().firstName(),
                            faker.name().lastName(),
                            username,
                            faker.internet().password(),
                            faker.phoneNumber().cellPhone(),
                            roleService.findAll().get(random.nextInt(0, roleService.findAll().size())),
                            Gender.values()[random.nextInt(0, Gender.values().length)], true)
                    );
                }
        );

        userService.saveAll(users);
    }
}
