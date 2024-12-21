
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TerrainDAOTest {

    private TerrainDAO terrainDAO;
    private Connection connection;

    @BeforeAll
    void setupDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/EM"; /
        String username = "root"; 
        String password = "";
        connection = DriverManager.getConnection(url, username, password);
        terrainDAO = new TerrainDAO();
    }

    @BeforeEach
    void resetDatabase() throws SQLException {
        connection.createStatement().execute("DELETE FROM terrains");
        connection.createStatement().execute(
            "INSERT INTO terrains (id, name, type, size) VALUES " +
            "(1, 'Football Field', 'Sports', 500)"
        );
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close(); 
    }

    @Test
    void testAddTerrain() {
        Terrain terrain = new Terrain(0, "Basketball Court", "Sports", 300);
        terrainDAO.add(terrain);

        List<Terrain> terrains = terrainDAO.getAll();
        assertEquals(2, terrains.size()); 
        assertTrue(terrains.stream().anyMatch(t -> t.getName().equals("Basketball Court")));
    }

    @Test
    void testGetTerrainById() {
        Terrain terrain = terrainDAO.get(1);
        assertNotNull(terrain);
        assertEquals("Football Field", terrain.getName());
        assertEquals("Sports", terrain.getType());
        assertEquals(500, terrain.getSize());
    }

    @Test
    void testUpdateTerrain() {
        Terrain updatedTerrain = new Terrain(1, "Updated Field", "Updated Type", 600);
        terrainDAO.update(updatedTerrain);

        Terrain terrain = terrainDAO.get(1);
        assertNotNull(terrain);
        assertEquals("Updated Field", terrain.getName());
        assertEquals("Updated Type", terrain.getType());
        assertEquals(600, terrain.getSize());
    }

    @Test
    void testDeleteTerrain() {
        terrainDAO.delete(1);

        Terrain terrain = terrainDAO.get(1);
        assertNull(terrain); 
    }

    @Test
    void testGetAllTerrains() {
        List<Terrain> terrains = terrainDAO.getAll();
        assertEquals(1, terrains.size()); 
        Terrain terrain = terrains.get(0);
        assertEquals("Football Field", terrain.getName());
        assertEquals("Sports", terrain.getType());
        assertEquals(500, terrain.getSize());
    }
}
