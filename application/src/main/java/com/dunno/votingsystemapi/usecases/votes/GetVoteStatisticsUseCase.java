package com.dunno.votingsystemapi.usecases.votes;

import com.dunno.votingsystemapi.commands.votes.GetVoteStatisticsCommand;
import com.dunno.votingsystemapi.models.VoteStatistics;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface GetVoteStatisticsUseCase extends UseCase<GetVoteStatisticsCommand, VoteStatistics> {}
