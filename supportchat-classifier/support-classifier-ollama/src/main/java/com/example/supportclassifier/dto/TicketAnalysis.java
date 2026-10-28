package com.example.supportclassifier.dto;

public class TicketAnalysis {
    private String category;
    private String priority;
    private String sentiment;
    private String draftResponse;

    public TicketAnalysis() {
    }

    public TicketAnalysis(String category, String priority, String sentiment, String draftResponse) {
        this.category = category;
        this.priority = priority;
        this.sentiment = sentiment;
        this.draftResponse = draftResponse;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getDraftResponse() {
        return draftResponse;
    }

    public void setDraftResponse(String draftResponse) {
        this.draftResponse = draftResponse;
    }
}
