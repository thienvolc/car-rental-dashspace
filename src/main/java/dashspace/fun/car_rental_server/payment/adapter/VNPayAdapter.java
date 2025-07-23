package dashspace.fun.car_rental_server.payment.adapter;

import dashspace.fun.car_rental_server.config.prop.PaymentProperties;
import dashspace.fun.car_rental_server.config.prop.PaymentProperties.PaymentProviderProperties;
import dashspace.fun.car_rental_server.payment.VNPayUtils;
import dashspace.fun.car_rental_server.payment.dto.PaymentCommand;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class VNPayAdapter {

    private final PaymentProviderProperties vnPayProperties;
    private final VNPayParamsBuilder pramasBuilder;

    private VNPayAdapter(PaymentProperties paymentProperties) {
        this.vnPayProperties = paymentProperties.getProviders().get("vnpay");
        this.pramasBuilder = new VNPayParamsBuilder(vnPayProperties);
    }

    public String generatePaymentUrl(PaymentCommand paymentCommand, String clientIpAdress) {
        Map<String, String> params = this.pramasBuilder.buildParams(paymentCommand,
                clientIpAdress);
        List<String> sortedKeys = sanitizedAndsortedKeys(params);

        String secureHash = buildSecureHash(params, sortedKeys);
        String queryData = buildQuery(params, sortedKeys, secureHash);

        return this.vnPayProperties.getPayUrl() + "?" + queryData;
    }

    private List<String> sanitizedAndsortedKeys(Map<String, String> params) {
        return params.keySet().stream()
                .filter(k -> StringUtils.hasLength(params.get(k)))
                .sorted().toList();
    }

    private String buildSecureHash(Map<String, String> params, List<String> sortedKeys) {
        String hashData = sortedKeys.stream()
                .map(k -> k + "=" + encodeUSASCII(params.get(k)))
                .collect(Collectors.joining("&"));
        return VNPayUtils.hmacSHA512(this.vnPayProperties.getHashSecret(), hashData);
    }

    private String buildQuery(Map<String, String> params, List<String> sortedKeys,
                              String secureHash) {

        String query = sortedKeys.stream()
                .map(k -> encodeUSASCII(k) + "=" + encodeUSASCII(params.get(k)))
                .collect(Collectors.joining("&"));
        return query + "&vnp_SecureHash=" + secureHash;
    }

    private String encodeUSASCII(String value) {
        return URLEncoder.encode(value, StandardCharsets.US_ASCII);
    }
}
