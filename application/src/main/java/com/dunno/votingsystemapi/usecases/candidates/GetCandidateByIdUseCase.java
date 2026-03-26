package com.dunno.votingsystemapi.usecases.candidates;

import com.dunno.votingsystemapi.commands.candidates.GetCandidateByIdCommand;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface GetCandidateByIdUseCase extends UseCase<GetCandidateByIdCommand, Candidate> {}
