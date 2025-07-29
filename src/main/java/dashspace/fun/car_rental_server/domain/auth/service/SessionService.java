package dashspace.fun.car_rental_server.domain.auth.service;

import dashspace.fun.car_rental_server.domain.auth.dto.SessionDto;
import dashspace.fun.car_rental_server.domain.auth.entity.Session;
import dashspace.fun.car_rental_server.domain.auth.mapper.SessionMapper;
import dashspace.fun.car_rental_server.domain.auth.repository.SessionRepository;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;
    private final SessionRepository repository;

    private final SessionMapper mapper;

    public void persistSession(SessionDto sessionDto) {
        var session = mapper.toSession(sessionDto);
        var userRef = userRepository.getReferenceById(sessionDto.userId());
        session.setUser(userRef);

        upsertSession(session);
    }

    private void upsertSession(Session session) {
        deletePreviousSession(session);
        repository.save(session);
    }

    private void deletePreviousSession(Session session) {
        repository.deleteByUserAndIpAddress(session.getUser(), session.getIpAddress());
    }

    public void deleteSessionsByUserId(Integer userId) {
        var userRef = userRepository.getReferenceById(userId);
        repository.deleteAllByUser(userRef);
    }
}
