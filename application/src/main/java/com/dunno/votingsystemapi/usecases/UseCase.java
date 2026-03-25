package com.dunno.votingsystemapi.usecases;

public interface UseCase <I, O>{
    O execute(I command);
}
