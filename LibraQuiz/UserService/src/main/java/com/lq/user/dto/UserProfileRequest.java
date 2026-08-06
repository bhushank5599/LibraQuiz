package com.lq.user.dto;

import com.lq.user.entity.UserProfile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserProfileRequest {

    @NotNull(message = "Identity User ID is required")
    private Long identityUserId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String address;
    private String avatarUrl;
    private UserProfile.UserRoleType userRole;

    private String rollNumber;
    private String department;
    private String batchYear;
    private String employeeId;
    private String qualification;
    private String specialization;
    private String deskLocation;
    private String shiftTiming;

    public UserProfileRequest() {
    }

    public UserProfileRequest(Long identityUserId, String firstName, String lastName, String email, String phone, String address, String avatarUrl, UserProfile.UserRoleType userRole, String rollNumber, String department, String batchYear, String employeeId, String qualification, String specialization, String deskLocation, String shiftTiming) {
        this.identityUserId = identityUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.userRole = userRole;
        this.rollNumber = rollNumber;
        this.department = department;
        this.batchYear = batchYear;
        this.employeeId = employeeId;
        this.qualification = qualification;
        this.specialization = specialization;
        this.deskLocation = deskLocation;
        this.shiftTiming = shiftTiming;
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

    public static UserProfileRequestBuilder builder() {
        return new UserProfileRequestBuilder();
    }

    public static class UserProfileRequestBuilder {
        private Long identityUserId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;
        private String avatarUrl;
        private UserProfile.UserRoleType userRole;
        private String rollNumber;
        private String department;
        private String batchYear;
        private String employeeId;
        private String qualification;
        private String specialization;
        private String deskLocation;
        private String shiftTiming;

        public UserProfileRequestBuilder identityUserId(Long identityUserId) {
            this.identityUserId = identityUserId;
            return this;
        }

        public UserProfileRequestBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserProfileRequestBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserProfileRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserProfileRequestBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserProfileRequestBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserProfileRequestBuilder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UserProfileRequestBuilder userRole(UserProfile.UserRoleType userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserProfileRequestBuilder rollNumber(String rollNumber) {
            this.rollNumber = rollNumber;
            return this;
        }

        public UserProfileRequestBuilder department(String department) {
            this.department = department;
            return this;
        }

        public UserProfileRequestBuilder batchYear(String batchYear) {
            this.batchYear = batchYear;
            return this;
        }

        public UserProfileRequestBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public UserProfileRequestBuilder qualification(String qualification) {
            this.qualification = qualification;
            return this;
        }

        public UserProfileRequestBuilder specialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public UserProfileRequestBuilder deskLocation(String deskLocation) {
            this.deskLocation = deskLocation;
            return this;
        }

        public UserProfileRequestBuilder shiftTiming(String shiftTiming) {
            this.shiftTiming = shiftTiming;
            return this;
        }

        public UserProfileRequest build() {
            return new UserProfileRequest(identityUserId, firstName, lastName, email, phone, address, avatarUrl, userRole, rollNumber, department, batchYear, employeeId, qualification, specialization, deskLocation, shiftTiming);
        }
    }
}
