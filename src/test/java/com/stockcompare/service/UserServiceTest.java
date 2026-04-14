package com.stockcompare.service;
import com.stockcompare.domain.model.UserDetail;
import com.stockcompare.repository.IUserRepository;
import org.junit.jupiter.api.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UserServiceTest {
    private IUserRepository mockRepo;
    private UserService userService;
    @BeforeEach void setUp() { mockRepo = mock(IUserRepository.class); userService = new UserService(mockRepo); }
    @Test @DisplayName("TC01 — createAccount returns UserDetail for valid inputs")
    void createAccount_valid() {
        UserDetail saved = new UserDetail("id1","anwar44","anwar@email.com","hash");
        when(mockRepo.existsByUsername("anwar44")).thenReturn(false);
        when(mockRepo.existsByEmail("anwar@email.com")).thenReturn(false);
        when(mockRepo.saveUser(any())).thenReturn(saved);
        UserDetail r = userService.createAccount("anwar44","anwar@email.com","password123");
        assertNotNull(r); assertEquals("anwar44", r.username);
    }
    @Test @DisplayName("TC02 — createAccount throws for duplicate username")
    void createAccount_duplicateUsername() {
        when(mockRepo.existsByUsername("anwar44")).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> userService.createAccount("anwar44","a@b.com","password123"));
    }
    @Test @DisplayName("TC03 — createAccount throws for duplicate email")
    void createAccount_duplicateEmail() {
        when(mockRepo.existsByUsername("newuser")).thenReturn(false);
        when(mockRepo.existsByEmail("anwar@email.com")).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> userService.createAccount("newuser","anwar@email.com","password123"));
    }
    @Test @DisplayName("TC04 — createAccount throws for short password")
    void createAccount_shortPassword() {
        assertThrows(IllegalArgumentException.class, () -> userService.createAccount("anwar44","a@b.com","abc"));
    }
    @Test @DisplayName("TC05 — validateDetails returns true for valid user")
    void validateDetails_valid() {
        assertTrue(userService.validateDetails(new UserDetail("id1","anwar44","a@b.com","hash")));
    }
    @Test @DisplayName("TC06 — validateDetails returns false for blank username")
    void validateDetails_blankUsername() {
        assertFalse(userService.validateDetails(new UserDetail("id1","","a@b.com","hash")));
    }
    @Test @DisplayName("TC07 — validateDetails returns false for invalid email")
    void validateDetails_invalidEmail() {
        assertFalse(userService.validateDetails(new UserDetail("id1","anwar44","notanemail","hash")));
    }
    @Test @DisplayName("TC08 — validateDetails returns false for null")
    void validateDetails_null() { assertFalse(userService.validateDetails(null)); }
    @Test @DisplayName("TC09 — getAccountDetails returns user when found")
    void getAccountDetails_found() {
        UserDetail u = new UserDetail("id1","anwar44","a@b.com","hash");
        when(mockRepo.findById("id1")).thenReturn(Optional.of(u));
        assertEquals("anwar44", userService.getAccountDetails("id1").username);
    }
    @Test @DisplayName("TC10 — getAccountDetails throws when not found")
    void getAccountDetails_notFound() {
        when(mockRepo.findById("bad")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getAccountDetails("bad"));
    }
    @Test @DisplayName("TC11 — checkUserExists returns true when username exists")
    void checkUserExists_true() {
        when(mockRepo.existsByUsername("anwar44")).thenReturn(true);
        assertTrue(userService.checkUserExists("anwar44","other@b.com"));
    }
    @Test @DisplayName("TC12 — checkUserExists returns false when neither exists")
    void checkUserExists_false() {
        when(mockRepo.existsByUsername("new")).thenReturn(false);
        when(mockRepo.existsByEmail("new@b.com")).thenReturn(false);
        assertFalse(userService.checkUserExists("new","new@b.com"));
    }
}
