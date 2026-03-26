package com.dunno.votingsystemapi.models;

import com.dunno.votingsystemapi.exceptions.InvalidFieldException;

public class Vote {

    private Long id;
    private final Long voterId;
    private final Long candidateId;

    public Vote(Long voterId, Long candidateId) {
        if (voterId == null) throw new InvalidFieldException("voterId", "Voter id cannot be null.");
        if (candidateId == null) throw new InvalidFieldException("candidateId", "Candidate id cannot be null.");

        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    private Vote(Long id, Long voterId, Long candidateId) {
        this.id = id;
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    public static Vote restore(Long id, Long voterId, Long candidateId) {
        return new Vote(id, voterId, candidateId);
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public Long getVoterId() {
        return voterId;
    }

    public Long getId() {
        return id;
    }
}
