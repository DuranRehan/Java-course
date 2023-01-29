package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.StationsDaoNL;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;

import java.util.List;

public class StationsRepoNL implements Repository<Integer, StationsDto> {
    private final StationsDaoNL dao;

    public StationsRepoNL() throws RepositoryException {
        dao = StationsDaoNL.getInstance();
    }

    StationsRepoNL(StationsDaoNL dao) {
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