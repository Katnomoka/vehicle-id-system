module com.vehicle.identification.vehicleidsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.postgresql.jdbc;  // 👈 Add this line

    opens com.vehicle.identification.vehicleidsystem.controller to javafx.fxml;
    opens com.vehicle.identification.vehicleidsystem to javafx.fxml;

    exports com.vehicle.identification.vehicleidsystem;
    exports com.vehicle.identification.vehicleidsystem.model;
    exports com.vehicle.identification.vehicleidsystem.dao;
    exports com.vehicle.identification.vehicleidsystem.util;
}