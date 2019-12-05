package com.pritamprasad.authservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Token implements Serializable {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "token_data")
    private String tokenData;

    @Column(name = "timestamp")
    private Long timestamp;
}
