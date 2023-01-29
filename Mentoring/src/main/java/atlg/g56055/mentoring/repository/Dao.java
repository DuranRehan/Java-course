package atlg.g56055.mentoring.repository;

import atlg.g56055.mentoring.dto.Dto;
import atlg.g56055.mentoring.exception.RepositoryException;
import java.util.List;

public interface Dao<T extends Dto<K>,K> {

    public void insert(T item) throws RepositoryException;

    public void delete(K key) throws RepositoryException;

    public void update(T item) throws RepositoryException;

    public T get(K key) throws RepositoryException;

    public List<T> getAll() throws RepositoryException;
}
