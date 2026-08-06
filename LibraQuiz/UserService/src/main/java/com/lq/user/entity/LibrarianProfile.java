package com.lq.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "librarian_profiles")
public class LibrarianProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private String employeeId;
    private String deskLocation;
    private String shiftTiming;

    public LibrarianProfile() {
    }

    public LibrarianProfile(Long id, UserProfile userProfile, String employeeId, String deskLocation, String shiftTiming) {
        this.id = id;
        this.userProfile = userProfile;
        this.employeeId = employeeId;
        this.deskLocation = deskLocation;
        this.shiftTiming = shiftTiming;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDeskLocation() {
        return deskLocation;
    }

    public void setDeskLocation(String deskLocation) {
        this.deskLocation = deskLocation;
    }

    public String getShiftTiming() {
        return shiftTiming;
    }

    public void setShiftTiming(String shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    public static LibrarianProfileBuilder builder() {
        return new LibrarianProfileBuilder();
    }

    public static class LibrarianProfileBuilder {
        private Long id;
        private UserProfile userProfile;
        private String employeeId;
        private String deskLocation;
        private String shiftTiming;

        public LibrarianProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LibrarianProfileBuilder userProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
            return this;
        }

        public LibrarianProfileBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public LibrarianProfileBuilder deskLocation(String deskLocation) {
            this.deskLocation = deskLocation;
            return this;
        }

        public LibrarianProfileBuilder shiftTiming(String shiftTiming) {
            this.shiftTiming = shiftTiming;
            return this;
        }

        public LibrarianProfile build() {
            return new LibrarianProfile(id, userProfile, employeeId, deskLocation, shiftTiming);
        }
    }
}
