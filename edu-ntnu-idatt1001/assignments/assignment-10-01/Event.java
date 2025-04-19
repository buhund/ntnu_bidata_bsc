public class Event {
    // Each event should have a separate Number, Name, Organiser, Type and Time (date, time)

    /**
     * Event ID. Four digits in incrementing value, starting at 1000.
     * I.e. 1001, 1002 ... 1101, etc.
     */
    private final int eventID;

    /**
     * Event date, ISO format.
     * Format: yyyy-MM-dd HH:mm
     * Written as int: yyyyMMddHHmm
     */
    private long eventDate; // yyyy MM dd --> yyyyMMdd


    /**
     * Event location.
     */
    private String eventLocation;

    /**
     * Event type, e.g.:
     * Concert, children's theater, lecture, music festival, etc.
     */
    private String eventType;

    /**
     * Organiser of the event. Or somesuch.
     */
    private String eventOrganiser;

    /**
     *
     * @param eventID
     * @param eventDate
     * @param eventLocation
     * @param eventType
     * @param eventOrganiser
     */
    public Event(int eventID, long eventDate, String eventLocation, String eventType, String eventOrganiser) {
        this.eventID = eventID;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventOrganiser = eventOrganiser;
    }

    // Getters
    public int getEventID() {
        return eventID;
    }

    public long getEventDate() {
        return eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventOrganiser() {
        return eventOrganiser;
    }

    // Setters. In case the date and/or location needs to be changed.
    // Might not be used in the finished product. We'll see.
    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }



    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    /**
     * toString to print out classthingymajig?
     * @return
     */
    @Override
    public String toString() {
        return "Event-ID: " + this.getEventID() + "\n" +
                "Event Date: " + this.getEventDate() + "\n" +
                "Event Location: " + this.getEventLocation() + "\n" +
                "Event Type: " + this.getEventType() + "\n" +
                "Event Organiser: " + this.getEventOrganiser() + "\n"
                + "\n";
    }
}
