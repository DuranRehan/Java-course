package g56055.atlg.stibride.models.data.dto;

public class FavoriteDto extends Dto<String> {
    private StationsDto source;
    private StationsDto destination;

    public FavoriteDto(String key) {
        super(key);
    }

    public FavoriteDto(String key,StationsDto source, StationsDto dest) {
        super(key);
        this.source = source;
        this.destination = dest;
    }

    public StationsDto getSource() {
        return source;
    }

    public StationsDto getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return key;
    }
}
