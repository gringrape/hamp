package kr.gringrape.hamp.interfaces.dto;

import kr.gringrape.hamp.domain.Meeting;
import lombok.*;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    private Long id;

    private String email;

    private String nick;

    private Integer level;

    private List<Meeting> appliedMeetings;

}
