package g56055.atlg.stibride.models.data.dao;

import g56055.atlg.stibride.models.data.dto.LinesDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LinesDao implements Dao<Integer, LinesDto> {
    private final Connection connexion;
    private LinesDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    @Override
    public List<LinesDto> selectAll() throws RepositoryException {
        List<LinesDto> dtos = new ArrayList<>();
        String sql = "SELECT id FROM LINES";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                LinesDto dto= new LinesDto(rs.getInt(1));
                dtos.add(dto);
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public LinesDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        LinesDto dto = null;
        String sql = "SELECT id FROM LINES WHERE  id = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto =  new LinesDto(rs.getInt(1));
            }
            return dto;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    //Singleton Pattern
    public static LinesDao getInstance() throws RepositoryException {
        return LinesDao.LinesDaoHolder.getInstance();
    }

    private static class LinesDaoHolder {
        private static LinesDao getInstance() throws RepositoryException {
            return new LinesDao();
        }
    }
}
