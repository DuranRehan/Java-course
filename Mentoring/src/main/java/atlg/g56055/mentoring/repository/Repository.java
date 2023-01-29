package atlg.g56055.mentoring.repository;

import atlg.g56055.mentoring.dto.Dto;
import atlg.g56055.mentoring.dto.StudentDto;
import atlg.g56055.mentoring.exception.RepositoryException;
import java.util.List;

public interface Repository<T extends Dto<K>,K> {

    public void add(T item) throws RepositoryException;

    public void remove(K key) throws RepositoryException;

    public T get(K key) throws RepositoryException;

    public List<T> getAll() throws RepositoryException;

    public boolean contains(K key) throws RepositoryException;
}
