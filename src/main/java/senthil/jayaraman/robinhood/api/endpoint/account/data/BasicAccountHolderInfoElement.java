package senthil.jayaraman.robinhood.api.endpoint.account.data;

public class BasicAccountHolderInfoElement  {
	
	private String address;
	private String citizenship;
	private String city;
	private String country_of_residence;
	private String date_of_birth;
	private String marital_status;
	private int number_dependents;
	private long phone_number;
	private String state;
	private int tax_id_ssn;
	
	//TODO: updated_at
	
	private int zipcode;

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the country_of_residence
	 */
	public String getCountryOfResidence() {
		return country_of_residence;
	}

	/**
	 * @return the date_of_birth
	 */
	public String getDateOfBirth() {
		return date_of_birth;
	}

	/**
	 * @return the marital_status
	 */
	public String getMaritalStatus() {
		return marital_status;
	}

	/**
	 * @return the number_dependents
	 */
	public int getNumberDependents() {
		return number_dependents;
	}

	/**
	 * @return the phone_number
	 */
	public long getPhoneNumber() {
		return phone_number;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the tax_id_ssn
	 */
	public int getTaxIdSsn() {
		return tax_id_ssn;
	}

	/**
	 * @return the zipcode
	 */
	public int getZipcode() {
		return zipcode;
	}
	

}
