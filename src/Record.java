import java.time.LocalTime;

public class Record {
    private String company;
    private LocalTime departure;
    private LocalTime arrival;
    public Record (String company, LocalTime departure, LocalTime arrival){
        this.company = company;
        this.departure = departure;
        this.arrival = arrival;
    }
    public boolean isMoreEfficient(Record r){   //Сравниваем эффективность данного рейса с параметром
        return (departure.equals(r.departure) && arrival.isBefore(r.arrival)) ||
                (departure.isAfter(r.departure) && (arrival.equals(r.arrival) || arrival.isBefore(r.arrival)));
    }
    public boolean isEqual(Record r){   //Равен ли данный рейс параметру по времени отправления и прибытия
        return departure.equals(r.departure) && arrival.equals(r.arrival);
    }
    public boolean isLongerThanHour(){  //Длиннее ли данный рейс часа
        return departure.plusHours(1).isBefore(arrival);
    }
    public LocalTime getDeparture(){
        return departure;
    }
    public LocalTime getArrival(){
        return arrival;
    }
    public String getCompany(){
        return company;
    }
    @Override
    public String toString(){
        return company + ' ' + departure + ' ' + arrival;
    }
}
