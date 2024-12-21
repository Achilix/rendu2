
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventDAOTest {

    private EventDAO eventDAO;
    private Connection connection;

    @BeforeAll
    void setupDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/EM";
        String username = "root";
        String password = "";
        connection = DriverManager.getConnection(url, username, password);
        eventDAO = new EventDAO();
    }

    @BeforeEach
    void resetDatabase() throws SQLException {
        // Réinitialiser les données avant chaque test
        connection.createStatement().execute("DELETE FROM events");
        connection.createStatement().execute(
                "INSERT INTO events (id, name, description, date) VALUES " +
                "(1, 'Test Event', 'Description of test event', '2024-01-01')"
        );
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close(); 
    }

    @Test
    void testAddEvent() {
        Event event = new Event(0, "New Event", "Description of new event", new Date());
        eventDAO.add(event);

        List<Event> events = eventDAO.getAll();
        assertEquals(2, events.size()); 
        assertTrue(events.stream().anyMatch(e -> e.getName().equals("New Event")));
    }

    @Test
    void testGetEventById() {
        Event event = eventDAO.get(1);
        assertNotNull(event);
        assertEquals("Test Event", event.getName());
        assertEquals("Description of test event", event.getDescription());
    }

    @Test
    void testUpdateEvent() {
        Event event = new Event(1, "Updated Event", "Updated description", new Date());
        eventDAO.update(event);

        Event updatedEvent = eventDAO.get(1);
        assertEquals("Updated Event", updatedEvent.getName());
        assertEquals("Updated description", updatedEvent.getDescription());
    }

    @Test
    void testDeleteEvent() {
        eventDAO.delete(1);

        Event event = eventDAO.get(1);
        assertNull(event); 
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = eventDAO.getAll();
        assertEquals(1, events.size()); 
        assertEquals("Test Event", events.get(0).getName());
    }
}
