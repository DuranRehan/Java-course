package g56055.atlg.stibride.models.data.dao;

import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationsDaoNL implements Dao<Integer, StationsDto>{
    private final Connection connexion;

    private StationsDaoNL() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    @Override
    public List<StationsDto> selectAll() throws RepositoryException {
        List<StationsDto> dtos = new ArrayList<>();
        String sql = "SELECT id,name FROM STATIONS_NL ORDER BY name";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                StationsDto dto= new StationsDto(rs.getInt(1),rs.getString(2));
                dtos.add(dto);
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StationsDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        StationsDto dto = null;
        String sql = "SELECT id,name FROM STATIONS_NL WHERE  id = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto =  new StationsDto(rs.getInt(1),rs.getString(2));
            }
            return dto;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    //Singleton Pattern
    public static StationsDaoNL getInstance() throws RepositoryException {
        return StationsDaoNL.StationDaoNLHolder.getInstance();
    }

    private static class StationDaoNLHolder {
        private static StationsDaoNL getInstance() throws RepositoryException {
            return new StationsDaoNL();
        }
    }
}