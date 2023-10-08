package org.example.bootstrap;

import net.datafaker.Faker;
import org.example.dto.ProjectDTO;
import org.example.dto.RoleDTO;
import org.example.dto.TaskDTO;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.enums.Status;
import org.example.service.ProjectService;
import org.example.service.RoleService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Component
public class DataGenerator implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final Faker faker;

    public DataGenerator(UserService userService, RoleService roleService, ProjectService projectService, TaskService taskService, Faker faker) {
        this.userService = userService;
        this.roleService = roleService;
        this.projectService = projectService;
        this.taskService = taskService;
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

        // Create an admin and a manager manually.
        UserDTO manager = new UserDTO("John", "Kelly",
                "johnkelly@example.com", "Abc1", "7459684542", roleService.findAll().get(1), Gender.MALE, true);
        UserDTO admin = new UserDTO("Josh", "Brown",
                "johsbrown@example.com", "Abc1", "7459684549", roleService.findAll().get(0), Gender.MALE, true);

        users.put(manager.getUserName(), manager);
        users.put(admin.getUserName(), admin);

        IntStream.rangeClosed(0, 10).forEach(i -> {
            String username = faker.internet().emailAddress();
            users.put(username, new UserDTO(
                            faker.name().firstName(),
                            faker.name().lastName(),
                            username,
                            faker.internet().password(),
                            faker.phoneNumber().cellPhone(),
                            // Create only users.
                            roleService.findAll().get(2),
                            Gender.values()[random.nextInt(0, Gender.values().length)], true)
                    );
                }
        );


        userService.saveAll(users);

        Map<String, ProjectDTO> initialProjects = Map.of(
                "PR001", new ProjectDTO("Spring MVC", "PR001", manager, LocalDate.now(), LocalDate.now().plusDays(25), "Creating Controllers", Status.OPEN),
                "PR002", new ProjectDTO("Spring ORM", "PR002", manager, LocalDate.now(), LocalDate.now().plusDays(10), "Creating Database", Status.IN_PROGRESS),
                "PR003", new ProjectDTO("Spring Container", "PR003", manager, LocalDate.now(), LocalDate.now().plusDays(32), "Creating Container", Status.IN_PROGRESS)
        );

        projectService.saveAll(initialProjects);

        // Get all project IDs.
        List<String> projectIDs = projectService.findAll().stream()
                .map(ProjectDTO::getProjectCode)
                .toList();

        // Get all usernames.
        List<String> userNames = userService.findAll().stream()
                .map(UserDTO::getUserName)
                .toList();

        Map<Long, TaskDTO> initialTasks = new HashMap<>();

        // User project IDs and usernames to create tasks.
        IntStream.rangeClosed(0, 20).forEach(i -> {
            Long id = UUID.randomUUID().getMostSignificantBits();
            initialTasks.put(
                    id,
                    new TaskDTO(
                            id,
                            projectService.findById(projectIDs.get(random.nextInt(0, projectIDs.size()))),
                            userService.findById(userNames.get(random.nextInt(0, users.size() - 1))),
                            faker.lorem().fixedString(40),
                            faker.lorem().fixedString(100),
                            Status.values()[random.nextInt(0, Status.values().length)],
                            LocalDate.now()
                    ));
        });

        taskService.saveAll(initialTasks);

    }
}
