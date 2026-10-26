package org.cq.contactly.controller;

import org.cq.contactly.dto.request.CreateContactRequest;
import org.cq.contactly.dto.request.UpdateContactRequest;
import org.cq.contactly.dto.response.ContactResponse;
import org.cq.contactly.service.ContactService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(
            @Valid @RequestBody CreateContactRequest request
    ) {
        ContactResponse response = contactService.createContact(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(
            @PathVariable UUID id
    ) {
        ContactResponse response = contactService.getContactById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAllContacts() {
        List<ContactResponse> contacts = contactService.getAllContacts();

        return ResponseEntity.ok(contacts);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateContactRequest request
    ) {
        return ResponseEntity.ok(contactService.updateContact(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable UUID id
    ) {
        contactService.deleteContact(id);

        return ResponseEntity.noContent().build();
    }
}