package com.cinosphere.dto;

/**
 * Flat stats payload returned by GET /api/admin/stats.
 * Used by AdminPanel.jsx to populate the four metric cards.
 */
public class AdminStatsDto {

    private double revenueToday;
    private double revenueYesterday;
    private String revenueChange;

    private int ticketsSoldToday;
    private int ticketsSoldYesterday;
    private String ticketsChange;

    private int newMembersToday;
    private int newMembersYesterday;
    private String usersChange;

    private int totalBookings;

    // ---- computed convenience fields (set by controller) ----

    public void computeChanges() {
        this.revenueChange  = formatChange(revenueToday,      revenueYesterday,  "Rs %.0f");
        this.ticketsChange  = formatChange(ticketsSoldToday,  ticketsSoldYesterday, "%d tickets");
        this.usersChange    = formatChange(newMembersToday,   newMembersYesterday,  "%d users");
    }

    private String formatChange(double today, double yesterday, String fmt) {
        if (yesterday == 0) return today > 0 ? "↑ New activity" : "No data";
        double pct = ((today - yesterday) / yesterday) * 100;
        return String.format((pct >= 0 ? "↑ %.0f%%" : "↓ %.0f%%"), Math.abs(pct));
    }

    // ---- getters / setters ----

    public double getRevenueToday()          { return revenueToday; }
    public void   setRevenueToday(double v)  { this.revenueToday = v; }

    public double getRevenueYesterday()          { return revenueYesterday; }
    public void   setRevenueYesterday(double v)  { this.revenueYesterday = v; }

    public String getRevenueChange()         { return revenueChange; }
    public void   setRevenueChange(String v) { this.revenueChange = v; }

    public int  getTicketsSoldToday()        { return ticketsSoldToday; }
    public void setTicketsSoldToday(int v)   { this.ticketsSoldToday = v; }

    public int  getTicketsSoldYesterday()        { return ticketsSoldYesterday; }
    public void setTicketsSoldYesterday(int v)   { this.ticketsSoldYesterday = v; }

    public String getTicketsChange()         { return ticketsChange; }
    public void   setTicketsChange(String v) { this.ticketsChange = v; }

    public int  getNewMembersToday()         { return newMembersToday; }
    public void setNewMembersToday(int v)    { this.newMembersToday = v; }

    public int  getNewMembersYesterday()         { return newMembersYesterday; }
    public void setNewMembersYesterday(int v)    { this.newMembersYesterday = v; }

    public String getUsersChange()           { return usersChange; }
    public void   setUsersChange(String v)   { this.usersChange = v; }

    public int  getTotalBookings()           { return totalBookings; }
    public void setTotalBookings(int v)      { this.totalBookings = v; }
}
