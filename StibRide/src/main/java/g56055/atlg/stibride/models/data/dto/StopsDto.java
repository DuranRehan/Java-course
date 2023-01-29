package g56055.atlg.stibride.models.data.dto;

import javafx.util.Pair;

public class StopsDto extends Dto<Pair<Integer,Integer>>{
    private final int lines;
    private final int stations;
    private final int order;
    public StopsDto(Pair<Integer,Integer> key, int id_line, int id_stations, int order) {
        super(key);
        this.lines = id_line;
        this.stations = id_stations;
        this.order = order;
    }

    public int getLines() {
        return lines;
    }

    public int getStations() {
        return stations;
    }

    public int getOrder() {
        return order;
    }
}
