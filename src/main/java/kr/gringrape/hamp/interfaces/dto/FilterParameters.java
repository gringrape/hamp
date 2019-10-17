package kr.gringrape.hamp.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class FilterParameters {

    private Optional<String> address;

    private Optional<Long> topicId;

    private Optional<String> keyword;

    private Optional<LocalDateTime> durationStart;

    private Optional<LocalDateTime> durationEnd;

}
