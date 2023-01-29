package g56055.atlg.stibride.models.data.dao;

import g56055.atlg.stibride.models.data.dto.LinesDto;
import g56055.atlg.stibride.models.data.dto.StopsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopsDao implements Dao<Pair<Integer, Integer>, StopsDto> {
    private final Connection connexion;

    private StopsDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }


    @Override
    public List<StopsDto> selectAll() throws RepositoryException {
        List<StopsDto> dtos = new ArrayList<>();
        String sql = "SELECT DISTINCT id_line,id_station,id_order FROM STOPS JOIN STATIONS ON STATIONS.id = STOPS.id_station ORDER BY NAME";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                StopsDto dto = new StopsDto(new Pair<>(rs.getInt(1), rs.getInt(2)),
                        rs.getInt(1), rs.getInt(2), rs.getInt(3));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StopsDto select(Pair<Integer, Integer> key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        } else if (key.getKey() == null || key.getValue() == null) {
            throw new RepositoryException("clée pas correct");
        }

        StopsDto dto = null;
        String sql = "SELECT id_line,id_station,id_order FROM STOPS WHERE  id_line = ? AND id_station = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key.getKey());
            pstmt.setInt(2, key.getValue());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new StopsDto(key, rs.getInt("id_line"),
                        rs.getInt("id_station"), rs.getInt("id_order"));
            }
            return dto;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private List<StopsDto> selectSameStops(Integer id_station) throws RepositoryException {
        if (id_station == null) {
            throw new RepositoryException("Aucun identifiant de station donnée en paramètre");
        }
        List<StopsDto> allStops = new ArrayList<>();
        String sql = "SELECT id_line,id_station,id_order,name " +
                "FROM STOPS JOIN STATIONS ON STATIONS.id = STOPS.id_station WHERE id_station = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, id_station);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StopsDto dto = new StopsDto(new Pair<>(rs.getInt("id_line"), id_station), rs.getInt("id_line"),
                        rs.getInt("id_station"), rs.getInt("id_order"));
                allStops.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return allStops;
    }

    public List<StopsDto> selectAdjacent(Integer id_station) throws RepositoryException {
        List<Pair<Integer, Integer>> lines = new ArrayList<>();
        List<StopsDto> adjacent = new ArrayList<>();
        String sql =
                "SELECT STOPS.id_line, STOPS.id_order " +
                        "FROM STOPS " +
                        "WHERE STOPS.id_station= ?";

        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id_station);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lines.add(new Pair(rs.getInt("id_line"), rs.getInt("id_order")));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        sql = "SELECT STOPS.id_line, STOPS.id_station, STOPS.id_order, STATIONS.name " +
                "FROM STOPS " +
                "JOIN LINES ON LINES.id = STOPS.id_line " +
                "JOIN STATIONS ON STATIONS.id = STOPS.id_station " +
                "WHERE STOPS.id_line= ? AND STOPS.id_order = ?";

        for (Pair<Integer, Integer> line : lines) {
            for (int i = -1; i < 2; i += 2) {
                try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
                    stmt.setInt(1, line.getKey());
                    stmt.setInt(2, line.getValue() + i);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        adjacent.add(new StopsDto(new Pair<>(rs.getInt("id_line"), id_station), rs.getInt("id_line"),
                                rs.getInt("id_station"), rs.getInt("id_order")));
                    }
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            }
        }
        return adjacent;
    }

    public List<LinesDto> selectLines(Integer id_station) throws RepositoryException {
        if (id_station == null) {
            throw new RepositoryException("Aucun identifiant de station donnée en paramètre");
        }
        List<LinesDto> allLines = new ArrayList<>();
        String sql = "SELECT id_line FROM STOPS WHERE id_station =?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {

            pstmt.setInt(1, id_station);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LinesDto dto = new LinesDto(rs.getInt(1));
                allLines.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return allLines;
    }

    //Singleton Pattern
    public static StopsDao getInstance() throws RepositoryException {
        return StopsDaoHolder.getInstance();
    }

    private static class StopsDaoHolder {
        private static StopsDao getInstance() throws RepositoryException {
            return new StopsDao();
        }
    }

}
