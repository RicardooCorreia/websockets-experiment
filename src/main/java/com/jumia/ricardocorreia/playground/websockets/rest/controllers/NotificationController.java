package com.jumia.ricardocorreia.playground.websockets.rest.controllers;

import com.jumia.ricardocorreia.playground.websockets.domain.humanresources.NotificationModel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {

    private final NotificationModel humanResourcesModel;

    @PostMapping("/notifications/submissions")
    public void submitRequest(@Valid @RequestBody SubmissionRequest request) {

        humanResourcesModel.processRequest(request.getReceiver(), request.getText());
    }

    @Value
    @Builder
    public static class SubmissionRequest {

        @NotBlank
        String receiver;

        @NotBlank
        String text;
    }
}
