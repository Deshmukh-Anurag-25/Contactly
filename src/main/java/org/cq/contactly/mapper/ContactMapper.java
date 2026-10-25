package org.cq.contactly.mapper;

import org.cq.contactly.dto.request.CreateContactRequest;
import org.cq.contactly.dto.response.ContactResponse;
import org.cq.contactly.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "favorite", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Contact toEntity(CreateContactRequest request);

    ContactResponse toResponse(Contact contact);
}