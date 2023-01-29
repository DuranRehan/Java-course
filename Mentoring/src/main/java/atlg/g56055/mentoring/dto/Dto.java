package atlg.g56055.mentoring.dto;

import java.util.Objects;

/**
 *
 * @author g56055
 * @param <K>
 */
public class Dto<K> {
    K key;
    public K getKey(){
        return key;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dto<?> other = (Dto<?>) obj;
        return Objects.equals(this.key, other.key);
    }
    
}
