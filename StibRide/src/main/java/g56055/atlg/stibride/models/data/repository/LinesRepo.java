package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.LinesDao;
import g56055.atlg.stibride.models.data.dto.LinesDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;

import java.util.List;

public class LinesRepo implements Repository<Integer, LinesDto> {
    private final LinesDao dao;

    public LinesRepo() throws RepositoryException {
        dao = LinesDao.getInstance();
    }

    LinesRepo(LinesDao dao) {
        this.dao = dao;
    }

    @Override
    public List<LinesDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public LinesDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }
}
