import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SalleDAOTest {

    private SalleDAO salleDAO;
    private Connection connection;

    @BeforeAll
    void setupDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/EM"; 
        String username = "root"; 
        String password = ""; 
        connection = DriverManager.getConnection(url, username, password);
        salleDAO = new SalleDAO();
    }

    @BeforeEach
    void resetDatabase() throws SQLException {
        connection.createStatement().execute("DELETE FROM salles");
        connection.createStatement().execute(
            "INSERT INTO salles (id, name, capacity, location) VALUES " +
            "(1, 'Test Room', 50, 'Building A')"
        );
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close(); 
    }

    @Test
    void testAddSalle() {
        Salle salle = new Salle(0, "New Room", 100, "Building B");
        salleDAO.add(salle);

        List<Salle> salles = salleDAO.getAll();
        assertEquals(2, salles.size()); 
        assertTrue(salles.stream().anyMatch(s -> s.getName().equals("New Room")));
    }

    @Test
    void testGetSalleById() {
        Salle salle = salleDAO.get(1);
        assertNotNull(salle);
        assertEquals("Test Room", salle.getName());
        assertEquals(50, salle.getCapacity());
        assertEquals("Building A", salle.getLocation());
    }

    @Test
    void testUpdateSalle() {
        Salle updatedSalle = new Salle(1, "Updated Room", 75, "Building C");
        salleDAO.update(updatedSalle);

        Salle salle = salleDAO.get(1);
        assertNotNull(salle);
        assertEquals("Updated Room", salle.getName());
        assertEquals(75, salle.getCapacity());
        assertEquals("Building C", salle.getLocation());
    }

    @Test
    void testDeleteSalle() {
        salleDAO.delete(1);

        Salle salle = salleDAO.get(1);
        assertNull(salle); 
    }

    @Test
    void testGetAllSalles() {
        List<Salle> salles = salleDAO.getAll();
        assertEquals(1, salles.size());
        Salle salle = salles.get(0);
        assertEquals("Test Room", salle.getName());
        assertEquals(50, salle.getCapacity());
        assertEquals("Building A", salle.getLocation());
    }
}
