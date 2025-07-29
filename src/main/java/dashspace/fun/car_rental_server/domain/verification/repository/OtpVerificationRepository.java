package dashspace.fun.car_rental_server.domain.verification.repository;

import dashspace.fun.car_rental_server.domain.verification.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Integer> {

    @Query("""
             SELECT o FROM OtpVerification o
             WHERE o.email = :email AND o.code = :code
               AND o.purpose = "REGISTRATION"
            """)
    Optional<OtpVerification> findRegistrationOtp(@Param("email") String email,
                                                  @Param("code") String code);

    @Query("""
             SELECT o FROM OtpVerification o
             WHERE o.email = :email AND o.code = :code
               AND o.purpose = "HOST_REGISTRATION"
            """)
    Optional<OtpVerification> findHostRegistrationOtp(@Param("email") String email,
                                                      @Param("code") String code);

    @Query("""
             SELECT o FROM OtpVerification o
             WHERE o.email = :email AND o.code = :code
               AND o.purpose = "FORGOT_PASSWORD"
            """)
    Optional<OtpVerification> findForgotPasswordOtp(@Param("email") String email,
                                                    @Param("code") String code);
}
