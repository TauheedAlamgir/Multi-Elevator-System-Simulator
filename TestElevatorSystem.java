import java.net.InetAddress;

public class TestElevatorSystem {

    public static void main(String[] args) {
        try {

            Scheduler scheduler = new Scheduler(5000); // Port number for the Scheduler.
            new Thread(scheduler).start();

            // Wait for the scheduler to start up.
            Thread.sleep(1000);


            Elevator elevator1 = new Elevator(5001, InetAddress.getLocalHost(), 5000);
            Elevator elevator2 = new Elevator(5002, InetAddress.getLocalHost(), 5000);
            Elevator elevator3 = new Elevator(5003, InetAddress.getLocalHost(), 5000);
            Elevator elevator4 = new Elevator(5004, InetAddress.getLocalHost(), 5000);

            // Start Elevator threads.
            new Thread(elevator1).start();
            new Thread(elevator2).start();
            new Thread(elevator3).start();
            new Thread(elevator4).start();

            // Wait for the elevators to start up.
            Thread.sleep(1000);

            // Simulate Floor requests.
            Floor floor = new Floor(InetAddress.getLocalHost(), 5000);
            floor.sendCommand("Time: 5:00, Start Floor: 1, Destination Floor: 5, Direction: Up");
            floor.sendCommand("Time: 5:05, Start Floor: 6, Destination Floor: 2, Direction: Down");

            // Simulate faults.
            elevator1.triggerStuckFault(); // Elevator 1 simulates a stuck condition.
            elevator2.triggerDoorFault(); // Elevator 2 simulates a door fault.
            elevator3.triggerSensorFault(); // Elevator 3 simulates a sensor fault.

            // Let the simulation run for some time.
            Thread.sleep(10000);

            System.out.println("Test completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
