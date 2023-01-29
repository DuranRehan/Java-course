package g56055.atlg.stibride.models.data.dao;

import g56055.atlg.stibride.models.data.dto.StopsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StopsDaoTest {

    private final StopsDto stops;
    private final StopsDto wrongStops;
    private final List<StopsDto> all;
    private static final Pair<Integer, Integer> KEY = new Pair<>(15, 0);
    private StopsDao instance;

    public StopsDaoTest() {
        stops = new StopsDto(KEY, KEY.getKey(), KEY.getValue(), 99);

        wrongStops = new StopsDto(new Pair<>(9, 9999), 9, 9999, 9);

        all = List.of(
                new StopsDto(new Pair<>(1, 8012), 1, 8012, 6),
                new StopsDto(new Pair<>(1, 8022), 1, 8022, 7),
                new StopsDto(new Pair<>(1, 8032), 1, 8032, 8),
                new StopsDto(new Pair<>(5, 8012), 5, 8012, 15),
                new StopsDto(new Pair<>(5, 8022), 5, 8022, 16),
                new StopsDto(new Pair<>(5, 8032), 5, 8032, 17),
                stops);
        try {
            instance = StopsDao.getInstance();
        } catch (RepositoryException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", e);
        }
    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Action
        StopsDto result = instance.select(KEY);
        //Assert
        assertEquals(stops, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Action
        StopsDto result = instance.select(wrongStops.getKey());
        //Assert
        assertNull(result);
    }

    @Test
    public void testSelectIncorrectParameter() throws Exception {
        System.out.println("testSelectIncorrectParameter");
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.select(null);
        });
    }

    @Test
    public void testgetAll() throws Exception {
        System.out.println("testSelectAll");
        List<StopsDto> result = instance.selectAll();
        assertTrue(all.containsAll(result));
    }

}
