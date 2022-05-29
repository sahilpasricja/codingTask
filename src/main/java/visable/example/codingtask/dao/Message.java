package visable.example.codingtask.dao;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "message")
public class Message {


    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recipientId;

    private Long senderId;

    private String content;


}
