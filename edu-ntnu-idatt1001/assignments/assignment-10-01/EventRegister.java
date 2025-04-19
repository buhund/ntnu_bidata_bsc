import java.util.*;
public class EventRegister {

    // Create the array list?
    public ArrayList<Event> eventDatabase = new ArrayList<>();

    /**
     * Method for creating new event.
     * Adds newEvent to the event database.
     * @param eventID
     * @param date
     * @param location
     * @param type
     * @param organiser
     * @return newEvent
     */
    public Event newEvent(int eventID, long date, String location, String type, String organiser){
        Event newEvent = new Event(eventID, date, location, type, organiser);
        eventDatabase.add(newEvent);
        return newEvent;
    }

    public Event findByLocation(String location){
        for (Event event: eventDatabase) {
            if(event.getEventLocation().equals(location))
                return event;
        }
        return null;
    }

    public Event findByDate(long date){
        for (Event event: eventDatabase){
            if(event.getEventDate() == (date))
                return event;
        }
        return null;
    }

    /**
     * Find events whose "date" lies between two input numbers ("dates").
     * Checks if the event date is greater than Low, and less than High.
     * @param dateLow
     * @param dateHigh
     * @return event in interval
     */
    public ArrayList<Event> findByTimeInterval(long dateLow, long dateHigh){
        ArrayList<Event> tempList = new ArrayList<>();
        for (Event event: eventDatabase) {
            if (event.getEventDate() >= dateLow && event.getEventDate()<= dateHigh){
                tempList.add(event);
            }
        }
        return tempList;
    }

    public ArrayList<Event> sortByLocation(){
        // Java Master Race Lambda Code
        eventDatabase.sort((e1, e2) -> e1.getEventLocation().compareToIgnoreCase(e2.getEventLocation()));

        // Pleb n00b code,
//        eventDatabase.sort(new Comparator<Event>() {
//            @Override
//            public int compare(Event e1, Event e2) {
//                return e1.getEventLocation().compareToIgnoreCase(e2.getEventLocation());
//            }
//        });
        return eventDatabase;
    }

    public ArrayList<Event> sortByType(){
        eventDatabase.sort((e1, e2) -> e1.getEventType().compareToIgnoreCase(e2.getEventType()));
        return eventDatabase;
    }

    public ArrayList<Event> sortByDateAndTime(){
        // Next Level 1337 Javacode
        //eventDatabase.sort(Comparator.comparingLong(Event::getEventDate));
        // Fancy Lambda Javacode
        // eventDatabase.sort((e1, e2) -> Long.compare(e1.getEventDate(), e2.getEventDate()));
        Collections.sort(eventDatabase, Comparator.comparing(Event :: getEventDate).thenComparing(Event :: getEventType));
        return eventDatabase;
    }


    @Override
    public String toString() {
        return eventDatabase.toString();
    }

}
