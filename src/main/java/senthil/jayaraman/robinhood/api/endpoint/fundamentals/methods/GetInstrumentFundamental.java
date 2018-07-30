package senthil.jayaraman.robinhood.api.endpoint.fundamentals.methods;

import senthil.jayaraman.robinhood.api.endpoint.fundamentals.Fundamentals;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.data.InstrumentFundamentalElement;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;
import senthil.jayaraman.robinhood.api.request.RequestMethod;

/**
 * Given a instrument ID, this method returns a {@link senthil.jayaraman.robinhood.api.endpoint.fundamentals.data.InstrumentFundamentalElement}
 * for the given instrument.
 * This class is not implemented in {@link senthil.jayaraman.robinhood.api.RobinhoodApi} because these IDs are generally not public, and
 * must be retrieved from other API methods.
 */
public class GetInstrumentFundamental extends Fundamentals {

    public GetInstrumentFundamental(String instrumentUrl) {

        this.setUrlBase(instrumentUrl);

        //This method is run as GET
        this.setMethod(RequestMethod.GET);

        //Declare what the response should look like
        this.setReturnType(InstrumentFundamentalElement.class);

    }
}
