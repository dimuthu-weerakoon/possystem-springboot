package com.ijse.cmjd106.posSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.cmjd106.posSystem.model.Invoice;
import java.util.List;
import java.time.LocalDateTime;
import com.ijse.cmjd106.posSystem.enums.PaymentStatus;
import com.ijse.cmjd106.posSystem.enums.InvoiceType;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByIssuedAt(LocalDateTime issuedAt);

    List<Invoice> findByPaymentStatus(PaymentStatus paymentStatus);

    Invoice findByReferenceNumber(String referenceNumber);

    List<Invoice> findByInvoiceType(InvoiceType invoiceType);
}
