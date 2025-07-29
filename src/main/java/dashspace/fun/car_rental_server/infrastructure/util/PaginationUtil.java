package dashspace.fun.car_rental_server.infrastructure.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PaginationUtil {

    private static final String SORT_EXPRESSION_PATTERN = "(\\w+?)(:)(.*)";

    public static int toZeroBasedPageIndex(int pageNo) {
        return (pageNo > 0 ? pageNo - 1 : 0);
    }

    public static Sort buildSort(@Nullable String... sortExpressions) {
        sortExpressions = ArrayUtils.nullToEmpty(sortExpressions);

        if (ArrayUtils.isEmpty(sortExpressions)) {
            return Sort.unsorted();
        }
        return Sort.by(buildOrders(sortExpressions));
    }

    private static List<Sort.Order> buildOrders(String[] sortExperessions) {
        return Stream.of(sortExperessions)
                .map(PaginationUtil::buildOrder)
                .filter(Objects::nonNull)
                .toList();
    }

    @Nullable
    private static Sort.Order buildOrder(String sortExpression) {
        if (ObjectUtils.isEmpty(sortExpression)) {
            return null;
        }

        var matcher = Pattern.compile(SORT_EXPRESSION_PATTERN).matcher(sortExpression);
        if (!matcher.matches()) {
            return null;
        }

        String sortBy = matcher.group(1);
        String direction = matcher.group(3).toUpperCase();
        return new Sort.Order(Sort.Direction.valueOf(direction), sortBy);
    }
}
