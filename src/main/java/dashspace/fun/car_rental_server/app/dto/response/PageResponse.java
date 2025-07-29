package dashspace.fun.car_rental_server.app.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private List<T> items;

    public static <T> PageResponse<T> fromPageAndItems(Page<?> pageable, List<T> items) {
        return PageResponse.<T>builder()
                .items(items)
                .pageNo(pageable.getNumber())
                .pageSize(pageable.getSize())
                .totalPage(pageable.getTotalPages())
                .build();
    }
}
