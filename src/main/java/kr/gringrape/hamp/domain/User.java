package kr.gringrape.hamp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nick;

    private String password;

    private Integer level;

    @JsonIgnore
    @ManyToMany(mappedBy = "applyingUsers")
    private List<Meeting> appliedMeetings;

    public boolean isAdmin() {

        if(this.level == 2) {
            return true;
        } else {
            return false;
        }

    }

    public void deactivate() {

        this.level = 0;

    }

    public boolean isActive() {

        if(this.level == 0) {
            return false;
        } else {
            return true;
        }

    }
}
