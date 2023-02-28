package uz.dam.userservice.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.dam.userservice.criteria.base.GenericCriteria;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Criteria extends GenericCriteria {

    public String search;

    @Override
    public String toString() {
        return "Criteria{" +
                "search='" + search + '\'' +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}
