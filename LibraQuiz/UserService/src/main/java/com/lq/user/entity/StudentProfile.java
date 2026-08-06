package com.lq.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private String rollNumber;
    private String department;
    private String batchYear;
    private Integer currentBorrowedCount = 0;
    private Integer maxBorrowLimit = 5;

    public StudentProfile() {
    }

    public StudentProfile(Long id, UserProfile userProfile, String rollNumber, String department, String batchYear, Integer currentBorrowedCount, Integer maxBorrowLimit) {
        this.id = id;
        this.userProfile = userProfile;
        this.rollNumber = rollNumber;
        this.department = department;
        this.batchYear = batchYear;
        if (currentBorrowedCount != null) this.currentBorrowedCount = currentBorrowedCount;
        if (maxBorrowLimit != null) this.maxBorrowLimit = maxBorrowLimit;
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

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(String batchYear) {
        this.batchYear = batchYear;
    }

    public Integer getCurrentBorrowedCount() {
        return currentBorrowedCount;
    }

    public void setCurrentBorrowedCount(Integer currentBorrowedCount) {
        this.currentBorrowedCount = currentBorrowedCount;
    }

    public Integer getMaxBorrowLimit() {
        return maxBorrowLimit;
    }

    public void setMaxBorrowLimit(Integer maxBorrowLimit) {
        this.maxBorrowLimit = maxBorrowLimit;
    }

    public static StudentProfileBuilder builder() {
        return new StudentProfileBuilder();
    }

    public static class StudentProfileBuilder {
        private Long id;
        private UserProfile userProfile;
        private String rollNumber;
        private String department;
        private String batchYear;
        private Integer currentBorrowedCount = 0;
        private Integer maxBorrowLimit = 5;

        public StudentProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentProfileBuilder userProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
            return this;
        }

        public StudentProfileBuilder rollNumber(String rollNumber) {
            this.rollNumber = rollNumber;
            return this;
        }

        public StudentProfileBuilder department(String department) {
            this.department = department;
            return this;
        }

        public StudentProfileBuilder batchYear(String batchYear) {
            this.batchYear = batchYear;
            return this;
        }

        public StudentProfileBuilder currentBorrowedCount(Integer currentBorrowedCount) {
            this.currentBorrowedCount = currentBorrowedCount;
            return this;
        }

        public StudentProfileBuilder maxBorrowLimit(Integer maxBorrowLimit) {
            this.maxBorrowLimit = maxBorrowLimit;
            return this;
        }

        public StudentProfile build() {
            return new StudentProfile(id, userProfile, rollNumber, department, batchYear, currentBorrowedCount, maxBorrowLimit);
        }
    }
}
