package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.StopsDao;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.models.data.dto.StopsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopsRepoTest {

    @Mock
    private StopsDao mock;
    private final StopsDto stops;
    private final StopsDto wrongStops;
    private final List<StopsDto> all;
    private static final Pair<Integer,Integer> KEY = new Pair<>(15,0);

    public StopsRepoTest() {
        stops = new StopsDto(KEY, KEY.getKey(), KEY.getValue(),99);

        wrongStops = new StopsDto(new Pair<>(9,9999),9,9999,9);

        all  = List.of(
                new StopsDto(new Pair<>(1,8012),1,8012,6),
                new StopsDto(new Pair<>(1,8022),1,8022,7),
                new StopsDto(new Pair<>(1,8032),1,8032,8),
                new StopsDto(new Pair<>(5,8012),5,8012,15),
                new StopsDto(new Pair<>(5,8022),5,8022,16),
                new StopsDto(new Pair<>(5,8032),5,8032,17),
                stops);
    }

    @BeforeEach
    void init() throws RepositoryException {
        //Mock behaviour
        Mockito.lenient().when(mock.select(stops.getKey())).thenReturn(stops);
        Mockito.lenient().when(mock.select(wrongStops.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetExist() throws RepositoryException {
        StopsRepo repository = new StopsRepo(mock);
        StopsDto result = repository.get(KEY);
        assertEquals(stops, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws RepositoryException {
        StopsRepo repository = new StopsRepo(mock);
        StopsDto result = repository.get(wrongStops.getKey());
        assertNull(result);
        Mockito.verify(mock, times(1)).select(wrongStops.getKey());
    }

    @Test
    public void testGetIncorrect() throws RepositoryException {
        StopsRepo repository = new StopsRepo(mock);
        assertThrows(RepositoryException.class, () -> {
            repository.get(null);
        });
        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testGetAll() throws RepositoryException {
        StopsRepo repository = new StopsRepo(mock);
        List<StopsDto> result = repository.getAll();
        assertEquals(all, result);
        Mockito.verify(mock, times(1)).selectAll();
    }
}
