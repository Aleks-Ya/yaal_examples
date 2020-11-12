package java8.time_api.excercises.airport_monitor;

class Flight {
    private final Event departure;
    private final Event arrival;

    Flight(Event departure, Event arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    Event getDeparture() {
        return departure;
    }

    Event getArrival() {
        return arrival;
    }
}
