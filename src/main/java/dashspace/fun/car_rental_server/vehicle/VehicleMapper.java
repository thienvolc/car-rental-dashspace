package dashspace.fun.car_rental_server.vehicle;

import dashspace.fun.car_rental_server.location.LocationMapper;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.vehicle.request.CreateVehicleRequest;
import dashspace.fun.car_rental_server.vehicle.response.AdminViewVehicleDto;
import dashspace.fun.car_rental_server.vehicle.response.SearchVehicleDto;
import dashspace.fun.car_rental_server.vehicle.response.VehicleImageDto;
import dashspace.fun.car_rental_server.vehicle.response.VehicleOwnerDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class VehicleMapper {
    private final LocationMapper locationMapper;

    public VehicleMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public Vehicle toVehicle(User userRef, CreateVehicleRequest request) {
        return Vehicle.builder()
                .owner(userRef)
                .brand(request.brand())
                .licensePlate(request.licensePlate())
                .dailyRate(BigDecimal.valueOf(request.dailyRate()))
                .description(request.description())
                .features(request.features())
                .fuelType(request.fuelType())
                .fuelConsumption(BigDecimal.valueOf(request.fuelConsumption()))
                .model(request.model())
                .manufactureYear(request.manufactureYear())
                .transmission(request.transmission())
                .seatCount(request.seatCount())
                .build();
    }

    public AdminViewVehicleDto toAdminViewVehicleDto(Vehicle vehicle) {
        return AdminViewVehicleDto.builder()
                .id(vehicle.getId())
                .ownerId(vehicle.getOwner().getId())
                .images(toImageDtos(vehicle.getImages()))
                .location(this.locationMapper.toLocationDto(vehicle.getLocation()))
                .fuelConsumption(vehicle.getFuelConsumption().doubleValue())
                .manufactureYear(vehicle.getManufactureYear())
                .fuelType(vehicle.getFuelType())
                .seatCount(vehicle.getSeatCount())
                .model(vehicle.getModel())
                .licensePlate(vehicle.getLicensePlate())
                .seatCount(vehicle.getSeatCount())
                .features(vehicle.getFeatures())
                .dailyRate(vehicle.getDailyRate().doubleValue())
                .brand(vehicle.getBrand())
                .description(vehicle.getDescription())
                .transmission(vehicle.getTransmission())
                .status(vehicle.getStatus())
                .approvalStatus(vehicle.getApprovalStatus())
                .build();
    }

    private List<VehicleImageDto> toImageDtos(List<VehicleImage> images) {
        return images.stream()
                .map(img -> new VehicleImageDto(img.getImageUrl(), img.getSortOrder()))
                .toList();
    }

    public HostVehicleDto toHostVehicleDto(Vehicle vehicle) {
        return HostVehicleDto.builder()
                .id(vehicle.getId())
                .images(toImageDtos(vehicle.getImages()))
                .location(this.locationMapper.toLocationDto(vehicle.getLocation()))
                .fuelConsumption(vehicle.getFuelConsumption().doubleValue())
                .manufactureYear(vehicle.getManufactureYear())
                .fuelType(vehicle.getFuelType())
                .seatCount(vehicle.getSeatCount())
                .model(vehicle.getModel())
                .licensePlate(vehicle.getLicensePlate())
                .seatCount(vehicle.getSeatCount())
                .features(vehicle.getFeatures())
                .dailyRate(vehicle.getDailyRate().doubleValue())
                .brand(vehicle.getBrand())
                .description(vehicle.getDescription())
                .transmission(vehicle.getTransmission())
                .approvalStatus(vehicle.getApprovalStatus())
                .status(vehicle.getStatus())
                .build();
    }

    public SearchVehicleDto toSearchVehicleDto(Vehicle vehicle) {
        return SearchVehicleDto.builder()
                .id(vehicle.getId())
                .owner(toVehicleOwnerDto(vehicle.getOwner()))
                .location(this.locationMapper.toLocationDto(vehicle.getLocation()))
                .licensePlate(vehicle.getLicensePlate())
                .manufactureYear(vehicle.getManufactureYear())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .seatCount(vehicle.getSeatCount())
                .fuelType(vehicle.getFuelType())
                .transmission(vehicle.getTransmission())
                .fuelConsumption(vehicle.getFuelConsumption().doubleValue())
                .dailyRate(vehicle.getDailyRate().doubleValue())
                .description(vehicle.getDescription())
                .features(vehicle.getFeatures())
                .images(toImageDtos(vehicle.getImages()))
                .build();
    }

    private VehicleOwnerDto toVehicleOwnerDto(User owner) {
        return VehicleOwnerDto.builder()
                .id(owner.getId())
                .name(owner.getUsername())
                .avatarUrl(owner.getAvatarUrl())
                .build();
    }
}
