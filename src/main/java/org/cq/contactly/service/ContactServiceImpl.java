package org.cq.contactly.service;

import org.cq.contactly.dto.request.CreateContactRequest;
import org.cq.contactly.dto.request.UpdateContactRequest;
import org.cq.contactly.dto.response.ContactResponse;
import org.cq.contactly.entities.Contact;
import org.cq.contactly.exception.DuplicateResourceException;
import org.cq.contactly.exception.ResourceNotFoundException;
import org.cq.contactly.mapper.ContactMapper;
import org.cq.contactly.repository.ContactRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

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
        if (hasText(request.getEmail()) && contactRepository.existsByEmailIgnoreCase(request.getEmail().trim())) {
            throw new DuplicateResourceException("A contact with this email already exists");
        }
        Contact contact = contactMapper.toEntity(request);
        return contactMapper.toResponse(contactRepository.save(contact));
    }

    @Override
    public ContactResponse updateContact(UUID id, UpdateContactRequest request) {
        Contact contact = findContactOrThrow(id);
        if (hasText(request.getEmail())
                && contactRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail().trim(), id)) {
            throw new DuplicateResourceException("A contact with this email already exists");
        }
        contactMapper.updateEntity(request, contact);
        return contactMapper.toResponse(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactResponse getContactById(UUID id) {
        return contactMapper.toResponse(findContactOrThrow(id));
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
    public void deleteContact(UUID id) {
        contactRepository.delete(findContactOrThrow(id));
    }

    private Contact findContactOrThrow(UUID id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }
}