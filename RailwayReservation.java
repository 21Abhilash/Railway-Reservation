import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RailwayReservation {
    private TrainSchedule trainSchedule;
    private Map<String, Integer> seatAvailability;

    public RailwayReservation() {
        trainSchedule = new TrainSchedule();
        seatAvailability = new HashMap<>();
        initializeSeatAvailability();
    }

    private void initializeSeatAvailability() {
        // Initialize seat availability for each train
        for (String train : trainSchedule.getTrainNames()) {
            seatAvailability.put(train, trainSchedule.getSeatsAvailable(train));
        }
    }

    public void displayTrainSchedule() {
        System.out.println("Train Schedule:");
        trainSchedule.displaySchedule();
        System.out.println();
    }

    public void findAvailableTrains(int requiredSeats) {
        System.out.println("Available Trains for " + requiredSeats + " seats:");
        trainSchedule.displayAvailableTrains(requiredSeats);
        System.out.println();
    }

    public void checkBookingStatus(String trainName) {
        if (trainSchedule.isValidTrain(trainName)) {
            int availableSeats = seatAvailability.get(trainName);
            System.out.println("Available seats on " + trainName + ": " + availableSeats);
        } else {
            System.out.println("Invalid train name");
        }
    }

    public void initiateReservation(String trainName, int numSeats) {
        if (trainSchedule.isValidTrain(trainName)) {
            int availableSeats = seatAvailability.get(trainName);
            if (numSeats <= availableSeats) {
                seatAvailability.put(trainName, availableSeats - numSeats);
                generateBookingConfirmation(trainName, numSeats);
            } else {
                System.out.println("Not enough seats available on " + trainName);
            }
        } else {
            System.out.println("Invalid train name");
        }
    }

    private void generateBookingConfirmation(String trainName, int numSeats) {
        Train train = trainSchedule.getTrain(trainName);
        String destination = train.getDestination();
        String confirmationCode = trainName.substring(0, 3).toUpperCase() + "-" + numSeats + "-CONF";
        System.out.println("Booking confirmation for " + numSeats + " seat(s) on " + trainName + " to " + destination);
        System.out.println("Confirmation Code: " + confirmationCode);
        System.out.println("Thank you for choosing our railway services!");
    }

    public static void main(String[] args) {
        RailwayReservation reservationSystem = new RailwayReservation();
        reservationSystem.displayTrainSchedule();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of seats required: ");
        int requiredSeats = scanner.nextInt();

        reservationSystem.findAvailableTrains(requiredSeats);

        scanner.nextLine(); // Consume newline character

        System.out.print("Enter the train name for reservation: ");
        String trainName = scanner.nextLine();

        reservationSystem.initiateReservation(trainName, requiredSeats);

        scanner.close();
    }
}

class TrainSchedule {
    private Map<String, Train> trainSchedule;

    public TrainSchedule() {
        trainSchedule = new HashMap<>();
        initializeTrainSchedule();
    }

    private void initializeTrainSchedule() {
        // Sample train schedule
        addTrain("Train1", "Udupi", "Gokarna", "08:00", 100); // Changed destination for Train1 to Gokarna
        addTrain("Train2", "Udupi", "Bidar", "10:00", 150);   // Changed destination for Train2 to Bidar
        addTrain("Train3", "Udupi", "Mangalore City", "12:00", 200);
    }

    public void addTrain(String trainNumber, String departure, String destination, String departureTime, int seatsAvailable) {
        trainSchedule.put(trainNumber, new Train(trainNumber, departure, destination, departureTime, seatsAvailable));
    }

    public void displaySchedule() {
        for (Train train : trainSchedule.values()) {
            System.out.println(train);
        }
    }

    public void displayAvailableTrains(int requiredSeats) {
        for (Train train : trainSchedule.values()) {
            if (train.getSeatsAvailable() >= requiredSeats) {
                System.out.println(train);
            }
        }
    }

    public int getSeatsAvailable(String trainNumber) {
        if (trainSchedule.containsKey(trainNumber)) {
            return trainSchedule.get(trainNumber).getSeatsAvailable();
        }
        return 0;
    }

    public boolean isValidTrain(String trainNumber) {
        return trainSchedule.containsKey(trainNumber);
    }

    public Iterable<String> getTrainNames() {
        return trainSchedule.keySet();
    }
    
    public Train getTrain(String trainNumber) {
        return trainSchedule.get(trainNumber);
    }
}

class Train {
    private String trainNumber;
    private String departure;
    private String destination;
    private String departureTime;
    private int seatsAvailable;

    public Train(String trainNumber, String departure, String destination, String departureTime, int seatsAvailable) {
        this.trainNumber = trainNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.seatsAvailable = seatsAvailable;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    @Override
    public String toString() {
        return "Train Number: " + trainNumber + ", Departure: " + departure + ", Destination: " + destination +
                ", Departure Time: " + departureTime + ", Seats Available: " + seatsAvailable;
    }
}
