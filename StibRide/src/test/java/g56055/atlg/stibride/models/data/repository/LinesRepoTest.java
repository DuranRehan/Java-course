package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.LinesDao;
import g56055.atlg.stibride.models.data.dto.LinesDto;
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
public class LinesRepoTest {

    @Mock
    private LinesDao mock;

    private final LinesDto lines;

    private final LinesDto wrongLines;

    private static final int KEY = 15;
    private final List<LinesDto> all;
    public LinesRepoTest() {
        lines = new LinesDto(KEY);
        wrongLines = new LinesDto(9999);
        all  = List.of(
                new LinesDto(1),
                new LinesDto(2),
                new LinesDto(5),
                new LinesDto(6),
                lines);
    }

    @BeforeEach
    void init() throws RepositoryException {
        //Mock behaviour
        Mockito.lenient().when(mock.select(lines.getKey())).thenReturn(lines);
        Mockito.lenient().when(mock.select(wrongLines.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetExist() throws RepositoryException {
        LinesRepo repository = new LinesRepo(mock);
        LinesDto result = repository.get(KEY);
        assertEquals(lines,result);
        Mockito.verify(mock,times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws RepositoryException {
        LinesRepo repository = new LinesRepo(mock);
        LinesDto result = repository.get(wrongLines.getKey());
        assertNull(result);
        Mockito.verify(mock,times(1)).select(wrongLines.getKey());
    }

    @Test
    public void testGetIncorrect() throws RepositoryException {
        LinesRepo repository = new LinesRepo(mock);
        Integer incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            repository.get(incorrect);
        });
        Mockito.verify(mock,times(1)).select(null);
    }

    @Test
    public void testGetAll() throws RepositoryException {
        LinesRepo repository = new LinesRepo(mock);
        List<LinesDto> result = repository.getAll();
        assertEquals(all,result);
        Mockito.verify(mock,times(1)).selectAll();
    }
}
