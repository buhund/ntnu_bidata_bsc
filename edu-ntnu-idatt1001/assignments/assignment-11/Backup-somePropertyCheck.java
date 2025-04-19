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

    // Gjøre sjekk her for hvorvidt den eksisterer eller ikke --> return null.
    // Burde sjekke dette for hver input.
    // Kan kombinere begge disse to til en metode, og bruke den til å utføre søkegreiene.

    /**
     * Delete property by iterating and comparing property ID to the list.
     * Remove element from list.
     * @param munNum
     * @param lotNum
     * @param secNum
     */
    public void deleteProperty(int munNum, int lotNum, int secNum) {
        Property placeholderProperty;
        for (int i = 0; i < getRegisterOfProperties().size(); i++) {
            placeholderProperty = getRegisterOfProperties().get(i);
            if ((placeholderProperty.getMunicipalityNumber() == munNum)
                    && (placeholderProperty.getLotNumber() == lotNum)
                    && (placeholderProperty.getSectionNumber() == secNum)) {
                registerOfProperties.remove(placeholderProperty);
            }
        }
    }

    /**
     * Take in mun-lot/sec, and search for property.
     * @param munNum Municipality number
     * @param lotNum Lot number
     * @param secNum Section number
     * @return property
     */
    public String searchForPropertyID(int munNum, int lotNum, int secNum){
        Property placeholderProperty;
        for (int i = 0; i < getRegisterOfProperties().size(); i++) {
            placeholderProperty = getRegisterOfProperties().get(i);
            if ((placeholderProperty.getMunicipalityNumber() == munNum)
                    && (placeholderProperty.getLotNumber() == lotNum)
                    && (placeholderProperty.getSectionNumber() == secNum)) {
                return getRegisterOfProperties().get(i).toString();
            }
        }
        return "Property not found. Please check your input.";
    }
