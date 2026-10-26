
package org.cq.contactly.service;

import org.cq.contactly.dto.request.CreateContactRequest;
import org.cq.contactly.dto.request.UpdateContactRequest;
import org.cq.contactly.dto.response.ContactResponse;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    ContactResponse createContact(CreateContactRequest request);

    ContactResponse getContactById(UUID id);

    List<ContactResponse> getAllContacts();

    ContactResponse updateContact(UUID id, UpdateContactRequest request);

    void deleteContact(UUID id);
}
