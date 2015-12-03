package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        final User loggedUser = getLoggedUser();

        if (loggedUser != null)
            return getFriendTrips(user, loggedUser);

        throw new UserNotLoggedInException();
    }

    List<Trip> getFriendTrips(User friend, User loggedUser) {
        final boolean isFriend = friend.getFriends().contains(loggedUser);
        return (isFriend) ? findTripsByUser(friend) : new ArrayList<Trip>();
    }

    List<Trip> findTripsByUser(final User user) {
        return TripDAO.findTripsByUser(user);
    }

    User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
