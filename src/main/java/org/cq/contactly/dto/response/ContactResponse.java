
package org.cq.contactly.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ContactResponse {

    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String company;
    private String jobTitle;

    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    private String notes;

    private boolean favorite;

    private Instant createdAt;
    private Instant updatedAt;
}
