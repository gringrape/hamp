package kr.gringrape.hamp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotEmpty
    private String address;

    @NotNull
    private Long topicId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @ManyToMany
    private List<User> applyingUsers;

    public void initialize() {
        applyingUsers = new ArrayList<>();
    }

    public boolean addUser(User user) {
        if(isExisted(user)) {
          return false;
        }
        return applyingUsers.add(user);
    }

    private boolean isExisted(User user) {
        return applyingUsers.stream()
                .anyMatch(
                        thisUser -> thisUser.getId()
                                        .equals(user.getId())
                );
    }

}
