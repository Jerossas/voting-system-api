package com.dunno.votingsystemapi.usecases.voters;

import com.dunno.votingsystemapi.commands.voters.DeleteVoterByIdCommand;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface DeleteVoterByIdUseCase extends UseCase<DeleteVoterByIdCommand, Void> {}
