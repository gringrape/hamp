package kr.gringrape.hamp.interfaces.dtos;

import kr.gringrape.hamp.domain.Meeting;
import lombok.*;

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
