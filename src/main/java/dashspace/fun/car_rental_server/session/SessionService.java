package dashspace.fun.car_rental_server.session;

import dashspace.fun.car_rental_server.session.request.SessionCommand;

public interface SessionService {
    void persistSession(SessionCommand sessionCommand);
    void invalidateAllSessions(Integer userId);
}
