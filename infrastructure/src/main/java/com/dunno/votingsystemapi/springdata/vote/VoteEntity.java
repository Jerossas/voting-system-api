package com.dunno.votingsystemapi.springdata.vote;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voter_id")
    private Long voterId;

    @Column(name = "candidate_id")
    private Long candidateId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public VoteEntity() {}

    public VoteEntity(Long id, Long voterId, Long candidateId) {
        this.id = id;
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    public Long getId() { return id; }
    public Long getVoterId() { return voterId; }
    public Long getCandidateId() { return candidateId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
