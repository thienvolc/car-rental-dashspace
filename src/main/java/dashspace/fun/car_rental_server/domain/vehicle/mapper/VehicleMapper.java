package dashspace.fun.car_rental_server.domain.vehicle.mapper;

import dashspace.fun.car_rental_server.domain.location.mapper.LocationMapper;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.vehicle.dto.*;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.CreateVehicleRequest;
import dashspace.fun.car_rental_server.domain.vehicle.entity.Vehicle;
import dashspace.fun.car_rental_server.domain.vehicle.entity.VehicleImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleMapper {

    private final LocationMapper locationMapper;
    private final VehicleDocumentMapper vehicleDocumentMapper;

    public Vehicle toVehicle(CreateVehicleRequest request) {
        return Vehicle.builder()
                .brand(request.brand())
                .model(request.model())
                .seatCount(request.seatCount())
                .licensePlate(request.licensePlate())
                .description(request.description())
                .features(request.features())
                .fuelType(request.fuelType())
                .transmission(request.transmission())
                .dailyRate(BigDecimal.valueOf(request.dailyRate()))
                .fuelConsumption(BigDecimal.valueOf(request.fuelConsumption()))
                .manufactureYear(request.manufactureYear())
                .build();
    }

    public VehicleDto toDto(Vehicle vehicle) {
        var imageDtos = toImageDtos(vehicle.getImages());
        var locationDo = locationMapper.toDto(vehicle.getLocation());
        var vehicleDocumentDto = vehicleDocumentMapper.toDto(vehicle.getVehicleDocument());
        return VehicleDto.builder()
                .id(vehicle.getId())
                .fuelConsumption(vehicle.getFuelConsumption().doubleValue())
                .manufactureYear(vehicle.getManufactureYear())
                .fuelType(vehicle.getFuelType())
                .seatCount(vehicle.getSeatCount())
                .model(vehicle.getModel())
                .licensePlate(vehicle.getLicensePlate())
                .features(vehicle.getFeatures())
                .dailyRate(vehicle.getDailyRate().doubleValue())
                .brand(vehicle.getBrand())
                .description(vehicle.getDescription())
                .transmission(vehicle.getTransmission())
                .status(vehicle.getStatus())
                .approvalStatus(vehicle.getApprovalStatus())
                .document(vehicleDocumentDto)
                .location(locationDo)
                .images(imageDtos)
                .build();
    }

    public ModerationViewVehicleDto toModerationViewVehicleDto(Vehicle vehicle) {
        var imageDtos = toImageDtos(vehicle.getImages());
        var locationDo = locationMapper.toDto(vehicle.getLocation());
        var vehicleDocumentDto = vehicleDocumentMapper.toDto(vehicle.getVehicleDocument());
        return ModerationViewVehicleDto.builder()
                .id(vehicle.getId())
                .ownerId(vehicle.getOwner().getId())
                .fuelConsumption(vehicle.getFuelConsumption().doubleValue())
                .manufactureYear(vehicle.getManufactureYear())
                .fuelType(vehicle.getFuelType())
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
                .document(vehicleDocumentDto)
                .location(locationDo)
                .images(imageDtos)
                .build();
    }

    public SearchVehicleDto toSearchVehicleDto(Vehicle vehicle) {
        var imageDtos = toImageDtos(vehicle.getImages());
        var locationDo = locationMapper.toDto(vehicle.getLocation());
        var ownerDto = toVehicleOwnerDto(vehicle.getOwner());
        return SearchVehicleDto.builder()
                .id(vehicle.getId())
                .owner(ownerDto)
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
                .location(locationDo)
                .images(imageDtos)
                .build();
    }

    private VehicleOwnerDto toVehicleOwnerDto(User owner) {
        return VehicleOwnerDto.builder()
                .id(owner.getId())
                .name(owner.getUsername())
                .avatarUrl(owner.getAvatarUrl())
                .build();
    }

    private List<VehicleImageDto> toImageDtos(List<VehicleImage> images) {
        return images.stream()
                .map(i -> new VehicleImageDto(i.getImageUrl(), i.getSortOrder()))
                .toList();
    }
}
