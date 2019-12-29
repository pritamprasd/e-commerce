package com.pritamprasad.ordersservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Cart implements Serializable {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "products")
    private ArrayList<UUID> productsInCart;

    @Column(name = "cart_total")
    private Double cartTotal = 0.0;

    @Column(name = "last_updated")
    private Long lastUpdated;
}
