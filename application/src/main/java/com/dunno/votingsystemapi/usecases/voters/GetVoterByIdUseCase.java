package com.dunno.votingsystemapi.usecases.voters;

import com.dunno.votingsystemapi.commands.voters.GetVoterByIdCommand;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface GetVoterByIdUseCase extends UseCase<GetVoterByIdCommand, Voter> {
}
