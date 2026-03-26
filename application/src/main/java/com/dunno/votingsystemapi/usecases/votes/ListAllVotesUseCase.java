package com.dunno.votingsystemapi.usecases.votes;

import com.dunno.votingsystemapi.commands.votes.ListAllVotesCommand;
import com.dunno.votingsystemapi.models.Vote;
import com.dunno.votingsystemapi.usecases.UseCase;

import java.util.List;

public interface ListAllVotesUseCase extends UseCase<ListAllVotesCommand, List<Vote>> {}
