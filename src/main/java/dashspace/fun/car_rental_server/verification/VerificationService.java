package dashspace.fun.car_rental_server.verification;

import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;
import dashspace.fun.car_rental_server.verification.request.ForgotPasswordRequest;
import dashspace.fun.car_rental_server.verification.request.HostRegistrationVerificationRequest;
import dashspace.fun.car_rental_server.verification.request.VerificationRequest;
import dashspace.fun.car_rental_server.verification.request.VerifyOtpRequest;
import dashspace.fun.car_rental_server.verification.response.VerificationResponse;

public interface VerificationService {
    void sendRegistrationOtp(VerificationRequest request);
    VerificationResponse verifyRegistrationOtp(VerifyOtpRequest request);
    boolean isRegistrationOtpValid(MessageContact messageContact, String code);

    void sendForgotPasswordOtp(ForgotPasswordRequest request);
    VerificationResponse verifyForgotPasswordOtp(VerifyOtpRequest request);
    boolean isForgotPasswordOtpValid(MessageContact messageContact, String code);

    void sendHostRegistrationOtp(Integer userId, HostRegistrationVerificationRequest
            request);
    VerificationResponse verifyHostRegistrationOtp(VerifyOtpRequest request);
    boolean isHostRegistrationOtpValid(MessageContact target);
}
