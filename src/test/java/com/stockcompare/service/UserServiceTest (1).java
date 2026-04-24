package com.stockcompare.service;

import com.stockcompare.domain.interfaces.IAccountService;
import com.stockcompare.domain.model.UserDetail;
import com.stockcompare.repository.IUserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private IUserRepository userRepository;
    private IAccountService userService;

    @BeforeEach void setUp() { userService = new UserService(userRepository); }

    @Test void createAccount_validInputs_returnsUserDetail() {
        when(userRepository.findByUsername("anwar")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("anwar@test.com")).thenReturn(Optional.empty());
        doNothing().when(userRepository).saveUser(any());
        UserDetail result = userService.createAccount(new UserDetail(null,"anwar","anwar@test.com","password123",false));
        assertNotNull(result); assertEquals("anwar", result.username);
    }

    @Test void createAccount_duplicateUsername_throwsException() {
        when(userRepository.findByUsername("anwar")).thenReturn(Optional.of(new UserDetail("id1","anwar","other@test.com","hash",false)));
        assertThrows(IllegalStateException.class, () -> userService.createAccount(new UserDetail(null,"anwar","anwar@test.com","password123",false)));
    }

    @Test void createAccount_duplicateEmail_throwsException() {
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("anwar@test.com")).thenReturn(Optional.of(new UserDetail("id1","anwar","anwar@test.com","hash",false)));
        assertThrows(IllegalStateException.class, () -> userService.createAccount(new UserDetail(null,"newuser","anwar@test.com","password123",false)));
    }

    @Test void createAccount_shortPassword_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userService.createAccount(new UserDetail(null,"anwar","anwar@test.com","abc",false)));
    }

    @Test void validateDetails_validUser_returnsTrue() {
        assertTrue(userService.validateDetails(new UserDetail("id1","anwar","anwar@test.com","password123",false)));
    }

    @Test void validateDetails_blankUsername_returnsFalse() {
        assertFalse(userService.validateDetails(new UserDetail("id1","","anwar@test.com","password123",false)));
    }

    @Test void validateDetails_invalidEmail_returnsFalse() {
        assertFalse(userService.validateDetails(new UserDetail("id1","anwar","not-an-email","password123",false)));
    }

    @Test void validateDetails_nullInput_returnsFalse() {
        assertFalse(userService.validateDetails(null));
    }

    @Test void getAccountDetails_userExists_returnsUserDetail() {
        when(userRepository.findById("id1")).thenReturn(Optional.of(new UserDetail("id1","anwar","anwar@test.com","hash",false)));
        assertEquals("anwar", userService.getAccountDetails("id1").username);
    }

    @Test void getAccountDetails_userNotFound_throwsException() {
        when(userRepository.findById("unknown")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getAccountDetails("unknown"));
    }

    @Test void checkUserExists_usernameExists_returnsTrue() {
        when(userRepository.findByUsername("anwar")).thenReturn(Optional.of(new UserDetail("id1","anwar","anwar@test.com","hash",false)));
        assertTrue(userService.checkUserExists("anwar","other@test.com"));
    }

    @Test void checkUserExists_neitherExists_returnsFalse() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("ghost@test.com")).thenReturn(Optional.empty());
        assertFalse(userService.checkUserExists("ghost","ghost@test.com"));
    }
}
