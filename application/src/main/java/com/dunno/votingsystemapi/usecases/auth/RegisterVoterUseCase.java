package com.dunno.votingsystemapi.usecases.auth;

import com.dunno.votingsystemapi.commands.auth.RegisterVoterCommand;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface RegisterVoterUseCase extends UseCase<RegisterVoterCommand, Voter> {}
