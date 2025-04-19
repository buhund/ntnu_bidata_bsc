/**
 * @author iverik
 * @version 0.0.1
 *
 * Notes to self:
 * - Where to JavaDoc
 * - Composition and aggregation?
 * - Cohesion? No clue.
 * - Coupling? Keine ahnung.
 */

public class Property {

    /**
     * Not final. Kommunesammenslåing.
     */
    private int municipalityNumber;
    /**
     * Not final. Kommunesammenslåing.
     */
    private String municipalityName; // Kommunesammenslåing --> Ikke final.
    private int lotNumber = 0; // Gårdnummer = Hele eiendommen
    private int sectionNumber = 0; // Bruksnummer = Del/tomt av gården.
    /**
     * Not final. Section name should be mutable.
     */
    private String sectionName; // Må kunne endre bruksnavn ;)
    /**
     * Not final. Lot area must be mutable, in case of subdividing the lot.
     */
    private double lotArea;
    /**
     * Not final. Owner name must be able to change.
     */
    private String ownerName;

    /**
     * Constructor for property class.
     * If statements to check for valid input.
     * Throw exception, with appropriate error message, if invalid.
     * @param municipalityNumber
     * @param municipalityName
     * @param lotNumber
     * @param sectionNumber
     * @param sectionName
     * @param lotArea
     * @param ownerName
     */
    public Property(int municipalityNumber, String municipalityName, int lotNumber, int sectionNumber, String sectionName, double lotArea, String ownerName) {

        // Checking inputs. Throw exception with error message if invalid.
        if (lotArea < 0)
            throw new IllegalArgumentException("Lot area number must be a positive integer.");
        if (municipalityNumber < 101 || municipalityNumber > 5054)
            throw new IllegalArgumentException("Municipality number must be a positive integer, between 101 and 5054.");
        if (lotNumber < 0)
            throw new IllegalArgumentException("Lot number must be a positive integer.");
        if (sectionNumber < 0)
            throw new IllegalArgumentException("Section number must be a positive integer.");

        this.municipalityNumber = municipalityNumber;
        this.municipalityName = municipalityName;
        this.lotNumber = lotNumber;
        this.sectionNumber = sectionNumber;
        this.sectionName = sectionName;
        this.lotArea = lotArea;
        this.ownerName = ownerName;
    }


    /**
     * Method for testing inputs before adding new property.
     * In this application, making an entire method for checking inputs, which would be used for only a single
     * input, is inefficient, according to industry programmers.
     * Better to have the check implemented directly in the method responsible for creating the Property.
     * Had this been an application with a larger scope, where the check could be used for several different
     * input scenarios, it would make more sense to move the testing out into a separate function.
     * Thus the function have been commented out.
     * It would work by the addNewProperty first using testValues to check inputs.
     * If inputs return no exception, then addNewProperty would make a new call to registerNewProperty;
     * if exceptions are returned, then relevant error messages should be displayed.
     * The testValues function should not do the call to registerNewProperty, since that would
     * be outside the area of responsibility for a simple test values function.
     * @param municipalityNumber
     * @param lotNumber
     * @param sectionNumber
     * @param lotArea
     */
/*
    public void testValues(int municipalityNumber, int lotNumber, int sectionNumber, int lotArea){
        if (lotArea < 0){
            throw new IllegalArgumentException();
        } else if (municipalityNumber < 101 || municipalityNumber > 5054) {
            throw new IllegalArgumentException();
        } else if (lotNumber > 0) {
            throw new IllegalArgumentException();
        } else if (sectionNumber > 0) {
            throw new IllegalArgumentException();
        } else {
            return;
        }
    }
*/




    // Getters
    public int getMunicipalityNumber() {
        return municipalityNumber;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public String getSectionName() {
        return sectionName;
    }

    public double getLotArea() {
        return lotArea;
    }

    public String getOwnerName() {
        return ownerName;
    }


    // Setters. Not used, but could be useful.
    public void setMunicipalityNumber(int municipalityNumber) {
        this.municipalityNumber = municipalityNumber;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setLotArea(double lotArea) {
        this.lotArea = lotArea;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * toString for full property details.
     * @return
     */
    public String toString(){
        return  "Property: " + getMunicipalityNumber() + "-" + getLotNumber() + "/" + getSectionNumber() + "\n" +
                "Municipality name: " + getMunicipalityName() + "\n" +
                "Municipality number: " + getMunicipalityNumber() + "\n" +
                "Lot number: " + getLotNumber() + "\n" +
                "Section number: " + getSectionNumber() + "\n" +
                "Section name: " + getSectionName() + "\n" +
                "Lot area: " + getLotArea() + "\n" +
                "Current owner: " + getOwnerName() + "\n";
    }

    /**
     * Printing short property info for section.
     * Only mun-lot/sec
     * municipalityNumber-lotNumber/sectionNumber
     * @return
     */
    public String toShortString(){
        return getMunicipalityNumber() + "-" + getLotNumber() + "/" + getSectionNumber();
    }



}
