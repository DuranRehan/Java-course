package g56055.atlg.stibride.models.data.dto;

import java.util.List;

public class StationsDto extends Dto<Integer> {
    private String name;
    private List<LinesDto> lines;

    public StationsDto(Integer key, String name) {
        super(key);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLines(List<LinesDto> lines) {
        this.lines = lines;
    }

    public List<LinesDto> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return name;
    }
}
