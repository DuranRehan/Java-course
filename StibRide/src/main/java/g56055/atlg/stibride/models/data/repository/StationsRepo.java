package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.StationsDao;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;

import java.util.List;

public class StationsRepo implements Repository<Integer, StationsDto> {
    private final StationsDao dao;

    public StationsRepo() throws RepositoryException {
        dao = StationsDao.getInstance();
    }

    StationsRepo(StationsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StationsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationsDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }
}
