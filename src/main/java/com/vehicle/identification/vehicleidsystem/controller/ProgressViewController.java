package com.vehicle.identification.vehicleidsystem.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ProgressViewController {

    @FXML
    private ProgressBar vehicleProgressBar;

    @FXML
    private Label vehicleProgressLabel;

    @FXML
    private ProgressBar insuranceProgressBar;

    @FXML
    private Label insuranceProgressLabel;

    @FXML
    private ProgressBar serviceProgressBar;

    @FXML
    private Label serviceProgressLabel;

    @FXML
    private Label totalVehiclesLabel;

    @FXML
    private Label activeServicesLabel;

    @FXML
    private Label insuranceCoverageLabel;

    @FXML
    private Label violationsLabel;

    @FXML
    private Label dbUsageLabel;

    @FXML
    private Label insurancePercentLabel;

    @FXML
    private Label servicePercentLabel;

    @FXML
    private Label uptimeLabel;

    @FXML
    private ProgressIndicator databaseIndicator;

    @FXML
    private ProgressIndicator backupIndicator;

    @FXML
    private ProgressIndicator updateIndicator;

    @FXML
    private Button dropShadowButton;

    @FXML
    private Button fadeButton;

    @FXML
    public void initialize() {
        // Apply DropShadow effect to button
        applyDropShadow();

        // Setup FadeTransition for fade button
        setupFadeTransition();

        // Simulate progress updates
        simulateProgressUpdates();
    }

    private void applyDropShadow() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.4, 0.4));

        dropShadowButton.setEffect(dropShadow);
    }

    private void setupFadeTransition() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), fadeButton);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.3);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    private void simulateProgressUpdates() {
        // Simulate database sync progress
        databaseIndicator.setProgress(0.5);

        // Simulate backup progress
        backupIndicator.setProgress(0.3);

        // Update complete
        updateIndicator.setProgress(1.0);
    }

    @FXML
    private void handleDropShadowClick() {
        // Change drop shadow color on click
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(15.0);
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0.2, 0.6, 0.8));

        dropShadowButton.setEffect(dropShadow);

        // Reset after 1 second
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> applyDropShadow());
        pause.play();
    }

    @FXML
    private void handleFadeClick() {
        // Create a burst fade effect
        FadeTransition burstFade = new FadeTransition(Duration.millis(500), fadeButton);
        burstFade.setFromValue(1.0);
        burstFade.setToValue(0.1);
        burstFade.setCycleCount(2);
        burstFade.setAutoReverse(true);
        burstFade.play();
    }
}