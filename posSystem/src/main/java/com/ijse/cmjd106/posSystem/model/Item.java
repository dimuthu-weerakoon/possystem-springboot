package com.ijse.cmjd106.posSystem.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, name = "item_name")
    private String itemName;
    @Column(nullable = true, name = "description")
    private String description;
    @Column(nullable = false, name = "unit_price")
    private double unitPrice;
    @Column(nullable = false, name = "current_stock")
    private Integer currentStock;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<SaleLineItem> saleLineItem;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<StockLineItem> stockLineItems;


}
