# Lab 3.1 Questões

### a) Identify a couple of examples that use AssertJ expressive methods chaining.
````java

void whenFindEmployedByExistingId_thenReturnEmployee() {
        Employee emp = new Employee("test", "test@deti.com");
        entityManager.persistAndFlush(emp);

        Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
    }
void whenInvalidId_thenReturnNull() {
    Employee fromDb = employeeRepository.findById(-111L).orElse(null);
    assertThat(fromDb).isNull();
}

void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
    Employee alex = new Employee("alex", "alex@deti.com");
    Employee ron = new Employee("ron", "ron@deti.com");
    Employee bob = new Employee("bob", "bob@deti.com");

    entityManager.persist(alex);
    entityManager.persist(bob);
    entityManager.persist(ron);
    entityManager.flush();

    List<Employee> allEmployees = employeeRepository.findAll();
    assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
}

````


### b) Take note of transitive annotations included in @DataJpaTest.

````java
    @Test
    void whenFindEmployedByExistingId_thenReturnEmployee() {
        Employee emp = new Employee("test", "test@deti.com");
        entityManager.persistAndFlush(emp);

        Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
    }

    @Test
    void given3Employees_whengetAll_thenReturn3Records() {
        Employee alex = new Employee("alex", "alex@deti.ua.pt");
        Employee john = new Employee("john", "john@deti.ua.pt");
        Employee bob = new Employee("bob", "bob@deti.ua.pt");
    
        List<Employee> allEmployees = employeeService.getAllEmployees();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
    }

    @Test
    void whenNonExistingName_thenEmployeeShouldNotExist() {
        boolean doesEmployeeExist = employeeService.exists("some_name");
        assertThat(doesEmployeeExist).isFalse();
        verifyFindByNameIsCalledOnce("some_name");
    }
````

### c) Identify an example in which you mock the behavior of the repository (and avoid involving a database).  
In the B_EmployeeService_UnitTest we mock the behavior of the repository
````java

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
````


### d) What is the difference between standard @Mock and @MockBean?

is a Spring Boot annotation that not only creates a mock object but also adds it to the Spring application context. This means that beans or components in the Spring context will use the mock object instead of the actual bean, making it suitable for integration testing.

### e) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
The main purpose of the "application-integrationtest.properties" is to configure the application test environment 


### f) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

Unit Tests (C) - Test individual components or methods without loading the full application context. Typically use annotations like @Mock and @ExtendWith(MockitoExtension.class).
Integration Tests (D) - Test the interaction between multiple components, usually by loading the full Spring context with annotations like @SpringBootTest. These tests verify the end-to-end behavior of the application.
End-to-End (E2E) Tests (E) - Test the entire system, including making real HTTP calls to the running application. These tests use tools like RestTemplate or WebTestClient to perform API calls and check responses.