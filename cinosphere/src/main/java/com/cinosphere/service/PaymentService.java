package com.cinosphere.service;

import com.cinosphere.model.PaymentModel;
import com.cinosphere.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles payment queries.
 *
 * Note: payment *creation* now happens inside BookingService.createBooking()
 * as part of the single @Transactional booking flow.
 * This service is kept for read operations: admin revenue reports,
 * payment history per booking, etc.
 *
 * Changes from original PaymentService:
 *  - createPayment() removed — creation is handled transactionally in BookingService.
 *  - Added getPaymentByBookingId() and getAll() for admin reporting.
 *  - Injected PaymentRepository instead of new PaymentDAO().
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentModel getPaymentByBookingId(int bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for booking: " + bookingId));
    }

    public List<PaymentModel> getAllPayments() {
        return paymentRepository.findAll();
    }
}