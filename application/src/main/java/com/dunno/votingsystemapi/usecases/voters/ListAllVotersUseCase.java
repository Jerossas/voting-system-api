package com.dunno.votingsystemapi.usecases.voters;

import com.dunno.votingsystemapi.commands.voters.ListAllVotersCommand;
import com.dunno.votingsystemapi.models.Voter;
import com.dunno.votingsystemapi.usecases.UseCase;

import java.util.List;

public interface ListAllVotersUseCase extends UseCase<ListAllVotersCommand, List<Voter>> {
}
