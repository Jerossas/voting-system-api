package com.dunno.votingsystemapi.springdata;

import com.dunno.votingsystemapi.springdata.admin.SpringDataAdminRepository;
import com.dunno.votingsystemapi.springdata.candidate.SpringDataCandidateRepository;
import com.dunno.votingsystemapi.springdata.voter.SpringDataVoterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SpringDataVoterRepository voterRepository;
    private final SpringDataCandidateRepository candidateRepository;
    private final SpringDataAdminRepository adminRepository;

    public UserDetailsServiceImpl(SpringDataVoterRepository voterRepository, SpringDataCandidateRepository candidateRepository, SpringDataAdminRepository adminRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return voterRepository.findByEmail(username)
                .map(v -> (UserDetails) v)
                .or(() -> candidateRepository.findByEmail(username).map(c -> (UserDetails) c))
                .or(() -> adminRepository.findByEmail(username).map(a -> (UserDetails) a))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
