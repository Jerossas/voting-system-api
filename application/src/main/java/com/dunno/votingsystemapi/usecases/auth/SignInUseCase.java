package com.dunno.votingsystemapi.usecases.auth;

import com.dunno.votingsystemapi.commands.auth.SignInCommand;
import com.dunno.votingsystemapi.models.User;
import com.dunno.votingsystemapi.usecases.UseCase;

public interface SignInUseCase extends UseCase<SignInCommand, User> {}
