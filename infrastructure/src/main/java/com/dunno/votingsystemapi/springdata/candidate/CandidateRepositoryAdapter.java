package com.dunno.votingsystemapi.springdata.candidate;

import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Password;
import com.dunno.votingsystemapi.repositories.CandidateRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CandidateRepositoryAdapter implements CandidateRepository {

    private final SpringDataCandidateRepository springDataCandidateRepository;

    public CandidateRepositoryAdapter(SpringDataCandidateRepository springDataCandidateRepository) {
        this.springDataCandidateRepository = springDataCandidateRepository;
    }

    @Override
    public Candidate save(Candidate candidate) {

        CandidateEntity entity = new CandidateEntity(
                candidate.getId(),
                candidate.getEmail().getValue(),
                candidate.getPassword().getValue(),
                candidate.getRole().name(),
                candidate.getFullName(),
                candidate.getParty()
        );

        CandidateEntity saved = springDataCandidateRepository.save(entity);

        return Candidate.restore(
                saved.getId(),
                Email.fromStored(saved.getEmail()),
                Password.fromEncoded(saved.getPassword()),
                saved.getFullName(),
                saved.getParty()
        );
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springDataCandidateRepository.existsByEmail(email.getValue());
    }

    @Override
    public Optional<Candidate> findByEmail(Email email) {
        return springDataCandidateRepository.findByEmail(email.getValue())
                .map(savedCandidate -> Candidate.restore(
                        savedCandidate.getId(),
                        Email.fromStored(savedCandidate.getEmail()),
                        Password.fromEncoded(savedCandidate.getPassword()),
                        savedCandidate.getFullName(),
                        savedCandidate.getParty()
                ));
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return springDataCandidateRepository.findById(id)
                .map(savedCandidate -> Candidate.restore(
                        savedCandidate.getId(),
                        Email.fromStored(savedCandidate.getEmail()),
                        Password.fromEncoded(savedCandidate.getPassword()),
                        savedCandidate.getFullName(),
                        savedCandidate.getParty()
                ));
    }

    @Override
    public void deleteById(Long id) {
        springDataCandidateRepository.deleteById(id);
    }

    @Override
    public List<Candidate> findAll() {
        return springDataCandidateRepository.findAll().stream()
                .map(entity -> Candidate.restore(
                        entity.getId(),
                        Email.fromStored(entity.getEmail()),
                        Password.fromEncoded(entity.getPassword()),
                        entity.getFullName(),
                        entity.getParty()
                )).toList();
    }
}
