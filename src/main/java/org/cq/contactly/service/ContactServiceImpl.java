package org.cq.contactly.service;

import org.cq.contactly.dto.request.CreateContactRequest;
import org.cq.contactly.dto.response.ContactResponse;
import org.cq.contactly.entities.Contact;
import org.cq.contactly.mapper.ContactMapper;
import org.cq.contactly.repository.ContactRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(
            ContactRepository contactRepository,
            ContactMapper contactMapper
    ) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public ContactResponse createContact(CreateContactRequest request) {
        Contact contact = contactMapper.toEntity(request);

        Contact savedContact = contactRepository.save(contact);

        return contactMapper.toResponse(savedContact);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactResponse getContactById(UUID id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Contact not found with id: " + id
                ));

        return contactMapper.toResponse(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toResponse)
                .toList();
    }

    @Override
    public ContactResponse updateContact(
            UUID id,
            CreateContactRequest request
    ) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Contact not found with id: " + id
                ));

        Contact updatedContact = contactMapper.toEntity(request);

        updatedContact.setId(existingContact.getId());
        updatedContact.setFavorite(existingContact.isFavorite());
        updatedContact.setCreatedAt(existingContact.getCreatedAt());

        Contact savedContact = contactRepository.save(updatedContact);

        return contactMapper.toResponse(savedContact);
    }

    @Override
    public void deleteContact(UUID id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Contact not found with id: " + id
                ));

        contactRepository.delete(contact);
    }
}