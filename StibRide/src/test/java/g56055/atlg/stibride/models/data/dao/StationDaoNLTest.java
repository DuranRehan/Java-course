package g56055.atlg.stibride.models.data.dao;

import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StationDaoNLTest {
    private StationsDaoNL instance;
    private final StationsDto stations;
    private final StationsDto wrongStations;
    private final List<StationsDto> all;
    private static final int KEY = 0;

    public StationDaoNLTest() {
        stations = new StationsDto(KEY, "STATION DE TEST");
        wrongStations = new StationsDto(9999, "STATION INEXISTANTE");
        all = List.of(
                new StationsDto(8012, "DE BROUCKERE"),
                new StationsDto(8022, "CENTRAAL STATION"),
                new StationsDto(8032, "PARK"),
                stations);
        try {
            instance = StationsDaoNL.getInstance();
        } catch (RepositoryException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", e);
        }
    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Action
        StationsDto result = instance.select(KEY);
        //Assert
        assertEquals(stations, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Action
        StationsDto result = instance.select(wrongStations.getKey());
        //Assert
        assertNull(result);
    }

    @Test
    public void testSelectIncorrectParameter() throws Exception {
        System.out.println("testSelectIncorrectParameter");
        //Arrange
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.select(incorrect);
        });
    }

    @Test
    public void testgetAll() throws Exception {
        System.out.println("testSelectAll");
        List<StationsDto> result = instance.selectAll();
        assertTrue(all.containsAll(result));
    }
}
