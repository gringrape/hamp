package kr.gringrape.hamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Target;

import javax.persistence.*;
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

    @OneToOne
    private User writer;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String address;

    private Double latitude;

    private Double longitude;

    @NotNull
    private Long topicId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @JsonIgnore
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
