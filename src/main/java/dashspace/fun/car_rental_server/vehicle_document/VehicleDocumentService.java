package dashspace.fun.car_rental_server.vehicle_document;

import dashspace.fun.car_rental_server.vehicle_document.request.CreateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.request.UpdateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.response.VehicleDocumentDto;

public interface VehicleDocumentService {
    VehicleDocumentDto create(CreateVehicleDocumentRequest request);
    VehicleDocumentDto update(UpdateVehicleDocumentRequest request);
}
