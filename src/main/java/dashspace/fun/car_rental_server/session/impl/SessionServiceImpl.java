package dashspace.fun.car_rental_server.session.impl;

import dashspace.fun.car_rental_server.session.*;
import dashspace.fun.car_rental_server.session.request.SessionCommand;
import dashspace.fun.car_rental_server.session.response.SessionDto;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    @Override
    public void persistSession(SessionCommand sessionCommand) {
        SessionDto sessionDto = this.sessionMapper.toSessionDto(sessionCommand);
        upsertSession(sessionDto);
    }

    private void upsertSession(SessionDto sessionDto) {
        clearPreviousSession(sessionDto.userId(), sessionDto.ipAddress());

        User userRef = this.userRepository.getReferenceById(sessionDto.userId());
        Session session = this.sessionMapper.toSession(sessionDto, userRef);

        this.sessionRepository.save(session);
    }

    private void clearPreviousSession(Integer userId, String ipAddress) {
        User userRef = this.userRepository.getReferenceById(userId);
        this.sessionRepository.deleteByUserAndIpAddress(userRef, ipAddress);
    }


    @Override
    public void invalidateAllSessions(Integer userId) {
        User userRef = this.userRepository.getReferenceById(userId);
        this.sessionRepository.deleteAllByUser(userRef);
    }
}
