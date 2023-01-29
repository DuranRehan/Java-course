package atlg.g56055.mentoring;

import atlg.g56055.mentoring.dto.StudentDto;
import atlg.g56055.mentoring.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)

public class StudentRepositoryTest {
    private final StudentDto bob;

    private final StudentDto patrick;

    private static final int KEY = 12_345;

    private final List<StudentDto> all;

    @Mock
    private StudentDao mock;

    public StudentRepositoryTest() {
        //System.out.println("StudentRepositoryTest Constructor");
        //Test data
        bob = new StudentDto(KEY, "SquarePants", "SpongeBob");
        patrick = new StudentDto(99_999, "Star", "Patrick");
        all = new ArrayList<>();
        all.add(bob);
        all.add(patrick);
    }

    @BeforeEach
    void init() throws RepositoryException {
        //System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.get(bob.getKey())).thenReturn(bob);
        Mockito.lenient().when(mock.get(patrick.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.getAll()).thenReturn(all);
        Mockito.lenient().when(mock.get(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        StudentDto expected = bob;
        StudentRepository repository = new StudentRepository(mock);
        //Action
        StudentDto result = repository.get(KEY);
        //Assert
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).get(KEY);
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("TestGetNotExist");
        StudentDto expected = bob;
        StudentRepository repository = new StudentRepository(mock);
        //Action
        StudentDto result = repository.get(patrick.getKey());
        //Assert
        assertEquals(null, result);
        Mockito.verify(mock, times(1)).get(patrick.getKey());
    }

    @Test
    public void testGetIncorrectParam() throws Exception {
        System.out.println("testGetIncorrectParam");
        StudentDto expected = bob;
        StudentRepository repository = new StudentRepository(mock);
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(0)).get(incorrect);
    }

    @Test
    public void testAddWhenExisting() throws Exception {
        System.out.println("testAddWhenExisting");
        //Arrange
        StudentRepository repository = new StudentRepository(mock);
        //Action
        repository.add(bob);
        //Assert
        Mockito.verify(mock, times(1)).get(KEY);
        Mockito.verify(mock, times(1)).update(bob);
        Mockito.verify(mock, times(0)).insert(any(StudentDto.class));
    }

    @Test
    public void testAddWhenNotExisting() throws Exception {
        System.out.println("testAddWhenNotExisting");
        //Arrange
        StudentRepository repository = new StudentRepository(mock);
        //Action
        repository.add(patrick);
        //Assert
        Mockito.verify(mock, times(1)).get(patrick.getKey());
        Mockito.verify(mock, times(0)).update(patrick);
        Mockito.verify(mock, times(1)).insert(any(StudentDto.class));
    }
    @Test
    public void testAddWhenIncorrect() throws Exception {
        System.out.println("testAddWhenIncorrect");
        //Arrange
        StudentRepository repository = new StudentRepository(mock);
       //Assert
        Integer incorrect = null;
        StudentDto test= new StudentDto(incorrect,"For","test");
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.add(test);
        });
        Mockito.verify(mock, times(0)).get(incorrect);
        Mockito.verify(mock, times(0)).update(test);
        Mockito.verify(mock, times(0)).insert(any(StudentDto.class));
    }

    @Test
    public void testRemoveWhenExisting() throws Exception {
        System.out.println("testRemoveWhenExisting");
        //Arrange
        StudentRepository repository = new StudentRepository(mock);
        //Action
        repository.remove(KEY);
        //Assert
        Mockito.verify(mock, times(1)).delete(KEY);
    }

    @Test
    public void testRemoveWhenNotExisting() throws Exception {
        System.out.println("testRemoveWhenNotExisting");
        //Arrange
        StudentRepository repository = new StudentRepository(mock);
        //Action
        repository.remove(patrick.getKey());
        //Assert
        Mockito.verify(mock, times(1)).delete(patrick.getKey());
    }

    @Test
    public void testRemoveWhenIncorrect() throws Exception {
        System.out.println("testRemoveWhenIncorrect");
        //Arrange
        StudentRepository repository = new StudentRepository(mock);
        //Assert
        Integer incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.remove(incorrect);
        });
        Mockito.verify(mock, times(0)).delete(incorrect);
    }
    /**
     * To add Test contains and getAll()
     */
}
