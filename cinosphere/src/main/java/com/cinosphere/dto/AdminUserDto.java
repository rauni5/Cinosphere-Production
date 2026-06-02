package com.cinosphere.dto;

/**
 * Enriched user row for the admin user management table.
 * Combines UsersModel + MembershipModel into one flat object
 * so AdminPanel.jsx can render each row without extra fetches.
 */
public class AdminUserDto {

    private int    userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String userRole;
    private boolean active;

    // From MembershipModel
    private String membershipType;
    private int    totalLoyaltyPoints;

    // From BookingRepository
    private int    totalBookings;

    // ---- getters / setters ----

    public int     getUserId()               { return userId; }
    public void    setUserId(int v)          { this.userId = v; }

    public String  getUsername()             { return username; }
    public void    setUsername(String v)     { this.username = v; }

    public String  getEmail()                { return email; }
    public void    setEmail(String v)        { this.email = v; }

    public String  getFirstName()            { return firstName; }
    public void    setFirstName(String v)    { this.firstName = v; }

    public String  getLastName()             { return lastName; }
    public void    setLastName(String v)     { this.lastName = v; }

    public String  getUserRole()             { return userRole; }
    public void    setUserRole(String v)     { this.userRole = v; }

    public boolean isActive()                { return active; }
    public void    setActive(boolean v)      { this.active = v; }

    public String  getMembershipType()       { return membershipType; }
    public void    setMembershipType(String v){ this.membershipType = v; }

    public int     getTotalLoyaltyPoints()        { return totalLoyaltyPoints; }
    public void    setTotalLoyaltyPoints(int v)   { this.totalLoyaltyPoints = v; }

    public int     getTotalBookings()        { return totalBookings; }
    public void    setTotalBookings(int v)   { this.totalBookings = v; }
}
