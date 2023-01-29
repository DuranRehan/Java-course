package g56055.atlg.stibride.models.data.dto;

public class LinesDto extends Dto<Integer>{

    public LinesDto(Integer key) {
        super(key);
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }
}
