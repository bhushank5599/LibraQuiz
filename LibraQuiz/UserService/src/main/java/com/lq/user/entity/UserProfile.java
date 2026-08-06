package com.lq.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long identityUserId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private UserRoleType userRole;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StudentProfile studentProfile;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TeacherProfile teacherProfile;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LibrarianProfile librarianProfile;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserPreference preference;

    public UserProfile() {
    }

    public UserProfile(Long id, Long identityUserId, String firstName, String lastName, String email, String phone, String address, String avatarUrl, UserRoleType userRole, LocalDateTime createdAt, LocalDateTime updatedAt, StudentProfile studentProfile, TeacherProfile teacherProfile, LibrarianProfile librarianProfile, UserPreference preference) {
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
        this.updatedAt = updatedAt;
        this.studentProfile = studentProfile;
        this.teacherProfile = teacherProfile;
        this.librarianProfile = librarianProfile;
        this.preference = preference;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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

    public UserRoleType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleType userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }

    public void setTeacherProfile(TeacherProfile teacherProfile) {
        this.teacherProfile = teacherProfile;
    }

    public LibrarianProfile getLibrarianProfile() {
        return librarianProfile;
    }

    public void setLibrarianProfile(LibrarianProfile librarianProfile) {
        this.librarianProfile = librarianProfile;
    }

    public UserPreference getPreference() {
        return preference;
    }

    public void setPreference(UserPreference preference) {
        this.preference = preference;
    }

    public enum UserRoleType {
        ADMIN, LIBRARIAN, TEACHER, STUDENT
    }

    public static UserProfileBuilder builder() {
        return new UserProfileBuilder();
    }

    public static class UserProfileBuilder {
        private Long id;
        private Long identityUserId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;
        private String avatarUrl;
        private UserRoleType userRole;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private StudentProfile studentProfile;
        private TeacherProfile teacherProfile;
        private LibrarianProfile librarianProfile;
        private UserPreference preference;

        public UserProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserProfileBuilder identityUserId(Long identityUserId) {
            this.identityUserId = identityUserId;
            return this;
        }

        public UserProfileBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserProfileBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserProfileBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserProfileBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserProfileBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserProfileBuilder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UserProfileBuilder userRole(UserRoleType userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserProfileBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserProfileBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserProfileBuilder studentProfile(StudentProfile studentProfile) {
            this.studentProfile = studentProfile;
            return this;
        }

        public UserProfileBuilder teacherProfile(TeacherProfile teacherProfile) {
            this.teacherProfile = teacherProfile;
            return this;
        }

        public UserProfileBuilder librarianProfile(LibrarianProfile librarianProfile) {
            this.librarianProfile = librarianProfile;
            return this;
        }

        public UserProfileBuilder preference(UserPreference preference) {
            this.preference = preference;
            return this;
        }

        public UserProfile build() {
            return new UserProfile(id, identityUserId, firstName, lastName, email, phone, address, avatarUrl, userRole, createdAt, updatedAt, studentProfile, teacherProfile, librarianProfile, preference);
        }
    }
}
