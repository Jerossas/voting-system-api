package com.dunno.votingsystemapi.usecases.candidates;

import com.dunno.votingsystemapi.commands.candidates.DeleteCandidateByIdCommand;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface DeleteCandidateByIdUseCase extends UseCase<DeleteCandidateByIdCommand, Void> {}
