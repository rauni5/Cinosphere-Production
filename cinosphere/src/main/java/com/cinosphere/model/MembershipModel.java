package com.cinosphere.model;

import jakarta.persistence.*;

/**
 * Model class representing database table Membership and its attributes
 */
@Entity
@Table(name = "membership")
public class MembershipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private int membershipId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "membership_type")
    private String membershipType;

    @Column(name = "membership_status")
    private String membershipStatus;

    @Column(name = "total_loyalty_points")
    private int totalLoyaltyPoints;

    @Column(name = "discount_percentage")
    private double discountPercentage;
    /**
     * Returns the unique membership ID.
     * 
     * @return int
     */
    public int getMembershipId() { 
    	return membershipId; 
    }

    /**
     * Sets the unique membership ID.
     * 
     * @param membershipId
     */
    public void setMembershipId(int membershipId) { 
    	this.membershipId = membershipId; 
    }

    /**
     * Returns the user ID associated with the membership.
     * 
     * @return int
     */
    public int getUserId() { 
    	return userId; 
    }

    /**
     * Sets the user ID associated with the membership.
     * 
     * @param customerId
     */
    public void setUserId(int customerId) {
    	this.userId = customerId; 
    }

    /**
     * Returns the membership type.
     * 
     * @return String
     */
    public String getMembershipType() { 
    	return membershipType; 
    }

    /**
     * Sets the membership type.
     * 
     * @param membershipType
     */
    public void setMembershipType(String membershipType) { 
    	this.membershipType = membershipType; 
    }

    /**
     * Returns the membership status.
     * 
     * @return String
     */
    public String getMembershipStatus() {
    	return membershipStatus; 
    }

    /**
     * Sets the membership status.
     * 
     * @param membershipStatus
     */
    public void setMembershipStatus(String membershipStatus) { 
    	this.membershipStatus = membershipStatus; 
    }

    /**
     * Returns the total loyalty points.
     * 
     * @return int
     */
    public int getTotalLoyaltyPoints() { 
    	return totalLoyaltyPoints; 
    }

    /**
     * Sets the total loyalty points.
     * 
     * @param totalLoyaltyPoints
     */
    public void setTotalLoyaltyPoints(int totalLoyaltyPoints) { 
    	this.totalLoyaltyPoints = totalLoyaltyPoints; 
    }

    /**
     * Returns the discount percentage.
     * 
     * @return double
     */
    public double getDiscountPercentage() { 
    	return discountPercentage; 
    }

    /**
     * Sets the discount percentage.
     * 
     * @param discountPercentage
     */
    public void setDiscountPercentage(double discountPercentage) { 
    	this.discountPercentage = discountPercentage;
    }
}