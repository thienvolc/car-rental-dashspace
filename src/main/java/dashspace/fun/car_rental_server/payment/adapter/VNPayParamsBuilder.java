package dashspace.fun.car_rental_server.payment.adapter;

import dashspace.fun.car_rental_server.config.prop.PaymentProperties;
import dashspace.fun.car_rental_server.payment.VNPayUtils;
import dashspace.fun.car_rental_server.payment.dto.PaymentCommand;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

class VNPayParamsBuilder {

    private final PaymentProperties.PaymentProviderProperties provider;

    VNPayParamsBuilder(PaymentProperties.PaymentProviderProperties provider) {
        this.provider = provider;
    }

    public Map<String, String> buildParams(PaymentCommand paymentCommand, String clientIpAdress) {
        String transactionRef = VNPayUtils.getRandomNumber(8);
        long amount = (long) (paymentCommand.amount() * 100);

        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", this.provider.getTerminalCode());
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", "VND");

        if (StringUtils.hasLength(paymentCommand.bankCode())) {
            params.put("vnp_BankCode", paymentCommand.bankCode());
        }

        params.put("vnp_Locale", getLocate(paymentCommand.language()));
        params.put("vnp_TxnRef", transactionRef);
        params.put("vnp_OrderInfo", "Thanh toan don hang " + transactionRef);
        params.put("vnp_OrderType", "other");
        params.put("vnp_ReturnUrl", this.provider.getReturnUrl());
        params.put("vnp_IpAddr", clientIpAdress);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.MINUTE, 15);
        String expireDate = dateFormat.format(calendar.getTime());

        params.put("vnp_CreateDate", createDate);
        params.put("vnp_ExpireDate", expireDate);

        return params;
    }

    private String getLocate(String language) {
        return (StringUtils.hasLength(language) ? language : "vn");
    }
}
