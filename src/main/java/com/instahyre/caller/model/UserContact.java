package com.instahyre.caller.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_contact")
public class UserContact {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "spam_status")
    private boolean spamStatus;

}
