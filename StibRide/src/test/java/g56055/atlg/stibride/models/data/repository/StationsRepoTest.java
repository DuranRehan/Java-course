package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.LinesDao;
import g56055.atlg.stibride.models.data.dao.StationsDao;
import g56055.atlg.stibride.models.data.dto.LinesDto;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
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
public class StationsRepoTest {
    @Mock
    private StationsDao mock;
    private final StationsDto stations;
    private final StationsDto wrongStations;
    private final List<StationsDto> all;
    private static final int KEY = 0;
    public StationsRepoTest() {
        stations = new StationsDto(KEY,"STATION DE TEST");
        wrongStations = new StationsDto(9999,"STATION INEXISTANTE");
        all  = List.of(
                new StationsDto(8012,"DE BROUCKERE"),
                new StationsDto(8022,"GARE CENTRALE"),
                new StationsDto(8032,"PARC"),
                stations);
    }

    @BeforeEach
    void init() throws RepositoryException {
        //Mock behaviour
        Mockito.lenient().when(mock.select(stations.getKey())).thenReturn(stations);
        Mockito.lenient().when(mock.select(wrongStations.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetExist() throws RepositoryException {
        StationsRepo repository = new StationsRepo(mock);
        StationsDto result = repository.get(KEY);
        assertEquals(stations, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws RepositoryException {
        StationsRepo repository = new StationsRepo(mock);
        StationsDto result = repository.get(wrongStations.getKey());
        assertNull(result);
        Mockito.verify(mock, times(1)).select(wrongStations.getKey());
    }

    @Test
    public void testGetIncorrect() throws RepositoryException {
        StationsRepo repository = new StationsRepo(mock);
        Integer incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testGetAll() throws RepositoryException {
        StationsRepo repository = new StationsRepo(mock);
        List<StationsDto> result = repository.getAll();
        assertEquals(all, result);
        Mockito.verify(mock, times(1)).selectAll();
    }
}
