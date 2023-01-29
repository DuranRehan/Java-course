package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.StationsDaoNL;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StationsRepoNLTest {
    @Mock
    private StationsDaoNL mock;
    private final StationsDto stations;
    private final StationsDto wrongStations;
    private final List<StationsDto> all;
    private static final int KEY = 0;

    public StationsRepoNLTest() {
        stations = new StationsDto(KEY, "STATION DE TEST");
        wrongStations = new StationsDto(9999, "STATION INEXISTANTE");
        all = List.of(
                new StationsDto(8012, "DE BROUCKERE"),
                new StationsDto(8022, "CENTRAAL STATION"),
                new StationsDto(8032, "PARK"),
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
        StationsRepoNL repository = new StationsRepoNL(mock);
        StationsDto result = repository.get(KEY);
        assertEquals(stations, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws RepositoryException {
        StationsRepoNL repository = new StationsRepoNL(mock);
        StationsDto result = repository.get(wrongStations.getKey());
        assertNull(result);
        Mockito.verify(mock, times(1)).select(wrongStations.getKey());
    }

    @Test
    public void testGetIncorrect() throws RepositoryException {
        StationsRepoNL repository = new StationsRepoNL(mock);
        Integer incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testGetAll() throws RepositoryException {
        StationsRepoNL repository = new StationsRepoNL(mock);
        List<StationsDto> result = repository.getAll();
        assertEquals(all, result);
        Mockito.verify(mock, times(1)).selectAll();
    }
}
