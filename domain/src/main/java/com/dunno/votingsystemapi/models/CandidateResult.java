package com.dunno.votingsystemapi.models;

public class CandidateResult {

    private final Long candidateId;
    private final String candidateName;
    private final String party;
    private final long votes;
    private final double percentage;

    public CandidateResult(Long candidateId, String candidateName, String party, long votes, double percentage) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.party = party;
        this.votes = votes;
        this.percentage = percentage;
    }

    public Long getCandidateId() {
        return candidateId;
    }
    public String getCandidateName() {
        return candidateName;
    }
    public String getParty() {
        return party;
    }
    public long getVotes() {
        return votes;
    }
    public double getPercentage() {
        return percentage;
    }
}
