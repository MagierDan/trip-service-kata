package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    @Mock
    TripService tripService;

    @Before
    public void setup() {
        Mockito.when(tripService.getTripsByUser(Mockito.any(User.class))).thenCallRealMethod();
        Mockito.when(tripService.getFriendTrips(Mockito.any(User.class), Mockito.any(User.class))).thenCallRealMethod();
    }

    @Test(expected = UserNotLoggedInException.class)
    public void getTripsUser_should_throw_exception_when_user_not_logged_in() {
        tripService.getTripsByUser(new User());
    }

    @Test
    public void getTripsUser_should_return_an_empty_list_if_no_friends() {
        Mockito.when(tripService.getLoggedUser()).thenReturn(new User());

        assertTrue(tripService.getTripsByUser(new User()).isEmpty());
    }

    @Test
    public void getTripsUser_should_return_an_empty_list_if_user_not_in_friends() {
        final User loggedUser = new User();
        loggedUser.addFriend(new User());
        Mockito.when(tripService.getLoggedUser()).thenReturn(loggedUser);

        assertTrue(tripService.getTripsByUser(new User()).isEmpty());
    }

    @Test
    public void getTripsUser_should_return_a_trip_list_when_logged_user_is_a_friend() {
        final User user = new User();
        final User loggedUser = new User();
        user.addFriend(loggedUser);

        final List<Trip> trips = new ArrayList<Trip>();
        trips.add(new Trip());

        Mockito.when(tripService.getLoggedUser()).thenReturn(loggedUser);
        Mockito.when(tripService.findTripsByUser(Mockito.any(User.class))).thenReturn(trips);

        assertFalse(tripService.getTripsByUser(user).isEmpty());
    }

}
