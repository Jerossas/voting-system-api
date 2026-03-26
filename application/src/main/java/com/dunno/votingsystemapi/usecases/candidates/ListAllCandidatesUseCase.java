package com.dunno.votingsystemapi.usecases.candidates;

import com.dunno.votingsystemapi.commands.candidates.ListAllCandidatesCommand;
import com.dunno.votingsystemapi.models.Candidate;
import com.dunno.votingsystemapi.usecases.UseCase;

import java.util.List;

public interface ListAllCandidatesUseCase extends UseCase<ListAllCandidatesCommand, List<Candidate>> {}
