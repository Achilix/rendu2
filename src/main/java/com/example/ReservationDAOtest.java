import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationDAOTest {

    private ReservationDAO reservationDAO;
    private Connection connection;

    @BeforeAll
    void setupDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/EM"; 
        String username = "root";
        String password = "";
        connection = DriverManager.getConnection(url, username, password);
        reservationDAO = new ReservationDAO();
    }

    @BeforeEach
    void resetDatabase() throws SQLException {
        connection.createStatement().execute("DELETE FROM reservations");
        connection.createStatement().execute(
            "INSERT INTO reservations (id, user_id, resource_id, reservation_date) VALUES " +
            "(1, 1, 101, '2024-01-01')"
        );
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close(); 
    }

    @Test
    void testAddReservation() {
        Reservation reservation = new Reservation(0, 2, 102, new Date());
        reservationDAO.add(reservation);

        List<Reservation> reservations = reservationDAO.getAll();
        assertEquals(2, reservations.size()); 
        assertTrue(reservations.stream().anyMatch(r -> r.getResourceId() == 102));
    }

    @Test
    void testGetReservationById() {
        Reservation reservation = reservationDAO.get(1);
        assertNotNull(reservation);
        assertEquals(1, reservation.getUserId());
        assertEquals(101, reservation.getResourceId());
        assertEquals(java.sql.Date.valueOf("2024-01-01"), reservation.getReservationDate());
    }

    @Test
    void testUpdateReservation() {
        Reservation updatedReservation = new Reservation(1, 3, 103, new Date());
        reservationDAO.update(updatedReservation);

        Reservation reservation = reservationDAO.get(1);
        assertNotNull(reservation);
        assertEquals(3, reservation.getUserId());
        assertEquals(103, reservation.getResourceId());
    }

    @Test
    void testDeleteReservation() {
        reservationDAO.delete(1);

        Reservation reservation = reservationDAO.get(1);
        assertNull(reservation); 
    }

    @Test
    void testGetAllReservations() {
        List<Reservation> reservations = reservationDAO.getAll();
        assertEquals(1, reservations.size()); 
        Reservation reservation = reservations.get(0);
        assertEquals(1, reservation.getUserId());
        assertEquals(101, reservation.getResourceId());
        assertEquals(java.sql.Date.valueOf("2024-01-01"), reservation.getReservationDate());
    }
}
