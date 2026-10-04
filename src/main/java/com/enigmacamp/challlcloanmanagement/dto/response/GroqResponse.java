package com.enigmacamp.challlcloanmanagement.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class GroqResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choices> choices;

    @Getter
    @Setter
    public static class Choices {
        private Integer index;
        private Message message;
        private String finish_reason;
    }

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;
    }
}
