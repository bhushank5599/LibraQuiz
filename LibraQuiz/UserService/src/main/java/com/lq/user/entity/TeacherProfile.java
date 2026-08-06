package com.lq.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_profiles")
public class TeacherProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private String employeeId;
    private String department;
    private String qualification;
    private String specialization;

    public TeacherProfile() {
    }

    public TeacherProfile(Long id, UserProfile userProfile, String employeeId, String department, String qualification, String specialization) {
        this.id = id;
        this.userProfile = userProfile;
        this.employeeId = employeeId;
        this.department = department;
        this.qualification = qualification;
        this.specialization = specialization;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public static TeacherProfileBuilder builder() {
        return new TeacherProfileBuilder();
    }

    public static class TeacherProfileBuilder {
        private Long id;
        private UserProfile userProfile;
        private String employeeId;
        private String department;
        private String qualification;
        private String specialization;

        public TeacherProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TeacherProfileBuilder userProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
            return this;
        }

        public TeacherProfileBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public TeacherProfileBuilder department(String department) {
            this.department = department;
            return this;
        }

        public TeacherProfileBuilder qualification(String qualification) {
            this.qualification = qualification;
            return this;
        }

        public TeacherProfileBuilder specialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public TeacherProfile build() {
            return new TeacherProfile(id, userProfile, employeeId, department, qualification, specialization);
        }
    }
}
