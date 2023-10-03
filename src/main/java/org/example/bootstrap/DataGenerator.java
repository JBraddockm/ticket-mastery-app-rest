package org.example.bootstrap;

import net.datafaker.Faker;
import org.example.dto.ProjectDTO;
import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.enums.Status;
import org.example.service.ProjectService;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class DataGenerator implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final Faker faker;

    public DataGenerator(UserService userService, RoleService roleService, ProjectService projectService, Faker faker) {
        this.userService = userService;
        this.roleService = roleService;
        this.projectService = projectService;
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
//                            roleService.findAll().get(random.nextInt(0, roleService.findAll().size())),
                                    roleService.findAll().get(2),
                                    Gender.values()[random.nextInt(0, Gender.values().length)], true)
                    );
                }
        );

        UserDTO manager = new UserDTO("John", "Kelly",
                "johnkelly@example.com", "Abc1", "7459684542", roleService.findAll().get(1), Gender.MALE, true);
        UserDTO admin = new UserDTO("Josh", "Brown",
                "johsbrown@example.com", "Abc1", "7459684549", roleService.findAll().get(0), Gender.MALE, true);

        users.put(manager.getUserName(), manager);
        users.put(admin.getUserName(), admin);

        userService.saveAll(users);


        Map<String, ProjectDTO> initialProjects = Map.of(
                "PR001", new ProjectDTO("Spring MVC", "PR001", manager, LocalDate.now(), LocalDate.now().plusDays(25), "Creating Controllers", Status.OPEN),
                "PR002", new ProjectDTO("Spring ORM", "PR002", manager, LocalDate.now(), LocalDate.now().plusDays(10), "Creating Database", Status.IN_PROGRESS),
                "PR003", new ProjectDTO("Spring Container", "PR003", manager, LocalDate.now(), LocalDate.now().plusDays(32), "Creating Container", Status.IN_PROGRESS)
        );

        projectService.saveAll(initialProjects);

    }
}
