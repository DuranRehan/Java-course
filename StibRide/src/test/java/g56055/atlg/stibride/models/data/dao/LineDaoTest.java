package g56055.atlg.stibride.models.data.dao;

import g56055.atlg.stibride.models.data.dto.LinesDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineDaoTest {

    private final LinesDto lines;
    private final LinesDto wrongLines;
    private static final int KEY = 15;
    private final List<LinesDto> all;
    private LinesDao instance;

    public LineDaoTest() {
        lines = new LinesDto(KEY);
        wrongLines = new LinesDto(9999);
        all = List.of(
                new LinesDto(1),
                new LinesDto(2),
                new LinesDto(5),
                new LinesDto(6),
                lines);
        try {
            instance = LinesDao.getInstance();
        } catch (RepositoryException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", e);
        }
    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Action
        LinesDto result = instance.select(KEY);
        //Assert
        assertEquals(lines, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Action
        LinesDto result = instance.select(wrongLines.getKey());
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
        List<LinesDto> result = instance.selectAll();
        assertTrue(all.containsAll(result));
    }

}
