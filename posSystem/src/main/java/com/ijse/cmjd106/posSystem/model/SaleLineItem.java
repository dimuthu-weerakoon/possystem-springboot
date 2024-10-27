package com.ijse.cmjd106.posSystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sale_line_items")
public class SaleLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;
    @Column(name = "quantity",nullable = false)
    private Integer quantity;
    @Column(name = "total_amount",nullable = false)
    private  double totalAmount;
    // @ManyToOne
    // @JoinColumn(name = "discount_id",nullable = true)
    // private Discount discount;
}
