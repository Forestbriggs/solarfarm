# SolarFarm

SolarFarm is a Java-based console application designed to manage solar panel installations for a solar farm. The application allows users to view, add, update, and delete solar panels efficiently while demonstrating principles of object-oriented programming (OOP).

---

### Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [How It Works](#how-it-works)
- [Setup](#setup)
- [Usage](#usage)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)
- [License](#license)

---

### Features

- **View Panels by Section**: Retrieve a list of all panels in a specific section, grouped for easy identification.
- **Add Panels**: Add new solar panels with attributes like section, row, column, installation year, material, and tracking status.
- **Update Panels**: Modify details of existing panels, ensuring the farm data stays up-to-date.
- **Delete Panels**: Remove panels from the system when no longer in use or incorrectly entered.
- **Validation**: Enforces constraints like valid installation years, unique panel placements, and correct material types.

---

### Technologies

- **Java**: Core language for implementing the application.
- **Spring Framework**: Manages dependencies through XML-based configuration.
- **JUnit**: Enables unit testing for application functionality.
- **File I/O**: Stores and retrieves panel data from a file for persistence.

---

### How It Works

1. **Controller**: Manages the application's flow, directing user input to the appropriate service methods.
2. **Service Layer**: Contains business logic to validate, add, update, or delete panels.
3. **Repository**: Handles file-based storage of solar panel data, with support for serialization and deserialization.
4. **Models**: Encapsulate solar panel properties and material types, ensuring a clear structure.
5. **View**: Provides a user-friendly console interface to interact with the system.

---

### Setup

### Prerequisites

- **Java JDK** (17 or later)
- A terminal or IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository.
    ```bash
    git clone https://github.com/Forestbriggs/solarfarm.git
    cd solarfarm
    ```
2. Build the project.
    ```bash
    javac -d bin $(find src -name "*.java")
    ```
3. Run the application.
    ```bash
    java -cp bin learn.solar.App
    ```

---

### Usage

Upon starting the application, navigate the menu with the following options:

1. **View Panels by Section**  
   Enter the section name to view all panels within that section.

2. **Add a Panel**  
   Input details like section, row, column, installation year, material type (e.g., `mono-Si`, `multi-Si`), and tracking status.

3. **Update a Panel**  
   Locate the panel by its section and coordinates, then update the desired attributes.

4. **Delete a Panel**  
   Locate and confirm the removal of a panel by its section and coordinates.

5. **Exit**  
   End the application session.

---

### Testing

Unit tests were implemented using **JUnit** to ensure correctness and stability of the system. The test suite covers:

1. **Service Layer Tests**:
   - Validates constraints such as valid section names, installation year, and unique panel coordinates.
   - Tests error handling for invalid inputs like out-of-range rows or columns.

2. **Repository Tests**:
   - Ensures panels can be correctly added, updated, and deleted in the file-based storage system.
   - Tests the integrity of deserialization logic for reading data from files.

3. **Mock Repository (PanelRepositoryDouble)**:
   - Used for isolated tests in the service layer, providing controlled scenarios without reliance on file-based storage.

### Running Tests
1. **Build and run all tests**:
    ```bash
    javac -d bin -cp lib/junit-5.jar $(find src -name "*.java" -or -name "*.test")
    java -cp bin:lib/junit-5.jar org.junit.runner.JUnitCore learn.solar.domain.PanelServiceTest
    ```
2. **View results directly in your IDE's test runner if using IntelliJ IDEA or Eclipse**:


---

### Future Enhancements

- **Database Integration**: Replace file-based storage with a database for scalability.
- **Material Analysis**: Add reporting features to summarize materials by type and usage.
- **Improved Validation**: Expand validation rules for more robust error handling.

---

### License

This project is licensed under the MIT License. Feel free to use, modify, and distribute the application as needed.