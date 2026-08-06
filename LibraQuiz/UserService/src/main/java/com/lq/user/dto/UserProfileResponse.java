package com.lq.user.dto;

import com.lq.user.entity.UserProfile;

import java.time.LocalDateTime;

public class UserProfileResponse {

    private Long id;
    private Long identityUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String avatarUrl;
    private UserProfile.UserRoleType userRole;
    private LocalDateTime createdAt;

    private String rollNumber;
    private String department;
    private String batchYear;
    private Integer currentBorrowedCount;
    private Integer maxBorrowLimit;

    private String employeeId;
    private String qualification;
    private String specialization;

    private String deskLocation;
    private String shiftTiming;

    public UserProfileResponse() {
    }

    public UserProfileResponse(Long id, Long identityUserId, String firstName, String lastName, String email, String phone, String address, String avatarUrl, UserProfile.UserRoleType userRole, LocalDateTime createdAt, String rollNumber, String department, String batchYear, Integer currentBorrowedCount, Integer maxBorrowLimit, String employeeId, String qualification, String specialization, String deskLocation, String shiftTiming) {
        this.id = id;
        this.identityUserId = identityUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.rollNumber = rollNumber;
        this.department = department;
        this.batchYear = batchYear;
        this.currentBorrowedCount = currentBorrowedCount;
        this.maxBorrowLimit = maxBorrowLimit;
        this.employeeId = employeeId;
        this.qualification = qualification;
        this.specialization = specialization;
        this.deskLocation = deskLocation;
        this.shiftTiming = shiftTiming;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdentityUserId() {
        return identityUserId;
    }

    public void setIdentityUserId(Long identityUserId) {
        this.identityUserId = identityUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public UserProfile.UserRoleType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserProfile.UserRoleType userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public static UserProfileResponseBuilder builder() {
        return new UserProfileResponseBuilder();
    }

    public static class UserProfileResponseBuilder {
        private Long id;
        private Long identityUserId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;
        private String avatarUrl;
        private UserProfile.UserRoleType userRole;
        private LocalDateTime createdAt;
        private String rollNumber;
        private String department;
        private String batchYear;
        private Integer currentBorrowedCount;
        private Integer maxBorrowLimit;
        private String employeeId;
        private String qualification;
        private String specialization;
        private String deskLocation;
        private String shiftTiming;

        public UserProfileResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserProfileResponseBuilder identityUserId(Long identityUserId) {
            this.identityUserId = identityUserId;
            return this;
        }

        public UserProfileResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserProfileResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserProfileResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserProfileResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserProfileResponseBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserProfileResponseBuilder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UserProfileResponseBuilder userRole(UserProfile.UserRoleType userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserProfileResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserProfileResponseBuilder rollNumber(String rollNumber) {
            this.rollNumber = rollNumber;
            return this;
        }

        public UserProfileResponseBuilder department(String department) {
            this.department = department;
            return this;
        }

        public UserProfileResponseBuilder batchYear(String batchYear) {
            this.batchYear = batchYear;
            return this;
        }

        public UserProfileResponseBuilder currentBorrowedCount(Integer currentBorrowedCount) {
            this.currentBorrowedCount = currentBorrowedCount;
            return this;
        }

        public UserProfileResponseBuilder maxBorrowLimit(Integer maxBorrowLimit) {
            this.maxBorrowLimit = maxBorrowLimit;
            return this;
        }

        public UserProfileResponseBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public UserProfileResponseBuilder qualification(String qualification) {
            this.qualification = qualification;
            return this;
        }

        public UserProfileResponseBuilder specialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public UserProfileResponseBuilder deskLocation(String deskLocation) {
            this.deskLocation = deskLocation;
            return this;
        }

        public UserProfileResponseBuilder shiftTiming(String shiftTiming) {
            this.shiftTiming = shiftTiming;
            return this;
        }

        public UserProfileResponse build() {
            return new UserProfileResponse(id, identityUserId, firstName, lastName, email, phone, address, avatarUrl, userRole, createdAt, rollNumber, department, batchYear, currentBorrowedCount, maxBorrowLimit, employeeId, qualification, specialization, deskLocation, shiftTiming);
        }
    }
}
