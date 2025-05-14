package com.backend.cyberbytes.dto;

import lombok.Data;

import java.util.List;

@Data
public class IaResponse {
    private List<Candidate> candidates;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    @Data
    public static class Candidate {
        private Content content;

        public Content getContent(){
            return content;
        }
    }

    @Data
    public static class Content {
        private List<Part> parts;

        public List<Part> getParts(){
            return parts;
        }

    }

    @Data
    public static class Part {
        private String text;

        public String getText(){
            return text;
        }
    }
}
