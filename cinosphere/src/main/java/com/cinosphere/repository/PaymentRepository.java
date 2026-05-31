package com.cinosphere.repository;

import com.cinosphere.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentModel, Integer> {

    // SELECT * FROM payment WHERE booking_id = ?
    Optional<PaymentModel> findByBookingId(int bookingId);

    // SELECT * FROM payment WHERE payment_id = ?
    Optional<PaymentModel> findByPaymentId(int paymentId);

    // SELECT * FROM payment
    List<PaymentModel> findAll();

    // Optional: if you want filtering by status later
    List<PaymentModel> findByPaymentStatus(String paymentStatus);
}