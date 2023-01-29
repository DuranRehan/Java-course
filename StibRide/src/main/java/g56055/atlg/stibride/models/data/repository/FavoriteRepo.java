package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.FavoriteDao;
import g56055.atlg.stibride.models.data.dto.FavoriteDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;

import java.util.List;

public class FavoriteRepo implements Repository<String, FavoriteDto> {
    private final FavoriteDao dao;

    public FavoriteRepo() throws RepositoryException {
        dao = FavoriteDao.getInstance();
    }

    public String add(FavoriteDto item) throws RepositoryException {
        String key = item.getKey();
        if (contains(item.getKey())) {
            dao.update(item);
        } else {
            key = dao.insert(item);
        }
        return key;
    }

    public void remove(String key) throws RepositoryException {
        dao.delete(key);
    }


    public boolean contains(String key) throws RepositoryException {
        FavoriteDto dto = dao.select(key);
        return dto != null;
    }


    @Override
    public List<FavoriteDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoriteDto get(String key) throws RepositoryException {
        return dao.select(key);
    }
}
