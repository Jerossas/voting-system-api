package com.dunno.votingsystemapi.usecases.auth;

import com.dunno.votingsystemapi.commands.auth.RegisterCandidateCommand;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface RegisterCandidateUseCase extends UseCase<RegisterCandidateCommand, Candidate> {}
