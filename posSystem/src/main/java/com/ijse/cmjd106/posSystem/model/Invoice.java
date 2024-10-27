package com.ijse.cmjd106.posSystem.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ijse.cmjd106.posSystem.enums.InvoiceType;
import com.ijse.cmjd106.posSystem.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "invoices")
@EntityListeners(AuditingEntityListener.class)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, name = "invoice_number")
    private String invoiceNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type", nullable = false)
    private InvoiceType invoiceType;
    @Column(name = "reference_number", unique = true, nullable = false)
    private String referenceNumber;
    @CreatedDate
    @Column(nullable = false, name = "issued_at",updatable = false)
    private LocalDateTime issuedAt;
    @Column(nullable = false, name = "total_amount")
    private double totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_status")
    private PaymentStatus paymentStatus;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "invoice_invoiceLineItems", joinColumns = @JoinColumn(nullable = false, name = "invoice_id"), inverseJoinColumns = @JoinColumn(nullable = false, name = "invoiceLineItem_id"))
    private List<InvoiceLineItem> invoiceLineItems;

}
