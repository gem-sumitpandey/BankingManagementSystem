package com.assignment.bankManagementSystem.controllerTests;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import com.assignment.bankManagementSystem.controller.UserController;
import com.assignment.bankManagementSystem.entities.Users;
import com.assignment.bankManagementSystem.services.UserServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserServices userServices;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUsers() {
        Users user1 = new Users();
        Users user2 = new Users();
        List<Users> usersList = Arrays.asList(user1, user2);
        when(userServices.getUsers()).thenReturn(usersList);

        List<Users> foundUsers = userController.getUsers().getBody();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        verify(userServices, times(1)).getUsers();
    }

    @Test
    public void testGetUser(){
    int userId=1;
    Users user = new Users();
    user.setUserId(userId);
    when(userServices.getUserById(userId)).thenReturn(user);
    Users foundUser= userController.getUser(userId).getBody();
    assertNotNull(foundUser);
    assertEquals(userId,foundUser.getUserId());
    verify(userServices,times(1)).getUserById(userId);

    }



    @Test
    public void testAddUser() {
        Users user = new Users();
        when(userServices.addUser(any(Users.class))).thenReturn(user);
        userController.addUser(user);
        verify(userServices,times(1)).addUser(any(Users.class));

    }

    @Test
    public void testPutUser()  {
        Users user = new Users();
        int userId=1;
        user.setUserId(userId);

        when(userServices.updateUserDetails(eq(userId),any(Users.class))).thenReturn(user);
        Users updated = userController.putUser(userId,user).getBody();
        assertEquals(user,updated);

       verify(userServices,times(1)).updateUserDetails(eq(userId),any(Users.class));
    }

    @Test
    public void testDeleteUser(){

        Users user = new Users();
        user.setUserId(1);
        when(userServices.getUserById(1)).thenReturn(user);


        ResponseEntity<HttpStatus> response = userController.deleteUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());


        verify(userServices, times(1)).deleteUser(eq(1));
    }


}
