
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
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EM", "root", "");
        reservationDAO = new ReservationDAO();
    }

    @BeforeEach
    void resetData() throws SQLException {
        // Réinitialiser les données avant chaque test
        connection.createStatement().execute("DELETE FROM reservations");
        connection.createStatement().execute("INSERT INTO reservations (id, user_id, resource_id, reservation_date) " +
                                             "VALUES (1, 1, 2, '2024-01-01')");
    }

    @Test
    void testAddReservation() {
        Reservation reservation = new Reservation(0, 2, 3, new Date());
        reservationDAO.add(reservation);

        List<Reservation> reservations = reservationDAO.getAll();
        assertEquals(2, reservations.size());
    }

    @Test
    void testGetReservationById() {
        Reservation reservation = reservationDAO.get(1);
        assertNotNull(reservation);
        assertEquals(1, reservation.getUserId());
        assertEquals(2, reservation.getResourceId());
    }

    @Test
    void testUpdateReservation() {
        Reservation reservation = new Reservation(1, 2, 3, new Date());
        reservationDAO.update(reservation);

        Reservation updatedReservation = reservationDAO.get(1);
        assertEquals(2, updatedReservation.getUserId());
        assertEquals(3, updatedReservation.getResourceId());
    }

    @Test
    void testDeleteReservation() {
        reservationDAO.delete(1);

        Reservation reservation = reservationDAO.get(1);
        assertNull(reservation);
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close();
    }
}
