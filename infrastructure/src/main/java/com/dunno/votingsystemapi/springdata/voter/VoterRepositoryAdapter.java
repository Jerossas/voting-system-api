package com.dunno.votingsystemapi.springdata.voter;

import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Password;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.repositories.VoterRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VoterRepositoryAdapter implements VoterRepository {

    private final SpringDataVoterRepository springDataVoterRepository;

    public VoterRepositoryAdapter(SpringDataVoterRepository springDataVoterRepository) {
        this.springDataVoterRepository = springDataVoterRepository;
    }

    @Override
    public Voter save(Voter voter) {

        VoterEntity entity = new VoterEntity(
                voter.getId(),
                voter.getEmail().getValue(),
                voter.getPassword().getValue(),
                voter.getRole().name(),
                voter.getFullName()
        );

        VoterEntity saved = springDataVoterRepository.save(entity);

        return Voter.restore(
                saved.getId(),
                saved.getFullName(),
                Email.fromStored(saved.getEmail()),
                Password.fromEncoded(saved.getPassword())
        );
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springDataVoterRepository.existsByEmail(email.getValue());
    }

    @Override
    public Optional<Voter> findByEmail(Email email) {
        return springDataVoterRepository.findByEmail(email.getValue())
                .map(savedVoter -> Voter.restore(
                        savedVoter.getId(),
                        savedVoter.getFullName(),
                        Email.fromStored(savedVoter.getEmail()),
                        Password.fromEncoded(savedVoter.getPassword())
                ));
    }

    @Override
    public List<Voter> findAll() {
        return springDataVoterRepository.findAll().stream()
                .map(entity -> Voter.restore(
                        entity.getId(),
                        entity.getFullName(),
                        Email.fromStored(entity.getEmail()),
                        Password.fromEncoded(entity.getPassword())
                )).toList();
    }

    @Override
    public Optional<Voter> findById(Long id) {
        return springDataVoterRepository.findById(id)
                .map(entity -> Voter.restore(
                        entity.getId(),
                        entity.getFullName(),
                        Email.fromStored(entity.getEmail()),
                        Password.fromEncoded(entity.getPassword())
                ));
    }

    @Override
    public void deleteById(Long id) {
        springDataVoterRepository.deleteById(id);
    }

    @Override
    public long count() {
        return springDataVoterRepository.count();
    }

}
