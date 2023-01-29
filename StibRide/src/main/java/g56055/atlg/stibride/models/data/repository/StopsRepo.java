package g56055.atlg.stibride.models.data.repository;

import g56055.atlg.stibride.models.data.dao.StopsDao;
import g56055.atlg.stibride.models.data.dto.LinesDto;
import g56055.atlg.stibride.models.data.dto.StopsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import javafx.util.Pair;

import java.util.List;

public class StopsRepo implements Repository<Pair<Integer, Integer>, StopsDto> {
    private final StopsDao dao;

    public StopsRepo() throws RepositoryException {
        dao = StopsDao.getInstance();
    }

    StopsRepo(StopsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StopsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopsDto get(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    public List<StopsDto> getAdjacent(Integer key) throws RepositoryException {
        return dao.selectAdjacent(key);
    }
    public List<LinesDto> getAllLines(Integer key) throws RepositoryException {
        return dao.selectLines(key);
    }
}
