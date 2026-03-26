package com.dunno.votingsystemapi.springdata.voter;

import com.dunno.votingsystemapi.models.Email;
import com.dunno.votingsystemapi.models.Password;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.repositories.VoterRepository;
import org.springframework.stereotype.Repository;

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
}
