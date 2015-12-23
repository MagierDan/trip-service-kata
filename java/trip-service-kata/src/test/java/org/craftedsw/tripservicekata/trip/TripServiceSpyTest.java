package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceSpyTest {

    @Spy
    TripService tripService;

    @Test(expected = UserNotLoggedInException.class)
    public void getTripsUser_should_throw_exception_when_user_not_logged_in() {
        Mockito.doReturn(null).when(tripService).getLoggedUser();
        tripService.getTripsByUser(new User());
    }

    @Test
    public void should_return_an_empty_list_if_no_friends() {
        Mockito.doReturn(new User()).when(tripService).getLoggedUser();
        assertTrue(tripService.getTripsByUser(new User()).isEmpty());
    }

    @Test
    public void should_return_an_empty_list_if_user_not_in_friends() {
        final User loggedUser = new User();
        loggedUser.addFriend(new User());
        Mockito.doReturn(loggedUser).when(tripService).getLoggedUser();

        assertTrue(tripService.getTripsByUser(new User()).isEmpty());
    }

    @Test
    public void should_return_a_trip_list_when_logged_user_is_a_friend() {
        final User user = new User();
        final User loggedUser = new User();
        user.addFriend(loggedUser);

        final List<Trip> trips = new ArrayList<Trip>();
        trips.add(new Trip());

        Mockito.doReturn(loggedUser).when(tripService).getLoggedUser();
        Mockito.doReturn(trips).when(tripService).findTripsByUser(Mockito.any(User.class));

        assertFalse(tripService.getTripsByUser(user).isEmpty());
    }

}
