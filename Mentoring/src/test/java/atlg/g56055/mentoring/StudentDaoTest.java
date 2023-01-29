package atlg.g56055.mentoring;

import atlg.g56055.mentoring.dto.StudentDto;
import atlg.g56055.mentoring.exception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDaoTest {
    private final StudentDto bob;
    private final StudentDto patrick;

    private static final int KEY = 12_345;
    private static final String FILE_URL = "data/test_repo_students.txt";

    private final String url;

    private final List<StudentDto> all;

    public StudentDaoTest() {
        System.out.println("==== StudentDaoTest Constructor =====");
        bob = new StudentDto(KEY, "SquarePants", "SpongeBob");
        patrick = new StudentDto(99_999, "Star", "Patrick");
        all = List.of(new StudentDto(64_931, "Olsen", "Maggy"),
                new StudentDto(73_780, "Frost", "Phoebe"),
                new StudentDto(94_853, "Ortega", "Skyler"),
                new StudentDto(93_371, "Blankenship", "Byron"),
                new StudentDto(82_227, "Cote", "Molly"),
                bob);

        url = FILE_URL;
    }


    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Arrange
        StudentDto expected = bob;
        StudentDao dao = new StudentDao(url);
        //Action
        StudentDto result = dao.get(KEY);
        //Assert
        assertEquals(expected, result);
    }


    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Arrange
        StudentDao dao = new StudentDao(url);
        //Action
        StudentDto result = dao.get(patrick.getKey());
        //Assert
        assertNull(result);
    }

    @Test
    public void testSelectIncorrectParameter() throws Exception {
        System.out.println("testSelectIncorrectParameter");
        //Arrange
        StudentDao dao = new StudentDao(url);
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            dao.get(incorrect);
        });
    }

    @Test
    public void testSelectWhenFileNotFound() throws Exception {
        System.out.println("testSelectWhenFileNotFound");
        //Arrange
        String url = "test/NoFile.txt";
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            StudentDao dao = new StudentDao(url);
            dao.get(KEY);
        });
    }

    @Test
    public void testInsertExist() throws Exception {
        System.out.println("testInsertExist");
        //Arrange
        StudentDao dao = new StudentDao(url);
        //Action
        dao.insert(bob);
        //Assert
        assertTrue(dao.getAll().contains(bob));
    }


    @Test
    public void testInsertNotExist() throws Exception {
        System.out.println("testInsertNotExist");
        //Arrange
        StudentDao dao = new StudentDao(url);
        //Action

        //Assert
        assertFalse(dao.getAll().contains(patrick));
    }

    @Test
    public void testInsertIncorrectParameter() throws Exception {
        System.out.println("testInsertIncorrectParameter");
        //Arrange
        StudentDao dao = new StudentDao(url);
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            dao.insert(new StudentDto(incorrect, "Unknow", "Unknow"));
        });
    }

    @Test
    public void testInsertWhenFileNotFound() throws Exception {
        System.out.println("testInsertWhenFileNotFound");
        //Arrange
        String url = "test/NoFile.txt";
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            StudentDao dao = new StudentDao(url);
            dao.insert(bob);
        });
    }

    @Test
    public void testSelectAllExist() throws Exception {
        System.out.println("testInsertExist");
        //Arrange
        StudentDao dao = new StudentDao(url);
        //Assert
        assertTrue(dao.getAll().contains(bob));
    }


    @Test
    public void testSelectAllNotExist() throws Exception {
        System.out.println("testInsertNotExist");
        //Arrange
        StudentDao dao = new StudentDao(url);
        //Action

        //Assert
        assertFalse(dao.getAll().contains(patrick));
    }


    @Test
    public void testSelectAllWhenFileNotFound() throws Exception {
        System.out.println("testSelectAllWhenFileNotFound");
        //Arrange
        String url = "test/NoFile.txt";
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            StudentDao dao = new StudentDao(url);
            dao.getAll();
        });

    }
}
