/**
 * @author John Ivar Eriksen
 * @version 0.0.1
 */

import java.util.ArrayList;

public class PropertyRegister {

    public PropertyRegister() {
    }

    /**
     * Create ArrayList, holding datatype Property.
     * A register of Properties.
     */
    private final ArrayList<Property> registerOfProperties = new ArrayList<>();

    /**
     * Getter for ArrayList registerOfProperties,
     * A register of Properties.
     * Problem v.1: You're returning the entire register, which gives access to modify it OUTSIDE of methods meant to manipulate it.
     * You should return a (deep) copy instead. This will reduce coupling.
     * @return registerOfProperties
     */
    public ArrayList<Property> getRegisterOfProperties() {
        ArrayList<Property> copyOfRegisterOfProperties = new ArrayList<>(registerOfProperties); // Shallow copy
        return registerOfProperties;
    }

    /**
     * Add new property to register.
     * Need to add duplicate check, if property already exist in list.
     * @param municipalityNumber
     * @param municipalityName
     * @param lotNumber
     * @param sectionNumber
     * @param sectionName
     * @param lotArea
     * @param ownerName
     * @return
     */
    public Property registerNewProperty(int municipalityNumber, String municipalityName, int lotNumber, int sectionNumber, String sectionName, double lotArea, String ownerName) {
        Property newProperty = new Property(municipalityNumber, municipalityName, lotNumber, sectionNumber, sectionName, lotArea, ownerName);
        registerOfProperties.add(newProperty);
        return newProperty;
    }

    /**
     * Return property if found.
     * Input by ID: municipality, lot and section number.
     * SA: Could you assign an id, by munLotSec, then search for that instead? getID?
     * Now, you're going through every single property by 3 parameters. Could this be done by 1 parameter?
     *
     * @param munNum
     * @param lotNum
     * @param secNum
     * @return property, or null
     */
    public Property getSomeProperty(int munNum, int lotNum, int secNum){
        Property placeholderProperty;
        for (int i = 0; i < getRegisterOfProperties().size(); i++) {
            placeholderProperty = getRegisterOfProperties().get(i);
            if ((placeholderProperty.getMunicipalityNumber() == munNum)
                    && (placeholderProperty.getLotNumber() == lotNum)
                    && (placeholderProperty.getSectionNumber() == secNum)) {
                return placeholderProperty;
            }
        }
        return null;
    }

    /**
     * Delete property by finding via search method.
     * If property does not exists, return to original function call.
     * Else (i.e. if found), remove element from list.
     * @param munNum
     * @param lotNum
     * @param secNum
     */
    public void deleteProperty(int munNum, int lotNum, int secNum) {
        Property placeholderProperty = getSomeProperty(munNum, lotNum, secNum);
        if (placeholderProperty == null)
            return;
        registerOfProperties.remove(placeholderProperty);
    }


    /**
     * Find all properties at Lot number.
     * @param lotNumber
     * @return
     */
    public ArrayList<Property> searchForAllAtLotNumber ( int lotNumber){
        ArrayList<Property> listOfLot = new ArrayList<>();
        for (int i = 0; i < getRegisterOfProperties().size(); i++) {
            if (getRegisterOfProperties().get(i).getLotNumber() == lotNumber) {
                listOfLot.add(getRegisterOfProperties().get(i));
            }
        }
        return listOfLot;
    }


    /**
     * Iterate over property array and calculate total average area.
     * @return average area as double.
     */
    public double averageLotArea () {
        double AreaCounter = 0;
        for (int i = 0; i < getRegisterOfProperties().size(); i++) {
            AreaCounter += getRegisterOfProperties().get(i).getLotArea();
        }
        double avgAreaCounter = AreaCounter / getRegisterOfProperties().size();
        return avgAreaCounter;
    }
}
