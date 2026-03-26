package com.dunno.votingsystemapi.usecases.votes;

import com.dunno.votingsystemapi.commands.votes.VoteCommand;
import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface VoteUseCase extends UseCase<VoteCommand, Vote> {}
