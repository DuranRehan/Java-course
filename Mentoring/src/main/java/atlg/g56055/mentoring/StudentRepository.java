package atlg.g56055.mentoring;

import atlg.g56055.mentoring.dto.StudentDto;
import atlg.g56055.mentoring.exception.RepositoryException;
import atlg.g56055.mentoring.repository.Repository;

import java.util.List;

public class StudentRepository implements Repository<StudentDto, Integer> {
    private final StudentDao dao;

    public StudentRepository() {
        dao = StudentDao.getInstance();
    }

    public StudentRepository(StudentDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(StudentDto item) throws RepositoryException {
        if (item.getKey() == null) {
            throw new RepositoryException(new IllegalArgumentException("Key Not valid"));
        }
        if (!contains(item.getKey())) {
            dao.insert(item);
        } else {
            dao.update(item);
        }
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException(new IllegalArgumentException("Key Not valid"));
        }
        dao.delete(key);
    }

    @Override
    public StudentDto get(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException(new IllegalArgumentException("Key Not valid"));
        }
        return dao.get(key);
    }

    @Override
    public List<StudentDto> getAll() throws RepositoryException {
        return dao.getAll();
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException(new IllegalArgumentException("Key Not valid"));
        }
        if (dao.get(key) != null) {
            return true;
        }
        return false;
    }
}
