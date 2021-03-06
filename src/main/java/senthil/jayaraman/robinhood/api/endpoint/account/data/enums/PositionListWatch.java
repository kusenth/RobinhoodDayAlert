package senthil.jayaraman.robinhood.api.endpoint.account.data.enums;

import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SirensBell on 6/19/2017.
 */
public class PositionListWatch {

    /**
     * This is here due to what looks to be an unimplemented feature from the Robinhood team
     */
    private PositionWatch[] previous;

    /**
     * The list of positions that the user is currently in
     */
    private PositionWatch[] results;

    /**
     * Get a Vector object containing every position on the account watchlist. You have a position in that stock if
     * the quantity value is above 0
     * @return A vector containing all of the positions of your account
     */
    public List<PositionWatch> getPositionWatch() throws RobinhoodApiException {

        if(results != null) {

            //Return the array as a list for ease-of-use
            List<PositionWatch> elementList = new ArrayList();
            elementList = Arrays.asList(results);

            return elementList;

        }
        else
            throw new RobinhoodApiException("Error retrieving the list of positions for the current user.");
    }

}
