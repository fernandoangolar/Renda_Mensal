package ao.com.techAngolar.exception;

import lombok.*;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StardarError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
