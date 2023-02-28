package uz.dam.userservice.criteria.base;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class GenericCriteria implements BaseGenericCriteria, Serializable {

    @Min(value = 0,message = "page min value 0")
    @NotNull(message = "page cannot be null")
    protected Integer page;

    @Min(value = 1,message = "size min value 1")
    @NotNull(message = "size cannot be null")
    protected Integer size;

    public Pageable getPageable(){
        return PageRequest.of(page, size);
    }

}