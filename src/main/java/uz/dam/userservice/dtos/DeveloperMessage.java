package uz.dam.userservice.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperMessage {

    private String uz;
    private String ru;
    private String en;

    public String toJson() {
        StringBuilder res = new StringBuilder("{\"uz\":\"");
        res.append(uz)
                .append("\", \"ru\":\"")
                .append(ru)
                .append("\", \"en\":\"")
                .append("\"}");
        return res.toString();
    }
}
