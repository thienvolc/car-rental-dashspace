package dashspace.fun.car_rental_server.vehicle;

import dashspace.fun.car_rental_server.vehicle.request.CreateVehicleRequest;
import dashspace.fun.car_rental_server.vehicle.request.RejectVehicleRequest;
import dashspace.fun.car_rental_server.vehicle.response.AdminViewVehicleDto;
import dashspace.fun.car_rental_server.vehicle.response.SearchVehicleDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {
    void registerVehicle(Integer userId, CreateVehicleRequest request,
                         MultipartFile... vehicleImages);

    List<AdminViewVehicleDto> getAllAdminViewVehiclesWithSortByMultipleColumns(
            int pageNo, int pageSize, String... sortBys);
    AdminViewVehicleDto getAdminViewVehicle(@NonNull Integer vehicleId);

    List<HostVehicleDto> getAllHostVehicles(int pageNo, int pageSize);
    HostVehicleDto getHostVehicle(Integer vehicleId);

    List<SearchVehicleDto> searchAllVehiclesWithSortByMultipleColumns(
            int pageNo, int pageSize, String... sortBys);

    void approveVehicle(Integer vehicleId, Integer verifiedById);
    void rejectVehicle(Integer vehicleId, Integer verifiedById,
                       RejectVehicleRequest request);
}
