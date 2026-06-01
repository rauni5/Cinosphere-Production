package com.cinosphere.repository;

import com.cinosphere.model.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookingRepository extends JpaRepository<BookingModel, Integer> {

    // ================= BASIC QUERIES =================

    List<BookingModel> findByUserId(int userId);

    List<BookingModel> findByBookingStatus(String bookingStatus);

    List<BookingModel> findByUserIdAndBookingStatus(int userId, String bookingStatus);

    Optional<BookingModel> findByBookingId(int bookingId);

    // ================= UPCOMING BOOKINGS =================

    
    @Query("""
        SELECT DISTINCT b FROM BookingModel b
        JOIN TicketModel t ON t.bookingId = b.bookingId
        JOIN ShowtimeModel s ON s.showtimeId = t.showtimeId
        WHERE b.userId = :userId AND s.showDate >= CURRENT_DATE
        ORDER BY s.showDate ASC
    """)
    List<BookingModel> findUpcomingByUserId(int userId);

    @Query("""
        SELECT DISTINCT b FROM BookingModel b
        JOIN TicketModel t ON t.bookingId = b.bookingId
        JOIN ShowtimeModel s ON s.showtimeId = t.showtimeId
        WHERE b.userId = :userId AND s.showDate >= CURRENT_DATE
        ORDER BY s.showDate ASC
        LIMIT 1
    """)
    Optional<BookingModel> findLatestComingByUserId(int userId);

    // ================= COUNTS =================

    long count();

    int countByUserId(int userId);

    @Query("""
        SELECT COUNT(DISTINCT b.bookingId)
        FROM BookingModel b
        JOIN TicketModel t ON t.bookingId = b.bookingId
        JOIN ShowtimeModel s ON s.showtimeId = t.showtimeId
        WHERE b.userId = :userId AND s.showDate >= CURRENT_DATE
    """)
    int countUpcomingByUserId(int userId);

    @Query("""
        SELECT COUNT(b)
        FROM BookingModel b
        WHERE MONTH(b.bookingDate) = MONTH(CURRENT_DATE)
        AND YEAR(b.bookingDate) = YEAR(CURRENT_DATE)
    """)
    int countCurrentMonthBookings();

    @Query("""
        SELECT COUNT(b)
        FROM BookingModel b
        WHERE MONTH(b.bookingDate) = MONTH(CURRENT_DATE)
        AND YEAR(b.bookingDate) = YEAR(CURRENT_DATE)
        AND b.userId = :userId
    """)
    int countCurrentMonthBookingsByUserId(int userId);

    // ================= REVENUE =================

    @Query("""
        SELECT COALESCE(SUM(b.totalAmount), 0)
        FROM BookingModel b
        WHERE DATE(b.bookingDate) = :date
    """)
    double getRevenueByDate(LocalDate date);

    @Query("""
        SELECT COUNT(b)
        FROM BookingModel b
        WHERE DATE(b.bookingDate) = :date
    """)
    int countByBookingDate(LocalDate date);

    // ================= SEAT TRACKING =================

    @Query("""
        SELECT t.seatId
        FROM TicketModel t
        JOIN BookingModel b ON b.bookingId = t.bookingId
        WHERE t.showtimeId = :showtimeId
        AND b.bookingStatus = 'confirmed'
    """)
    Set<Integer> getConfirmedSeatIdsByShowtime(int showtimeId);

    // ================= SIMPLE FILTERS =================

    List<BookingModel> findByBookingDate(LocalDate bookingDate);
}