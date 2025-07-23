package dashspace.fun.car_rental_server.common.util;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class PaginationUtils {

    private PaginationUtils() {
        /* Prevent instantiation */
    }

    private static final Pattern SORT_EXPRESSION_PATTERN = Pattern.compile(
            "(\\w+?)(:)(.*)");

    public static int toZeroBasedPageIndex(int pageNo) {
        return (pageNo > 0 ? pageNo - 1 : 0);
    }

    public static Sort buildSort(@Nullable String... sortExpressions) {
        if (hasSorts(sortExpressions)) {
            return Sort.by(buildOrders(sortExpressions));
        }
        return Sort.unsorted();
    }

    private static boolean hasSorts(String... sortExpressions) {
        return sortExpressions != null && sortExpressions.length > 0;
    }

    private static List<Sort.Order> buildOrders(@NotNull String... sortExperessions) {
        return Stream.of(sortExperessions)
                .map(PaginationUtils::buildOrder)
                .filter(Objects::nonNull)
                .toList();
    }

    @Nullable
    private static Sort.Order buildOrder(String sortExpression) {
        if (!StringUtils.hasLength(sortExpression)) {
            return null;
        }

        Matcher matcher = SORT_EXPRESSION_PATTERN.matcher(sortExpression);
        if (!matcher.find()) {
            return null;
        }

        String sortBy = matcher.group(1);
        String direction = matcher.group(3).toUpperCase();
        return new Sort.Order(Sort.Direction.valueOf(direction), sortBy);
    }
}
