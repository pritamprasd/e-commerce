package com.pritamprasad.ordersservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import static com.pritamprasad.ordersservice.models.OrderStatus.NEW;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "address_id")
    private UUID addressId;

    @Column(name = "order_status")
    private OrderStatus orderStatus = NEW;

    @Column(name = "products")
    private ArrayList<UUID> products;

    @Column(name = "order_total")
    private Double orderTotal;

    @Column(name = "created")
    private Long createdTimestamp;

    @Column(name = "modified")
    private Long modifiedTimestamp;

    //TODO: add order id column

}
