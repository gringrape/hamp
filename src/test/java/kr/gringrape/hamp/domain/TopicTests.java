package kr.gringrape.hamp.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TopicTests {

    @Test
    public void creation() {

        Topic topic = Topic.builder().name("C 언어").build();

        assertThat(topic.getName(), is("C 언어"));

    }

}